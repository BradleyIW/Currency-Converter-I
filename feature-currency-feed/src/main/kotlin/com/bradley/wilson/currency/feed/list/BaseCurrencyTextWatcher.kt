package com.bradley.wilson.currency.feed.list

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class BaseCurrencyTextWatcher(
    private val editText: EditText,
    private val afterTextChanged: (String) -> Unit
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged.invoke(s.toString())
        editText.setSelection(s.toString().length)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        editText.setSelection(s.toString().length)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        //Do nothing
    }

}
