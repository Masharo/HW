package com.masharo.hw

import android.content.Context
import android.os.Bundle
import java.util.*

//До дедлайна часов 5, а я только что осознал что не так понял задание
//мммм... статика
object ConfigurationRegistrar {

    const val CONFIG_HEIGHT: String = "height"
    const val CONFIG_WIDTH: String = "width"
    const val CONFIG_LANGUAGE: String = "language"
    const val COUNT = "count"

    var count: Int = 0

    fun isConfigChange(baseContext: Context, bundle: Bundle) {

        if (bundle.containsKey(COUNT)) {
            count = bundle.getInt(COUNT)
        }

        if (bundle.containsKey(CONFIG_HEIGHT) &&
            bundle.containsKey(CONFIG_WIDTH) &&
            bundle.containsKey(CONFIG_LANGUAGE)
        ) {
            if (bundle.getInt(CONFIG_HEIGHT) !=
                baseContext.resources.displayMetrics.heightPixels ||

                bundle.getInt(CONFIG_WIDTH) !=
                baseContext.resources.displayMetrics.widthPixels ||

                bundle.getString(CONFIG_LANGUAGE) !==
                Locale.getDefault().language
            ) {
                count++
            }
        }
    }

}