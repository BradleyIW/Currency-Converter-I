package com.bradley.wilson.currency.feed.formatting

import android.os.Build
import com.bradley.wilson.core.compatibility.Compatibility
import java.util.Locale

object CurrencyLocaleRetriever {
    fun getDefaultLocale(
        compatibility: Compatibility = Compatibility()
    ): Locale =
        if (compatibility.supportsAndroidVersion(Build.VERSION_CODES.N)) {
            Locale.getDefault(Locale.Category.FORMAT)
        } else {
            Locale.getDefault()
        }
}
