package com.undrown.nosmoking

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.fixedRateTimer
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var timeStart =
            this.getPreferences(Context.MODE_PRIVATE)
                .getLong("com.undrown.nosmoking.timestart", Date().time)
        fixedRateTimer("timer", false, 0L, 1000L){
            this@MainActivity.runOnUiThread {
                val timeCur = Date().time
                val moneySaved = MoneySavedFormat(timeStart, timeCur)
                timePassed.text = TimestampFormat(timeStart, timeCur).toVerbose()
                rubSaved.text = moneySaved.vRub
                dollarsSaved.text = moneySaved.vUSD
            }
        }
        resetButton.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            with(builder){
                setMessage("Желаете сбросить?")
                setNegativeButton("Отмена"){_, _ ->
                    return@setNegativeButton
                }
                setPositiveButton("Ок"){_, _ ->
                    with(this@MainActivity.getPreferences(Context.MODE_PRIVATE).edit()){
                        putLong("com.undrown.nosmoking.timestart", Date().time)
                        apply()
                    }
                    timeStart = this@MainActivity
                        .getPreferences(Context.MODE_PRIVATE)
                        .getLong("com.undrown.nosmoking.timestart", Date().time)
                }
                create().show()
            }
        }
        //resetButton.isVisible = false
    }
}
