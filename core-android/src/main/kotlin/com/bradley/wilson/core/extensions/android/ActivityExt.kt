package com.bradley.wilson.core.extensions.android

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(
    frameId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true
) = supportFragmentManager.inTransaction {
    replace(frameId, fragment).also {
        if (addToBackStack) {
            it.addToBackStack(fragment.tag)
        }
    }
}