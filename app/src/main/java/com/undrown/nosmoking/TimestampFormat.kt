package com.undrown.nosmoking

import kotlin.math.abs


class TimestampFormat(timeStart:Long, timeCur:Long) {
    private val timeDelta = timeCur - timeStart
    private var years:Long = 0L
    private var months:Long = 0L
    private var days:Long = 0L
    private var hours:Long = 0L
    private var minutes:Long = 0L
    private var seconds:Long = 0L

    //service values
    private val millisInSec = 1000L
    private val millisInMinute = 60*millisInSec
    private val millisInHour = 60*millisInMinute
    private val millisInDay = 24*millisInHour
    private val millisInMonth = 30*millisInDay
    private val millisInYear = 365*millisInDay

    init {
        timeDelta
            .getYears()
            .getMonths()
            .getDays()
            .getHours()
            .getMinutes()
            .getSeconds()
    }

    fun toVerbose():String{
        return "${vYear()}${vMonth()}${vDay()}${vHour()}${vMinute()}${vSecond()}"
    }

    private fun Long.getYears(): Long {
        years = this/millisInYear
        return this%millisInYear
    }
    private fun Long.getMonths(): Long {
        months = this/millisInMonth
        return this%millisInMonth
    }
    private fun Long.getDays(): Long {
        days = this/millisInDay
        return this%millisInDay
    }
    private fun Long.getHours(): Long {
        hours = this/millisInHour
        return this%millisInHour
    }
    private fun Long.getMinutes(): Long {
        minutes = this/millisInMinute
        return this%millisInMinute
    }
    private fun Long.getSeconds(): Long {
        seconds = this/millisInSec
        return this%millisInSec
    }
    //verbose functions

    private fun vYear():String{
        if(years == 0L) return ""
        val v = when(abs(years/10) % 10){
            1L -> "Лет"
            else -> {
                when(abs(years) % 10){
                    1L -> "Год"
                    2L, 3L, 4L -> "Года"
                    else -> {
                        "Лет"
                    }
                }
            }
        }
        return "$years $v, "
    }
    private fun vMonth():String{
        if(months == 0L) return ""
        val v = when(abs(months/10) % 10){
            1L -> "Месяцев"
            else -> {
                when(abs(months) % 10){
                    1L -> "Месяц"
                    2L, 3L, 4L -> "Месяца"
                    else -> {
                        "Месяцев"
                    }
                }
            }
        }
        return "$months $v, "
    }
    private fun vDay():String{
        if(days == 0L) return ""
        val v = when(abs(days/10) % 10){
            1L -> "Дней"
            else -> {
                when(abs(days) % 10){
                    1L -> "День"
                    2L, 3L, 4L -> "Дня"
                    else -> {
                        "Дней"
                    }
                }
            }
        }
        return "$days $v, "
    }
    private fun vHour():String{
        if(hours == 0L) return ""
        val v = when(abs(hours/10) % 10){
            1L -> "Часов"
            else -> {
                when(abs(hours) % 10){
                    1L -> "Час"
                    2L, 3L, 4L -> "Часа"
                    else -> {
                        "Часов"
                    }
                }
            }
        }
        return "$hours $v, "
    }
    private fun vMinute():String{
        if(minutes == 0L) return ""
        val v = when(abs(minutes/10) % 10){
            1L -> "Минут"
            else -> {
                when(abs(minutes) % 10){
                    1L -> "Минута"
                    2L, 3L, 4L -> "Минуты"
                    else -> {
                        "Минут"
                    }
                }
            }
        }
        return "$minutes $v, "
    }
    private fun vSecond():String{
        if(seconds == 0L) return ""
        val v = when(abs(seconds/10) % 10){
            1L -> "Секунд"
            else -> {
                when(abs(seconds) % 10){
                    1L -> "Секунда"
                    2L, 3L, 4L -> "Секунды"
                    else -> {
                        "Секунд"
                    }
                }
            }
        }
        return "$seconds $v"
    }
}