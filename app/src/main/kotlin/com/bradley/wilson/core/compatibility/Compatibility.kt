package com.bradley.wilson.core.compatibility

import android.os.Build

class Compatibility {
    fun supportsAndroidVersion(versionCode: Int) = Build.VERSION.SDK_INT >= versionCode
}
