package com.undrown.nosmoking

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.fixedRateTimer


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
        timePassed.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Я не курю уже ${TimestampFormat(timeStart, Date().time).toVerbose()}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share via...")
            startActivity(shareIntent)
        }
        //TODO: show how to use saved money
        //TODO: show variants
        rubSaved.setOnClickListener {
            val visualizer = MoneyVisualizer(MoneySavedFormat(timeStart, Date().time).rubSaved)
            visualizer.getRandomSplit()
            Toast.makeText(this@MainActivity, visualizer.toString(), Toast.LENGTH_SHORT).show()
            moneyCanvas.setImageBitmap(visualizer.drawMoney(this))
        }
    }
}
