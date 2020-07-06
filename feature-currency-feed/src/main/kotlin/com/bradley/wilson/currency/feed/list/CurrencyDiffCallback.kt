package com.bradley.wilson.currency.feed.list

import androidx.recyclerview.widget.DiffUtil
import com.bradley.wilson.core.extensions.math.notEqualTo
import com.bradley.wilson.currency.feed.CurrencyItem

class CurrencyDiffCallback(
    private val oldCurrencyItems: List<CurrencyItem>,
    private val newCurrencyItems: List<CurrencyItem>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCurrencyItems[oldItemPosition].country == newCurrencyItems[newItemPosition].country

    override fun getOldListSize(): Int = oldCurrencyItems.size

    override fun getNewListSize(): Int = newCurrencyItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        when (isBaseCurrencyPosition(oldItemPosition) && isBaseCurrencyPosition(newItemPosition)) {
            true -> true
            else -> oldCurrencyItems[oldItemPosition] == newCurrencyItems[newItemPosition]
        }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        if (isBaseCurrencyPosition(oldItemPosition)) return null
        if (totalAmountOfItemHasChanged(oldItemPosition, newItemPosition)) return null
        return checkRatesAreTheSame(oldItemPosition, newItemPosition)
    }

    private fun checkRatesAreTheSame(oldItemPosition: Int, newItemPosition: Int): Any? {
        val newRate = newCurrencyItems[newItemPosition].rate
        val oldRate = oldCurrencyItems[oldItemPosition].rate
        return if (newRate.notEqualTo(oldRate)) newCurrencyItems[newItemPosition] else null
    }

    private fun totalAmountOfItemHasChanged(oldItemPosition: Int, newItemPosition: Int) =
        hasListSizeChanged(oldCurrencyItems, oldItemPosition) || hasListSizeChanged(newCurrencyItems, newItemPosition)

    private fun hasListSizeChanged(currencyList: List<CurrencyItem>, position: Int) = currencyList.size <= position

    private fun isBaseCurrencyPosition(position: Int) = position == BASE_CURRENCY_POSITION

    companion object {
        private const val BASE_CURRENCY_POSITION = 0
    }
}
