package ar.com.westsoft.supercalc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalcVM : ViewModel() {
    val model = MutableLiveData( Calc.createCalc() )

    fun onRequest(result: Int) {
        model.value?.let {
            model.postValue(it.request(result))
        }
    }
    fun start() {
        model.postValue( Calc.createCalc() )
    }
}