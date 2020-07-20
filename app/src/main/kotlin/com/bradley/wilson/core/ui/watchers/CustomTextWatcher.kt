package com.bradley.wilson.core.ui.watchers

import android.text.Editable
import android.text.TextWatcher

class CustomTextWatcher(
    private val beforeTextChanged: (CharSequence?, Int, Int, Int) -> Unit = { _: CharSequence?, _: Int, _: Int, _: Int -> },
    private val onTextChanged: (CharSequence?, Int, Int, Int) -> Unit = { _: CharSequence?, _: Int, _: Int, _: Int -> },
    private val afterTextChanged: (String) -> Unit
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged.invoke(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged.invoke(s, start, before, count)
    }
}
