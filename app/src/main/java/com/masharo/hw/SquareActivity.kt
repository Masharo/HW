package com.masharo.hw

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleEventObserver

class SquareActivity : AppCompatActivity() {

    private var squareValue: TextView? = null
    private var value: Int? = null

    companion object {
        const val VALUE = "value"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)

        squareValue = findViewById(R.id.square_textView_value)

        if (intent.hasExtra(VALUE)){
            value = intent.getIntExtra(VALUE, 0)
        }

        savedInstanceState?.let {
            if (it.containsKey(VALUE)) {
                value = it.getInt(VALUE)
            }
        }

        value?.let {
            squareValue?.text = (it * it).toString()
        }

        lifecycle.addObserver(LifecycleEventObserver { _, b ->
            LogToFile.print(applicationContext, this::class.java.simpleName + " " + b.name + "\n")
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        value?.let {
            outState.putInt(VALUE, it)
        }

        super.onSaveInstanceState(outState)
    }
}