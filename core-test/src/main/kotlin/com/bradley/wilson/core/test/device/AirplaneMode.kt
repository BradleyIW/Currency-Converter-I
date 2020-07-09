package com.bradley.wilson.core.test.device

import android.content.Intent
import android.provider.Settings
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until


class AirplaneMode private constructor() {
    companion object {
        @JvmStatic
        fun setAirplaneMode(enable: Boolean) {
            if ((if (enable) 1 else 0) == Settings.System.getInt(
                    getInstrumentation().context.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON, 0
                )
            ) {
                return
            }
            val device = UiDevice.getInstance(getInstrumentation())
            device.openQuickSettings()

            val description = By.desc("Airplane mode")
            // Need to wait for the button, as the opening of quick settings is animated.
            device.wait(Until.hasObject(description), 500)
            device.findObject(description).click()
            getInstrumentation().context.sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
        }
    }
}