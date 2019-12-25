package com.undrown.nosmoking

class MoneyVisualizer(val amount:Float) {
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
    val result = mutableMapOf<String, Double>()

    fun getSplit(){
        var amountUnused = amount
    }
}