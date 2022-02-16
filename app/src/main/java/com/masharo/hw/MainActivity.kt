package com.masharo.hw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver

class MainActivity : AppCompatActivity() {

    companion object {
        private const val NAME_COUNT = "count"
    }

    private var count: Int = 0
    private var tvCount: TextView? = null
    private var buttonStartSquare: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCount = findViewById(R.id.main_textView_count)
        buttonStartSquare = findViewById(R.id.main_button_startActivitySquare)

        savedInstanceState?.let {
            count = it.getInt(NAME_COUNT, 0)
        }

        tvCount?.text = count.toString()
        buttonStartSquare?.setOnClickListener {
            val intent = Intent(applicationContext, SquareActivity::class.java)
            intent.putExtra(SquareActivity.VALUE, count)

            startActivity(intent)
        }

        lifecycle.addObserver(LifecycleEventObserver { _, b ->
            LogToFile.print(applicationContext, this::class.java.simpleName + " " + b.name + "\n")
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(NAME_COUNT, count + 1)
        super.onSaveInstanceState(outState)
    }
}