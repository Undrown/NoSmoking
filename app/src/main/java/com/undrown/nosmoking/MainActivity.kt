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
    private val pref = this.getPreferences(Context.MODE_PRIVATE)
    private var timeStart = pref.getLong("com.undrown.nosmoking.timestart", Date().time)
    private var time = Date().time
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fixedRateTimer("timer", false, 0L, 1000L){
            this@MainActivity.runOnUiThread {
                timePassed.text = getTimeStamp(timeStart)
                //textMoneySaved.text = ((timeStart - time)*(20/(3600*24))).toString()
            }
        }
        resetButton.setOnClickListener {
            with(this.getPreferences(Context.MODE_PRIVATE).edit()){
                putLong("com.undrown.nosmoking.timestart", Date().time)
                apply()
            }
            timeStart = time
        }
    }

    private fun getTimeStamp(timeStart:Long):String{
        val timeDelta = Date().time - timeStart
        var timeDeltaInMillisRest = timeDelta
        //days
        var diff = TimeUnit.DAYS.convert(timeDeltaInMillisRest, TimeUnit.MILLISECONDS)
        var diffUsed = TimeUnit.DAYS.toMillis(diff)
        timeDeltaInMillisRest -= diffUsed
        val daysVerbose = when(abs(diff/10) % 10){
            1L -> "Дней"
            else -> {
                when(abs(diff) % 10){
                    1L -> "День"
                    2L, 3L, 4L -> "Дня"
                    else -> {
                        "Дней"
                    }
                }
            }
        }
        val days = "$diff $daysVerbose, "
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
        val hours = "$diff $hoursVerbose, "
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
        val minutes ="$diff $minutesVerbose, "
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
        return "$days $hours $minutes $seconds"
    }
}
