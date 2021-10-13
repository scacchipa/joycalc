package ar.com.westsoft.supercalc

enum class Operation(
    val sign: String,
    val operation: ((num1: Int, num2: Int) -> Int),
    val createNum1: ((num2: Int, max1: Int) -> Int),
    val createNum2: ((max2:Int) -> Int)) {

    PLUS("+",
        { num1: Int, num2: Int -> num1 + num2 },
        { _ : Int, max: Int -> (Math.random() * max).toInt() },
        { max2: Int -> (Math.random() * max2).toInt() }),
    REST("-",
        { num1: Int, num2: Int -> num1 - num2 },
        { num2: Int, max: Int -> (Math.random() * max).toInt() + num2 },
        { max2: Int -> (Math.random() * max2).toInt() }),
    MULT("X",
        { num1: Int, num2: Int -> num1 * num2 },
        { _: Int, max: Int -> (Math.random() * max).toInt() },
        { max2: Int -> (Math.random() * max2).toInt() }),
    DIV("/",
        { num1: Int, num2: Int -> num1 / num2 },
        { num2: Int, max: Int -> (Math.random() * max).toInt() * num2 },
        { max2: Int -> (Math.random() * (max2 - 1)).toInt() + 1})
}

data class Calc(
    val num1: Int,
    val num2: Int,
    val operator: Operation,
    val correctRequestCount: Int,
    val totalRequestCount: Int,
    val repetitionTime: Int,
    val message: String = "") {
    val repeat = totalRequestCount < repetitionTime
    companion object {
        var activedOperations = mutableListOf(Operation.PLUS, Operation.REST, Operation.MULT, Operation.DIV)
        var activedPlus = true
        var activedRest = true
        var activedMulti = true
        var activedDivide = true
        val maxNum1:Int = 10
        val maxNum2:Int = 10
        fun createCalc() = createCalc(activedOperations[(Math.random() * activedOperations.size).toInt()])
        fun createCalc(operator: Operation): Calc {
            val num2 = operator.createNum2(maxNum2)
            val num1 = operator.createNum1(num2, maxNum1)
            return Calc(
                num1 = num1,
                num2 = num2,
                operator = operator,
                correctRequestCount = 0,
                totalRequestCount = 0,
                repetitionTime = 10)
        }
        fun buildActOperations() {
            activedOperations = mutableListOf()
            if (activedPlus) activedOperations.add(Operation.PLUS)
            if (activedRest) activedOperations.add(Operation.REST)
            if (activedMulti) activedOperations.add(Operation.MULT)
            if (activedDivide) activedOperations.add(Operation.DIV)
        }
    }
    fun request(result: Int): Calc =
        if (!repeat)
            this.copy(
                message = "Finished."
            )
        else
            if (this.operator.operation(num1, num2) == result)
                createCalc(operator).copy(
                    correctRequestCount = correctRequestCount + 1,
                    totalRequestCount = totalRequestCount + 1,
                    operator = operator,
                    message = "Correct")
            else
                createCalc(operator).copy(
                    correctRequestCount = correctRequestCount,
                    totalRequestCount = totalRequestCount + 1,
                    operator = operator,
                    message = "Incorrect: $num1 ${this.operator.sign} $num2 = ${this.operator.operation(num1, num2)}")
}
