package com.bradley.wilson.currency.feed.list

import androidx.recyclerview.widget.DiffUtil
import com.bradley.wilson.core.extensions.primitives.notEqualTo
import com.bradley.wilson.currency.usecase.Currency

class CurrencyDiffCallback(
    private val oldCurrencyItems: List<Currency>,
    private val newCurrencyItems: List<Currency>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCurrencyItems[oldItemPosition].country == newCurrencyItems[newItemPosition].country

    override fun getOldListSize(): Int = oldCurrencyItems.size

    override fun getNewListSize(): Int = newCurrencyItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCurrencyItems[oldItemPosition] == newCurrencyItems[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        if (oldCurrencyItems.size <= oldItemPosition || newCurrencyItems.size <= newItemPosition) return null
        val newCurrencyItem = newCurrencyItems[newItemPosition]
        val oldCurrencyItem = oldCurrencyItems[oldItemPosition]
        return if (newCurrencyItem.rate.notEqualTo(oldCurrencyItem.rate)) {
            newCurrencyItem.rate
        } else null
    }
}
