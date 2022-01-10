package com.hassine.fizzbuzz

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hassine.fizzbuzz.ui.FizzBuzzViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Unit Test for FizzBuzzViewModel class login.
 */
@RunWith(JUnit4::class)
class FizzBuzzViewModelUnitTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fizzBuzzViewModel: FizzBuzzViewModel

    private val firstNumber = 3
    private val secondNumber = 5
    private val limitNumber = 20
    private val fizzMessage = "Fizz"
    private val buzzMessage = "Buzz"
    private val finalResult = arrayListOf(
        "1",
        "2",
        fizzMessage,
        "4",
        buzzMessage,
        fizzMessage,
        "7",
        "8",
        fizzMessage,
        buzzMessage,
        "11",
        fizzMessage,
        "13",
        "14",
        fizzMessage + buzzMessage,
        "16",
        "17",
        fizzMessage,
        "19",
        buzzMessage
    )


    @Before
    fun setUp() {
        fizzBuzzViewModel = FizzBuzzViewModel()
    }

    @Test
    fun testSuccess() {
        fizzBuzzViewModel.onFirstNumberTextChange(firstNumber.toString())
        fizzBuzzViewModel.onSecondNumberTextChange(secondNumber.toString())
        fizzBuzzViewModel.onLimitTextChange(limitNumber.toString())
        fizzBuzzViewModel.onFizzTextChange(fizzMessage)
        fizzBuzzViewModel.onBuzzTextChange(buzzMessage)
        fizzBuzzViewModel.onButtonClick()
        val formResult = fizzBuzzViewModel.resultLiveData.getOrAwaitValue()
        assert(formResult is FizzBuzzViewModel.FormResult.Success)
    }

    @Test
    fun testMapper() {
        testSuccess()
        (1..limitNumber).forEach {
            assert(finalResult[it - 1] == fizzBuzzViewModel.mapValue(it))
        }
    }

    @Test
    fun testFirstNumberError() {
        fizzBuzzViewModel.onFirstNumberTextChange("")
        fizzBuzzViewModel.onSecondNumberTextChange(secondNumber.toString())
        fizzBuzzViewModel.onLimitTextChange(limitNumber.toString())
        fizzBuzzViewModel.onFizzTextChange(fizzMessage)
        fizzBuzzViewModel.onBuzzTextChange(buzzMessage)
        fizzBuzzViewModel.onButtonClick()
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Error)
        var errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidFirstNumber)
        fizzBuzzViewModel.onFirstNumberTextChange("1")
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Waiting)
        fizzBuzzViewModel.onButtonClick()
        errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidFirstNumber)
        fizzBuzzViewModel.onFirstNumberTextChange(secondNumber.toString())
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Waiting)
        fizzBuzzViewModel.onButtonClick()
        errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidFirstNumber)
    }

    @Test
    fun testSecondNumberError() {
        fizzBuzzViewModel.onFirstNumberTextChange(firstNumber.toString())
        fizzBuzzViewModel.onSecondNumberTextChange("")
        fizzBuzzViewModel.onLimitTextChange(limitNumber.toString())
        fizzBuzzViewModel.onFizzTextChange(fizzMessage)
        fizzBuzzViewModel.onBuzzTextChange(buzzMessage)
        fizzBuzzViewModel.onButtonClick()
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Error)
        var errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidSecondNumber)
        fizzBuzzViewModel.onSecondNumberTextChange("1")
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Waiting)
        fizzBuzzViewModel.onButtonClick()
        errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidSecondNumber)
        fizzBuzzViewModel.onSecondNumberTextChange(firstNumber.toString())
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Waiting)
        fizzBuzzViewModel.onButtonClick()
        errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidSecondNumber)
    }

    @Test
    fun testLimitNumberError() {
        fizzBuzzViewModel.onFirstNumberTextChange(firstNumber.toString())
        fizzBuzzViewModel.onSecondNumberTextChange(secondNumber.toString())
        fizzBuzzViewModel.onLimitTextChange("")
        fizzBuzzViewModel.onFizzTextChange(fizzMessage)
        fizzBuzzViewModel.onBuzzTextChange(buzzMessage)
        fizzBuzzViewModel.onButtonClick()
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Error)
        var errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidLimitNumber)
        fizzBuzzViewModel.onSecondNumberTextChange("15")
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Waiting)

        fizzBuzzViewModel.onButtonClick()
        errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidLimitNumber)
    }

    @Test
    fun testFizzTextError() {
        fizzBuzzViewModel.onFirstNumberTextChange(firstNumber.toString())
        fizzBuzzViewModel.onSecondNumberTextChange(secondNumber.toString())
        fizzBuzzViewModel.onLimitTextChange(limitNumber.toString())
        fizzBuzzViewModel.onFizzTextChange("")
        fizzBuzzViewModel.onBuzzTextChange(buzzMessage)
        fizzBuzzViewModel.onButtonClick()
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Error)
        var errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidFizzMessage)
        fizzBuzzViewModel.onFizzTextChange(buzzMessage)
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Waiting)
        fizzBuzzViewModel.onButtonClick()
        errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidFizzMessage)
    }

    @Test
    fun testBuzzTextError() {
        fizzBuzzViewModel.onFirstNumberTextChange(firstNumber.toString())
        fizzBuzzViewModel.onSecondNumberTextChange(secondNumber.toString())
        fizzBuzzViewModel.onLimitTextChange(limitNumber.toString())
        fizzBuzzViewModel.onFizzTextChange(fizzMessage)
        fizzBuzzViewModel.onBuzzTextChange("")
        fizzBuzzViewModel.onButtonClick()
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Error)
        var errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error
        assert(!errorResult.isValidBuzzMessage)
        fizzBuzzViewModel.onFizzTextChange(fizzMessage)
        assert(fizzBuzzViewModel.resultLiveData.getOrAwaitValue() is FizzBuzzViewModel.FormResult.Waiting)
        fizzBuzzViewModel.onButtonClick()
        errorResult =
            fizzBuzzViewModel.resultLiveData.getOrAwaitValue() as FizzBuzzViewModel.FormResult.Error

        assert(!errorResult.isValidBuzzMessage)
    }
}