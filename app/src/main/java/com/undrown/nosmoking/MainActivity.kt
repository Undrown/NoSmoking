package com.undrown.nosmoking

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pref = this.getPreferences(Context.MODE_PRIVATE)
        var timeStart = pref.getLong("com.undrown.nosmoking.timestart", Date().time)
        fixedRateTimer("timer", false, 0L, 1000L){
            this@MainActivity.runOnUiThread {
                timePassed.text = getTimeStamp(timeStart)
                textMoneySaved.text = ((Date().time - timeStart)*(20/(3600*24))).toString()
            }
        }
        resetButton.setOnClickListener {
            with(pref.edit()){
                putLong("com.undrown.nosmoking.timestart", Date().time)
                apply()
            }
            timeStart = pref.getLong("com.undrown.nosmoking.timestart", Date().time)
        }
    }

    private fun getTimeStamp(timeStart:Long):String{
        val timeDelta = Date().time - timeStart
        var timeDeltaInMillisRest = timeDelta
        //days
        var diff = TimeUnit.DAYS.convert(timeDeltaInMillisRest, TimeUnit.MILLISECONDS)
        var diffUsed = TimeUnit.DAYS.toMillis(diff)
        timeDeltaInMillisRest -= diffUsed
        //test
        val years = diff/365
        var daysRest = diff%365
        val months = daysRest/30
        daysRest %= 30
        val yearsVerbose = when(abs(years/10) % 10){
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
        val monthsVerbose = when(abs(months/10) % 10){
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
        var ymd = ""
        if(years != 0L){
            ymd += "$years $yearsVerbose, "
        }
        if(months != 0L){
            ymd += "$months $monthsVerbose, "
        }
        //ymd = "$years $yearsVerbose, $months $monthsVerbose, "
        ////////////////////////////test
        val daysVerbose = when(abs(daysRest/10) % 10){
            1L -> "Дней"
            else -> {
                when(abs(daysRest) % 10){
                    1L -> "День"
                    2L, 3L, 4L -> "Дня"
                    else -> {
                        "Дней"
                    }
                }
            }
        }
        val days = if(daysRest != 0L)"$daysRest $daysVerbose, " else ""
        //hours
        diff = TimeUnit.HOURS.convert(timeDeltaInMillisRest, TimeUnit.MILLISECONDS)
        diffUsed = TimeUnit.HOURS.toMillis(diff)
        timeDeltaInMillisRest -= diffUsed
        val hoursVerbose = when(abs(diff/10) % 10){
            1L -> "Часов"
            else -> {
                when(abs(diff) % 10){
                    1L -> "Час"
                    2L, 3L, 4L -> "Часа"
                    else -> {
                        "Часов"
                    }
                }
            }
        }
        val hours = if(diff != 0L)"$diff $hoursVerbose, " else ""
        //minutes
        diff = TimeUnit.MINUTES.convert(timeDeltaInMillisRest, TimeUnit.MILLISECONDS)
        diffUsed = TimeUnit.MINUTES.toMillis(diff)
        timeDeltaInMillisRest -= diffUsed
        val minutesVerbose = when(abs(diff/10) % 10){
            1L -> "Минут"
            else -> {
                when(abs(diff) % 10){
                    1L -> "Минуту"
                    2L, 3L, 4L -> "Минуты"
                    else -> {
                        "Минут"
                    }
                }
            }
        }
        val minutes = if (diff!=0L) "$diff $minutesVerbose, " else ""
        //seconds
        diff = TimeUnit.SECONDS.convert(timeDeltaInMillisRest, TimeUnit.MILLISECONDS)
        diffUsed = TimeUnit.SECONDS.toMillis(diff)
        timeDeltaInMillisRest -= diffUsed
        val secondsVerbose = when(abs(diff/10) % 10){
            1L -> "Секунд"
            else -> {
                when(abs(diff) % 10){
                    1L -> "Секунду"
                    2L, 3L, 4L -> "Секунды"
                    else -> {
                        "Секунд"
                    }
                }
            }
        }
        val seconds = "$diff $secondsVerbose"
        return "$ymd $days $hours $minutes $seconds"
    }
}
