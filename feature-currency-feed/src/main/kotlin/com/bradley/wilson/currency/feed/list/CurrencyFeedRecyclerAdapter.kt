package com.bradley.wilson.currency.feed.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bradley.wilson.currency.R
import com.bradley.wilson.currency.usecase.Currency
import kotlinx.android.synthetic.main.item_view_currency_feed.view.*

class CurrencyFeedRecyclerAdapter :
    RecyclerView.Adapter<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>() {

    private var currencyFeedItems = mutableListOf<Currency>()

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
            payloads.isNotEmpty() -> (payloads.first() as Double)
                .also {
                    holder.bindRate("$it")
                }
            else -> onBindViewHolder(holder, position)
        }
    }

    fun updateList(updatedCurrencyFeedItems: List<Currency>) {
        val difference = DiffUtil.calculateDiff(
            CurrencyDiffCallback(currencyFeedItems, updatedCurrencyFeedItems)
        )
        currencyFeedItems.clear()
        currencyFeedItems.addAll(updatedCurrencyFeedItems)
        difference.dispatchUpdatesTo(this)
    }

    inner class CurrencyFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindAll(currency: Currency) {
            with(itemView) {
                currency_title.text = currency.country
                currency_amount.text = "${currency.rate}"
            }
        }

        fun bindRate(rate: String) {
            with(itemView) {
                currency_amount.text = rate
            }
        }
    }
}
