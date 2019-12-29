package com.undrown.nosmoking

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import org.jetbrains.annotations.TestOnly
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
        "50c" to 0.5,
        "25c" to 0.25,
        "10c" to 0.10,
        "5c" to 0.05,
        "1c" to 0.01
        )
    val images = mapOf(
        "500ka" to listOf(
            R.drawable.img_500,
            R.drawable.img_500r,
            R.drawable.img_new_500,
            R.drawable.img_new_500r
        ),
        "200ka" to listOf(
            R.drawable.img_200,
            R.drawable.img_200r,
            R.drawable.img_new_200,
            R.drawable.img_new_200r
        ),
        "100ka" to listOf(
            R.drawable.img_100,
            R.drawable.img_100r,
            R.drawable.img_new_100,
            R.drawable.img_new_100r
        ),
        "50ka" to listOf(
            R.drawable.img_50,
            R.drawable.img_50r,
            R.drawable.img_new_50,
            R.drawable.img_new_50r
        ),
        "25ka" to listOf(
            R.drawable.img_25,
            R.drawable.img_25r,
            R.drawable.img_new_25,
            R.drawable.img_new_25r
        ),
        "10ka" to listOf(
            R.drawable.img_10,
            R.drawable.img_10r,
            R.drawable.img_new_10,
            R.drawable.img_new_10r
        ),
        "5ka" to listOf(
            R.drawable.img_5,
            R.drawable.img_5r,
            R.drawable.img_new_5,
            R.drawable.img_new_5r
        ),
        "1ka" to listOf(
            R.drawable.img_1,
            R.drawable.img_1r,
            R.drawable.img_new_1,
            R.drawable.img_new_1r
        ),
        "50c" to listOf(
            R.drawable.img_05,
            R.drawable.img_05r,
            R.drawable.img_05,
            R.drawable.img_05r
        ),
        "25c" to listOf(
            R.drawable.img_025,
            R.drawable.img_025r,
            R.drawable.img_025,
            R.drawable.img_025r
        ),
        "10c" to listOf(
            R.drawable.img_01,
            R.drawable.img_01r,
            R.drawable.img_01,
            R.drawable.img_01r
        ),
        "5c" to listOf(
            R.drawable.img_005,
            R.drawable.img_005r,
            R.drawable.img_005,
            R.drawable.img_005r
        ),
        "1c" to listOf(
            R.drawable.img_001,
            R.drawable.img_001r,
            R.drawable.img_001,
            R.drawable.img_001r
        )
    )
    val paint = Paint()
    val result = mutableMapOf<String, Int>()
    val resultRandom = mutableMapOf(
        "500ka" to 0,
        "200ka" to 0,
        "100ka" to 0,
        "50ka" to 0,
        "25ka" to 0,
        "10ka" to 0,
        "5ka" to 0,
        "1ka" to 0,
        "50c" to 0,
        "25c" to 0,
        "10c" to 0,
        "5c" to 0,
        "1c" to 0
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
            resultRandom["1c"] = resultRandom["1c"]!! + 2
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

    @TestOnly
    fun getResultSum(): Double {
        var result = 0.0
        for (item in resultRandom){
            val times = item.value
            val scale = nominalsRub.getOrElse(item.key) { 0.0 }
            result += scale * times
        }
        return result
    }

    fun drawMoney(context: Context):Bitmap{
        val width = 400
        val height = 400
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        while(resultRandom.filter { item -> item.value > 0 }.isNotEmpty()){
            for (item in resultRandom.filter { item -> item.value > 0 }){
                if (item.value > 0){
                    resultRandom[item.key] = item.value - 1
                    val img = images[item.key]
                    var bmp = BitmapFactory.decodeResource(context.resources, img!![Random.nextInt(4)])
                    if(bmp == null){
                        println("null")
                        bmp = BitmapFactory.decodeResource(context.resources, R.drawable.img_001)
                    }
                    drawBitmap(canvas, bmp)
                }
            }
        }
        return bitmap
    }

    private fun drawBitmap(canvas:Canvas, bitmap: Bitmap){
        val matrix = Matrix()
        matrix.reset()
        val degrees = Random.nextFloat()*360
        canvas.rotate(degrees)
        val x = Random.nextFloat()*(canvas.width - 100)
        val y = Random.nextFloat()*(canvas.height - 50)
        matrix.setTranslate(x, y)
        canvas.drawBitmap(bitmap, matrix, paint)
        canvas.rotate(-degrees)
    }
}