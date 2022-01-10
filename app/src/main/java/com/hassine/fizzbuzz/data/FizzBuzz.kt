package com.hassine.fizzbuzz.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FizzBuzz(
    var firstNumber: Int = 0,
    var secondNumber: Int = 0,
    var limitNumber: Int = 0,
    var fizzMessage: String = "",
    var buzzMessage: String = ""
) : Parcelable
