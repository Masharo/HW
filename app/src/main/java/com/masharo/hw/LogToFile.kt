package com.masharo.hw

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileWriter

object LogToFile {

    private const val FILE_LOG: String = "Log.txt"

    fun print(context: Context, info: String) {
        val writer = FileWriter(File(context.filesDir, FILE_LOG), true)

        writer.write(info)
        writer.close()
    }

}