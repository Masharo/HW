package com.masharo.hw

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleEventObserver
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvCount: TextView? = null
    private var buttonStartSquare: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCount = findViewById(R.id.main_textView_count)
        buttonStartSquare = findViewById(R.id.main_button_startActivitySquare)

        savedInstanceState?.let {
            ConfigurationRegistrar.isConfigChange(baseContext, it)
        }

        buttonStartSquare?.setOnClickListener {
            val intent = Intent(applicationContext, SquareActivity::class.java)
            intent.putExtra(SquareActivity.VALUE, ConfigurationRegistrar.count)

            startActivity(intent)
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

    //при возврате со 2 активности не обновлялось число, так что теперь оно тут а не в create
    override fun onResume() {
        tvCount?.text = ConfigurationRegistrar.count.toString()

        super.onResume()
    }

    //сохраняем данные для проверки изменилась ли конфигурации
    //не знаю есть ли еще что то что можно назвать изменением конфигурации кроме поворота экрана,
    //изменения размера и языка
    //не будет инкрементироваться при повороте если экран квадратный, но я думаю что и не должно
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ConfigurationRegistrar.COUNT, ConfigurationRegistrar.count)
        outState.putInt(ConfigurationRegistrar.CONFIG_HEIGHT, baseContext.resources.displayMetrics.heightPixels)
        outState.putInt(ConfigurationRegistrar.CONFIG_WIDTH, baseContext.resources.displayMetrics.widthPixels)
        outState.putString(ConfigurationRegistrar.CONFIG_LANGUAGE, Locale.getDefault().language)

        super.onSaveInstanceState(outState)
    }

}