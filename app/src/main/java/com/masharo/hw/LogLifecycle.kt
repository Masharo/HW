package com.masharo.hw

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import java.io.File
import java.io.FileWriter

object LogLifecycle {

    private const val FILE_LOG: String = "Log.txt"
    const val MARKER_LOG = "lifecycleLog"
    private var writer: FileWriter? = null

    fun print(context: Context, event: Lifecycle.Event, info: String) {

        try {

            if (writer == null) {
                writer = FileWriter(File(context.filesDir, FILE_LOG), true)
            }

            writer?.write(info)

        } catch (ex: Exception) {
            //Ничего не делаем
        }

        when (event) {
            ON_STOP -> {
                close()
            }
            ON_DESTROY -> {
                close()
            }
        }
    }

    private fun close() {
        writer?.close()
        writer = null
    }

}