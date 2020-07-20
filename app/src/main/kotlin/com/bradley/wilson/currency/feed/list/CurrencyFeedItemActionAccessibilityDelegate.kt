package com.bradley.wilson.currency.feed.list

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.bradley.wilson.R

class CurrencyFeedItemActionAccessibilityDelegate(private val isBaseRate: Boolean) : AccessibilityDelegateCompat() {

    override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        if (!isBaseRate) {
            val click = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                AccessibilityNodeInfo.ACTION_CLICK,
                host.context.getString(R.string.currency_feed_accessibility_click_action_description)
            )
            info.addAction(click)
        }
    }
}
