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

    companion object {
        const val COUNT = "count"
        var count: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCount = findViewById(R.id.main_textView_count)
        buttonStartSquare = findViewById(R.id.main_button_startActivitySquare)

        savedInstanceState?.let {
            if (savedInstanceState.containsKey(COUNT)) {
                count = savedInstanceState.getInt(COUNT)
            }
        }

        buttonStartSquare?.setOnClickListener {
            val intent = Intent(applicationContext, SquareActivity::class.java)
            intent.putExtra(SquareActivity.VALUE, count)

            startActivity(intent)
        }

        if (isChangingConfigurations) {
            count++
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
        tvCount?.text = count.toString()

        super.onResume()
    }

    override fun onStop() {
        if (isChangingConfigurations) {
            count++
        }
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(COUNT, count)
        super.onSaveInstanceState(outState)
    }

}