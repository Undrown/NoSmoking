package com.undrown.nosmoking

import kotlin.math.floor

class MoneyVisualizer(value:Double) {
    private val amount = floor(value*100)/100
    val nominalsRub = mapOf(
        "50ka" to 50.0,
        "25ka" to 25.0,
        "10ka" to 10.0,
        "5ka" to 5.0,
        "1ka" to 1.0,
        "50коп" to 0.5,
        "25коп" to 0.25,
        "10коп" to 0.10,
        "5коп" to 0.05,
        "1коп" to 0.01
        )
    private val result = mutableMapOf<String, Int>()

    fun getSplit(amountUnused:Double){
        val actualNominals = nominalsRub.filter { entry -> entry.value >= amountUnused }
        val scale = actualNominals.entries.first()
        result[scale.key] = floor(amountUnused/scale.value).toInt()
        if(scale.value == 0.01)
            return
        getSplit(amountUnused - floor(amountUnused/scale.value))
    }
}