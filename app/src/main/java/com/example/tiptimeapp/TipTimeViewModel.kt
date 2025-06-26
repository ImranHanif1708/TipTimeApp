package com.example.tiptimeapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

class TipTimeViewModel : ViewModel() {

    var tipInput by mutableStateOf("")
    var roundup by mutableStateOf(false)

    var billAmount by mutableStateOf("")

    var tip by mutableStateOf(calculateTip(0.0, 0.0, roundup = false))

    private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundup: Boolean): String {
        var tip = tipPercent / 100 * amount
        if(roundup) {
            tip = kotlin.math.ceil(tip)}
        return NumberFormat.getCurrencyInstance().format(tip)
    }
    fun updateTip() {

        val actualAmount = billAmount.toDoubleOrNull() ?: 0.0
        val tipPercentage = tipInput.toDoubleOrNull() ?: 0.0

        tip = calculateTip(
            amount = actualAmount,
            tipPercent = tipPercentage,
            roundup = roundup
        )
    }
}