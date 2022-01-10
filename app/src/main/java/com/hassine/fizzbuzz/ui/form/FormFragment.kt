package com.hassine.fizzbuzz.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.hassine.fizzbuzz.R
import com.hassine.fizzbuzz.databinding.FragmentFormBinding
import com.hassine.fizzbuzz.ui.FizzBuzzViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FizzBuzzViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextViews()
        initChangeListeners()
        observeResultLiveData()
        initButtonClickListener()
    }

    private fun initButtonClickListener() {
        binding.validateButton.setOnClickListener {
            viewModel.onButtonClick()
        }
    }

    private fun observeResultLiveData() {
        viewModel.resultLiveData.distinctUntilChanged().observe(viewLifecycleOwner) {
            when (it) {
                FizzBuzzViewModel.FormResult.Waiting -> {
                    clearErrors()
                }
                is FizzBuzzViewModel.FormResult.Error -> showError(it)
                is FizzBuzzViewModel.FormResult.Success -> goToResultFragment()
            }
        }
    }

    private fun goToResultFragment() {
        binding.validateButton.isEnabled = true
        binding.buzzInputText.isEnabled = true
        binding.fizzInputText.isEnabled = true
        binding.limitNumberInputText.isEnabled = true
        binding.firstNumberInputText.isEnabled = true
        binding.secondNumberInputText.isEnabled = true
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    private fun showError(errorResult: FizzBuzzViewModel.FormResult.Error) {
        if (!errorResult.isValidFirstNumber) {
            binding.firstNumberInputLayout.error = getString(R.string.first_number_error)
        }
        if (!errorResult.isValidSecondNumber) {
            binding.secondNumberInputLayout.error = getString(R.string.first_number_error)
        }
        if (!errorResult.isValidLimitNumber) {
            binding.limitNumberInputLayout.error = getString(R.string.limit_number_error)
        }
        if (!errorResult.isValidFizzMessage) {
            binding.fizzInputLayout.error = getString(R.string.fizz_message_error)
        }
        if (!errorResult.isValidBuzzMessage) {
            binding.buzzInputLayout.error = getString(R.string.buzz_message_error)
        }
    }

    private fun clearErrors() {
        binding.buzzInputLayout.isErrorEnabled = false
        binding.fizzInputLayout.isErrorEnabled = false
        binding.firstNumberInputLayout.isErrorEnabled = false
        binding.secondNumberInputLayout.isErrorEnabled = false
        binding.limitNumberInputLayout.isErrorEnabled = false
    }

    private fun initTextViews() {
        binding.firstNumberInputText.setText(viewModel.firstNumber)
        binding.secondNumberInputText.setText(viewModel.secondNumber)
        binding.limitNumberInputText.setText(viewModel.limitNumber)
        binding.fizzInputText.setText(viewModel.fizzText)
        binding.buzzInputText.setText(viewModel.buzzText)
    }

    private fun initChangeListeners() {
        binding.firstNumberInputText.doAfterTextChanged { viewModel.onFirstNumberTextChange(it.toString()) }
        binding.secondNumberInputText.doAfterTextChanged { viewModel.onSecondNumberTextChange(it.toString()) }
        binding.limitNumberInputText.doAfterTextChanged { viewModel.onLimitTextChange(it.toString()) }
        binding.fizzInputText.doAfterTextChanged { viewModel.onFizzTextChange(it.toString()) }
        binding.buzzInputText.doAfterTextChanged { viewModel.onBuzzTextChange(it.toString()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}