package com.bradley.wilson.currency.feed.list

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bradley.wilson.R
import com.bradley.wilson.core.extensions.math.equalsZero
import com.bradley.wilson.core.extensions.math.notEqualTo
import com.bradley.wilson.core.extensions.primitives.empty
import com.bradley.wilson.core.ui.watchers.CustomTextWatcher
import com.bradley.wilson.currency.feed.CurrencyItem
import com.bradley.wilson.currency.feed.flags.CurrencyFlags
import com.bradley.wilson.currency.feed.formatting.CurrencyFormatter
import kotlinx.android.synthetic.main.item_view_currency_feed.view.*
import java.math.BigDecimal

class CurrencyFeedRecyclerAdapter : RecyclerView.Adapter<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>() {

    private var currencyFeedItems = mutableListOf<CurrencyItem>()

    private lateinit var onItemClicked: (currencyItem: CurrencyItem) -> Unit

    private lateinit var onRateChanged: (currencyItem: CurrencyItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyFeedViewHolder =
        CurrencyFeedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_currency_feed, parent, false)
        )

    override fun getItemCount(): Int = currencyFeedItems.size

    override fun onBindViewHolder(holder: CurrencyFeedViewHolder, position: Int) =
        holder.bindAll(currencyFeedItems[position])

    override fun onBindViewHolder(
        holder: CurrencyFeedViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when {
            payloads.isNotEmpty() -> (payloads.first() as CurrencyItem)
                .also { holder.bindInputSpecs(it) }
            else -> onBindViewHolder(holder, position)
        }
    }

    fun rateChanged(onRateChanged: (currencyItem: CurrencyItem) -> Unit) {
        this.onRateChanged = onRateChanged
    }

    fun itemClicked(onItemClicked: (currencyItem: CurrencyItem) -> Unit) {
        this.onItemClicked = onItemClicked
    }

    fun updateList(latestRates: List<CurrencyItem>) {
        val difference = DiffUtil.calculateDiff(CurrencyDiffCallback(currencyFeedItems, latestRates))
        difference.dispatchUpdatesTo(this)

        currencyFeedItems.clear()
        currencyFeedItems.addAll(latestRates)
    }

    inner class CurrencyFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val currencyAmount = itemView.findViewById<EditText>(R.id.currency_feed_item_view_currency_input)

        private val currencyFormatter by lazy { CurrencyFormatter() }

        private val baseCurrencyTextWatcher = CustomTextWatcher {
            currencyAmount.setSelection(it.length)
            val currencyItem = currencyFeedItems[adapterPosition]
            if (it.isEmpty()) {
                onRateChanged(currencyItem.copy(rate = BigDecimal.ZERO))
            } else {
                val newRate = currencyFormatter.formatCurrencyToRate(it)
                if (currencyItem.rate.notEqualTo(newRate)) {
                    onRateChanged(currencyItem.copy(rate = newRate))
                }
            }
        }

        fun bindAll(currencyItem: CurrencyItem) {
            with(itemView) {
                ViewCompat.setAccessibilityDelegate(this, CurrencyFeedItemActionAccessibilityDelegate(currencyItem.isBateRate))

                val currency = currencyFormatter.currency(currencyItem.country)
                currency_feed_item_view_currency_code.text = currencyItem.country
                currency_feed_item_view_currency_display_name.text = currency?.displayName ?: String.empty()
                currency_feed_item_view_flag.text = CurrencyFlags.getFlagEmojiForCurrency(currency)

                bindInputSpecs(currencyItem)

                if (!currencyItem.isBateRate) setOnClickListener { onItemClicked() }
            }
        }

        private fun defineContentDescription(view: View, baseCurrencyItem: CurrencyItem, currencyItem: CurrencyItem) {
            val regularRate = currencyFormatter.formatRateToCurrency(currencyItem.rate)
            val baseRate = currencyFormatter.formatRateToCurrency(baseCurrencyItem.rate)
            view.contentDescription = when (layoutPosition) {
                0 -> {
                    view.context.getString(R.string.base_currency_content_description, displayName(baseCurrencyItem), baseRate)
                }
                else -> view.context.getString(
                    R.string.regular_currency_content_description,
                    displayName(currencyItem), regularRate,
                    displayName(baseCurrencyItem), baseRate
                )
            }
        }

        fun bindInputSpecs(currencyItem: CurrencyItem) {
            with(currencyAmount) {
                val isBaseItem = currencyItem.isBateRate
                inputType = if (isBaseItem) {
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                } else {
                    InputType.TYPE_NULL
                }
                isFocusableInTouchMode = isBaseItem
                isFocusable = isBaseItem
                isClickable = isBaseItem
                movementMethod = if (isBaseItem) movementMethod else null
                keyListener = if (isBaseItem) keyListener else null
            }
            bindRate(currencyItem)
        }

        private fun bindRate(currencyItem: CurrencyItem) {
            defineContentDescription(itemView, currencyFeedItems.first(), currencyItem)
            with(currencyAmount) {
                removeTextChangedListener(baseCurrencyTextWatcher)
                setCurrencyText(currencyItem)
                addTextChangedListener(baseCurrencyTextWatcher)
            }
        }

        private fun setCurrencyText(currencyItem: CurrencyItem) {
            currencyAmount.setText(
                if (currencyItem.rate.equalsZero()) {
                    String.empty()
                } else {
                    currencyFormatter.formatRateToCurrency(currencyItem.rate)
                }
            )
        }

        private fun displayName(currencyItem: CurrencyItem) =
            currencyFormatter.currency(currencyItem.country)?.displayName ?: currencyItem.country

        private fun onItemClicked() {
            onItemClicked(currencyFeedItems[layoutPosition])
        }
    }
}
