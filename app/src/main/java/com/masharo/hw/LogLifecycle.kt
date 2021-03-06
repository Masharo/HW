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

            writer?.let {
                it.write(info)

                when (event) {
                    ON_STOP, ON_DESTROY -> {
                        it.close()
                        writer = null
                    }
                }
            }

        } catch (ex: Exception) {
            //Ничего не делаем
        }
    }

}