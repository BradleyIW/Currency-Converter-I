package com.bradley.wilson.core.test.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.bradley.wilson.core.test.TestApplication

class EspressoRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application =
        super.newApplication(cl, TestApplication::class.java.name, context)
}