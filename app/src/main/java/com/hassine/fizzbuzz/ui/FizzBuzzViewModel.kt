package com.hassine.fizzbuzz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hassine.fizzbuzz.data.FizzBuzz

/**
 * Shared view model between all fragments of FizzBuzzActivity
 */
class FizzBuzzViewModel : ViewModel() {

    private val _resultLiveData by lazy { MutableLiveData<FormResult>() }
    val resultLiveData: LiveData<FormResult> = _resultLiveData


    var firstNumber: String = ""
        private set(value) {
            field = value
            fizzBuzz.firstNumber = value.toIntOrNull() ?: 0
        }
    var secondNumber: String = ""
        private set(value) {
            field = value
            fizzBuzz.secondNumber = value.toIntOrNull() ?: 0
        }
    var limitNumber: String = ""
        private set(value) {
            field = value
            fizzBuzz.limitNumber = value.toIntOrNull() ?: 0
        }
    var fizzText: String = ""
        private set(value) {
            field = value
            fizzBuzz.fizzMessage = value
        }
    var buzzText: String = ""
        private set(value) {
            field = value
            fizzBuzz.buzzMessage = value
        }

    var fizzBuzz = FizzBuzz()
        private set

    private var isValidFirstNumber: Boolean = false
    private var isValidSecondNumber: Boolean = false
    private var isValidLimitNumber: Boolean = false
    private var isValidFizzMessage: Boolean = false
    private var isValidBuzzMessage: Boolean = false


    fun onFirstNumberTextChange(text: String) {
        firstNumber = text
        _resultLiveData.value = FormResult.Waiting
    }

    fun onSecondNumberTextChange(text: String) {
        secondNumber = text
        _resultLiveData.value = FormResult.Waiting
    }

    fun onLimitTextChange(text: String) {
        limitNumber = text
        _resultLiveData.value = FormResult.Waiting
    }

    fun onFizzTextChange(text: String) {
        fizzText = text
        _resultLiveData.value = FormResult.Waiting
    }

    fun onBuzzTextChange(text: String) {
        buzzText = text
        _resultLiveData.value = FormResult.Waiting
    }

    private fun isFormValid(): Boolean {
        fizzBuzz.apply {
            isValidFirstNumber = firstNumber > 1 && firstNumber != secondNumber
            isValidSecondNumber = secondNumber > 1 && secondNumber != firstNumber
            isValidLimitNumber = limitNumber > firstNumber * secondNumber
            isValidFizzMessage = fizzMessage.isNotEmpty() && fizzMessage != buzzMessage
            isValidBuzzMessage = buzzMessage.isNotEmpty() && fizzMessage != buzzMessage
        }

        return isValidFirstNumber && isValidSecondNumber && isValidLimitNumber && isValidFizzMessage && isValidBuzzMessage
    }

    fun onButtonClick() {
        if (isFormValid()) {
            _resultLiveData.value = FormResult.Success
        } else {
            _resultLiveData.value = FormResult.Error(
                isValidFirstNumber,
                isValidSecondNumber,
                isValidLimitNumber,
                isValidFizzMessage,
                isValidBuzzMessage
            )
        }
    }

    fun mapValue(value: Int): String {
        return fizzBuzz.let {
            when {
                value % (it.firstNumber * it.secondNumber) == 0 -> {
                    it.fizzMessage + it.buzzMessage
                }
                value % (it.firstNumber) == 0 -> {
                    it.fizzMessage
                }
                value % (it.secondNumber) == 0 -> {
                    it.buzzMessage
                }
                else -> {
                    value.toString()
                }
            }
        }
    }

    sealed class FormResult {
        object Waiting : FormResult()
        data class Error(
            val isValidFirstNumber: Boolean,
            val isValidSecondNumber: Boolean,
            val isValidLimitNumber: Boolean,
            val isValidFizzMessage: Boolean,
            val isValidBuzzMessage: Boolean
        ) : FormResult()

        object Success : FormResult()
    }
}