package com.bradley.wilson.currency.feed.list

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bradley.wilson.core.extensions.android.isFirstItem
import com.bradley.wilson.core.extensions.primitives.empty
import com.bradley.wilson.core.extensions.primitives.notEqualTo
import com.bradley.wilson.core.extensions.primitives.toCurrencyRate
import com.bradley.wilson.currency.R
import com.bradley.wilson.currency.usecase.CurrencyItem
import kotlinx.android.synthetic.main.item_view_currency_feed.view.*
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class CurrencyFeedRecyclerAdapter :
    RecyclerView.Adapter<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>() {

    private var currencyFeedItems = mutableListOf<CurrencyItem>()

    private lateinit var onItemClicked: (currencyItem: CurrencyItem) -> Unit

    private lateinit var onRateChanged: (currencyItem: CurrencyItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyFeedViewHolder =
        CurrencyFeedViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_currency_feed,
                parent,
                false
            )
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
                .also { holder.bindRate(it) }
            else -> onBindViewHolder(holder, position)
        }
    }

    fun rateChanged(onRateChanged: (currencyItem: CurrencyItem) -> Unit) {
        this.onRateChanged = onRateChanged
    }

    fun itemClicked(onItemClicked: (currencyItem: CurrencyItem) -> Unit) {
        this.onItemClicked = onItemClicked
    }

    fun updateList(updatedCurrencyFeedItems: List<CurrencyItem>) {
        val difference = DiffUtil.calculateDiff(
            CurrencyDiffCallback(currencyFeedItems, updatedCurrencyFeedItems)
        )
        //Must dispatch before we update values
        difference.dispatchUpdatesTo(this)
        currencyFeedItems.clear()
        currencyFeedItems.addAll(updatedCurrencyFeedItems)

    }

    inner class CurrencyFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val currencyAmount = itemView.findViewById<EditText>(R.id.currency_amount)
        private val baseCurrencyTextWatcher = BaseCurrencyTextWatcher(currencyAmount) {
            val currencyItem = currencyFeedItems[adapterPosition]
            if (it.isEmpty()) {
                onRateChanged(currencyItem.copy(rate = 0.00))
            } else {
                val newRate = it.toCurrencyRate()
                if (currencyItem.rate.notEqualTo(newRate)) {
                    onRateChanged(currencyItem.copy(rate = newRate))
                }
            }
        }

        fun bindAll(currency: CurrencyItem) {
            with(itemView) {
                currency_title.text = currency.country
                bindRate(currency)
                setOnClickListener { moveToTop() }
            }
        }

        fun bindRate(currency: CurrencyItem) {
            with(currencyAmount) {
                inputType = if (isFirstItem()) {
                    InputType.TYPE_NUMBER_FLAG_DECIMAL
                } else {
                    InputType.TYPE_NULL
                }
                removeTextChangedListener(baseCurrencyTextWatcher)
                setRateText(currency)
                if (isFirstItem()) {
                    addTextChangedListener(baseCurrencyTextWatcher)
                }
            }
        }

        private fun moveToTop() {
            adapterPosition.takeIf { it > 0 }?.also { position ->
                onItemClicked(currencyFeedItems[position])
            }
        }

        private fun setRateText(currencyItem: CurrencyItem) {
            val currencyFormatter = NumberFormat.getCurrencyInstance()
            val currency = Currency.getInstance(currencyItem.country)
            currencyFormatter.currency = currency
            itemView.currency_description.text = currency.displayName
            if (isFirstItem()) {
                currencyAmount.setText(
                    currencyFormatter.format(currencyItem.rate).replace(currency.symbol, String.empty())
                )
            } else {
                currencyFormatter.maximumFractionDigits = 2
                currencyFormatter.roundingMode = RoundingMode.FLOOR
                currencyAmount.setText(
                    currencyFormatter.format(currencyItem.rate).replace(currency.symbol, String.empty()),
                    TextView.BufferType.EDITABLE
                )
            }
        }
    }
}
