package com.undrown.nosmoking

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.Image
import android.media.ImageReader
import kotlin.math.floor
import kotlin.random.Random
/*
* TODO: make heap of money
* */
class MoneyVisualizer(value:Double) {
    private val amount = floor(value*100)/100
    val nominalsRub = mapOf(
        "500ka" to 500.0,
        "200ka" to 200.0,
        "100ka" to 100.0,
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
    val images = mapOf(
        "500ka" to BitmapFactory.decodeFile("/drawable/img_500.png"),
        "200ka" to BitmapFactory.decodeFile("/drawable/img_200.png"),
        "100ka" to BitmapFactory.decodeFile("/drawable/img_100.png"),
        "50ka" to BitmapFactory.decodeFile("/drawable/img_50.png"),
        "25ka" to BitmapFactory.decodeFile("/drawable/img_25.png"),
        "10ka" to BitmapFactory.decodeFile("/drawable/img_10.png"),
        "5ka" to BitmapFactory.decodeFile("/drawable/img_5.png"),
        "1ka" to BitmapFactory.decodeFile("/drawable/img_1.png"),
        "50коп" to BitmapFactory.decodeFile("/drawable/img_05.png"),
        "25коп" to BitmapFactory.decodeFile("/drawable/img_025.png"),
        "10коп" to BitmapFactory.decodeFile("/drawable/img_01.png"),
        "5коп" to BitmapFactory.decodeFile("/drawable/img_005.png"),
        "1коп" to BitmapFactory.decodeFile("/drawable/img_001.png")
    )
    val result = mutableMapOf<String, Int>()
    val resultRandom = mutableMapOf<String, Int>(
        "500ka" to 0,
        "200ka" to 0,
        "100ka" to 0,
        "50ka" to 0,
        "25ka" to 0,
        "10ka" to 0,
        "5ka" to 0,
        "1ka" to 0,
        "50коп" to 0,
        "25коп" to 0,
        "10коп" to 0,
        "5коп" to 0,
        "1коп" to 0
    )

    private fun getSplit(amountUnused:Double){
        val actualNominals = nominalsRub.filter { entry -> entry.value <= amountUnused }
        if (actualNominals.isEmpty()) return
        val scale = actualNominals.entries.first()
        result[scale.key] = floor(amountUnused/scale.value).toInt()
        if(scale.value == 0.01)
            return
        getSplit(amountUnused - scale.value*floor(amountUnused/scale.value).toInt())
    }

    private fun getRandomSplit(amountUnused: Double){
        val actualNominals = nominalsRub.filter { entry -> entry.value <= amountUnused }
        if (actualNominals.isEmpty()) return
        val scale = actualNominals.entries.elementAt(Random.nextInt(actualNominals.entries.size))
        var scalesMax = floor(amountUnused/scale.value).toInt()
        if (scalesMax > 150)
            scalesMax = 150
        val times = Random.nextInt(scalesMax)
        resultRandom[scale.key] = (resultRandom[scale.key]?.plus(times) ?: times)
        if(amountUnused <= 0.02) {
            resultRandom["1коп"] = resultRandom["1коп"]!! + 2
            return
        }
        getRandomSplit(amountUnused - scale.value*times)
    }
    fun getSplit(){
        result.clear()
        getSplit(amount)
    }

    fun getRandomSplit(){
        resultRandom.clear()
        getRandomSplit(amount)
    }

    override fun toString(): String {
        return resultRandom.toString()
    }

    fun getResultSum(): Double {
        var result = 0.0
        for (item in resultRandom){
            val times = item.value
            val scale = nominalsRub.getOrElse(item.key) { 0.0 }
            result += scale * times
        }
        return result
    }
}