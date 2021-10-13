package ar.com.westsoft.supercalc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ar.com.westsoft.supercalc.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private val viewmodel: CalcVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.model.observe(viewLifecycleOwner, { calc ->
            binding.correctRequest.text = calc.correctRequestCount.toString()
            binding.totalRequest.text = calc.totalRequestCount.toString()
            binding.limitRequest.text = calc.repetitionTime.toString()
            binding.num1.text = calc.num1.toString()
            binding.num2.text = calc.num2.toString()
            binding.sign.text = calc.operator.sign
            binding.result.setText("")
            if (calc.message.isNotBlank()) {
                Toast.makeText(context, calc.message, Toast.LENGTH_LONG).show()
            }
        })
        binding.requestButton.setOnClickListener {
            binding.result.text.toString().toIntOrNull()?.let { num ->
                viewmodel.onRequest(num)
            } ?: let {
                binding.result.setText("")
            }
        }
        binding.startButton.setOnClickListener {
            viewmodel.start()
        }
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}