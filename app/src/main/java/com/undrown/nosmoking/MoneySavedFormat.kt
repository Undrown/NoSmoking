package com.undrown.nosmoking

import kotlin.math.floor
import kotlin.math.roundToInt

class MoneySavedFormat(timeStart:Long, timeCur:Long) {
    private val secDelta = (timeCur - timeStart)/1000              //секунд прошло
    private val rubSavedPerDay:Double = 20.0                                //рублей в сутки
    private val secInDay = 3600*24                                 //секунд в сутках
    private val rubSavedPerSec = rubSavedPerDay/secInDay            //экономия в секунду
    private val rubSaved = rubSavedPerSec * secDelta
    private val usdSaved = (rubSaved/16.5f)
    //right way:
    private val rub = floor(rubSaved).toInt()
    private val kop = floor((rubSaved - rub)*100).toInt()
    private val usd = floor(usdSaved).toInt()
    private val cent = ((usdSaved - usd)*100).toInt()
    //wrong way:
    //private val rub = if(rubSaved - rubSaved.roundToInt() > 0)rubSaved.roundToInt() else rubSaved.roundToInt() - 1
    //private val kop = ((rubSaved - rub)*100).roundToInt()
    //private val usd = if(usdSaved - usdSaved.roundToInt() > 0)usdSaved.roundToInt() else usdSaved.roundToInt() - 1
    //private val cent = ((usdSaved - usd)*100).roundToInt()

    val vRub = "$rub руб, $kop к."
    val vUSD = "$usd $, $cent c."
}