package com.hassine.fizzbuzz.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hassine.fizzbuzz.databinding.FragmentResultBinding
import com.hassine.fizzbuzz.ui.FizzBuzzViewModel
import com.hassine.fizzbuzz.ui.result.adapter.ResultAdapter

/**
 * A Fragment with recycler view to show the result of Fizz Buzz.
 */
class ResultFragment : Fragment() {


    private var _binding: FragmentResultBinding? = null
    private val viewModel: FizzBuzzViewModel by activityViewModels()

    private val adapter: ResultAdapter by lazy {
        ResultAdapter(viewModel.fizzBuzz, viewModel::mapValue)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultRecycler.layoutManager = LinearLayoutManager(context)
        binding.resultRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}