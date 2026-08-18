package com.conding.cybersecurise.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.develop.cybersecurise.R
import com.develop.cybersecurise.models.CardData

class CardAdapter(private val context: Context, private val dataList: List<CardData>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_card_custom, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.iconView.setImageDrawable(currentItem.icon)
        holder.textViewTitle.text = currentItem.title
        holder.textViewDescription.text = currentItem.description


        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = dataList.size

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val iconView: ImageView = itemView.findViewById(R.id.icon_box)
    }
}