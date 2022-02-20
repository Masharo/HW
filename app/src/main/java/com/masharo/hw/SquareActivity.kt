package com.masharo.hw

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleEventObserver

class SquareActivity : AppCompatActivity() {

    private var squareValue: TextView? = null
    private var buttonBack: Button? = null
    private var value: Int? = null

    companion object {
        const val VALUE = "value"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)

        squareValue = findViewById(R.id.square_textView_value)
        buttonBack = findViewById(R.id.main_button_backToMain)

        if (intent.hasExtra(VALUE)){
            value = intent.getIntExtra(VALUE, 0)
        }

        savedInstanceState?.let {
            if (it.containsKey(VALUE)) {
                value = it.getInt(VALUE)
            }

            if (it.containsKey(CountConfigChange.COUNT)) {
                CountConfigChange.count = it.getInt(CountConfigChange.COUNT)
            }
        }

        value?.let {
            squareValue?.text = (it * it).toString()
        }

        buttonBack?.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)

            startActivity(intent)
            finish()
        }

        lifecycle.addObserver(LifecycleEventObserver { _, b ->
            val logInfo = "${this::class.java.simpleName}: ${b.name}"
            LogLifecycle.print(applicationContext, b,"$logInfo\n")
            Log.i(LogLifecycle.MARKER_LOG, logInfo)
        })

        CountConfigChange.instanceSP(this)
        CountConfigChange.isConfigChange()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        value?.let {
            outState.putInt(VALUE, it)
        }

        outState.putInt(CountConfigChange.COUNT, CountConfigChange.count)

        super.onSaveInstanceState(outState)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        CountConfigChange.incCount()
        return super.onRetainCustomNonConfigurationInstance()
    }
}