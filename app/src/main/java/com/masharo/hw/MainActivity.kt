package com.masharo.hw

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleEventObserver

class MainActivity : AppCompatActivity() {

    private var tvCount: TextView? = null
    private var buttonStartSquare: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCount = findViewById(R.id.main_textView_count)
        buttonStartSquare = findViewById(R.id.main_button_startActivitySquare)

        savedInstanceState?.let {
            if (it.containsKey(CountConfigChange.COUNT) &&
                CountConfigChange.countNotValid()
            ) {
                CountConfigChange.count = it.getInt(CountConfigChange.COUNT)
            }
        }

        CountConfigChange.instanceSP(this)
        CountConfigChange.isConfigChange()

        buttonStartSquare?.setOnClickListener {
            val intent = Intent(applicationContext, SquareActivity::class.java)
            intent.putExtra(SquareActivity.VALUE, CountConfigChange.count)

            startActivity(intent)
            finish()
        }

        //В презентации с лекции написано в logcat,
        //а в основной в фаил ¯\_(ツ)_/¯
        //https://youtu.be/VeD1x3weUgc?t=3212
        lifecycle.addObserver(LifecycleEventObserver { _, b ->
            val logInfo = "${this::class.java.simpleName}: ${b.name}"
            LogLifecycle.print(applicationContext, b,"$logInfo\n")
            Log.i(LogLifecycle.MARKER_LOG, logInfo)
        })
    }

    override fun onResume() {
        tvCount?.text = CountConfigChange.count.toString()

        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CountConfigChange.COUNT, CountConfigChange.count)
        super.onSaveInstanceState(outState)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        CountConfigChange.incCount()
        return super.onRetainCustomNonConfigurationInstance()
    }
}