package com.kalmar.finalapp

import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(city: City) {
        val cityTextView: TextView=itemView.findViewById(R.id.city_text_view)
        cityTextView.text=city.name

        val stateTextView:TextView=itemView.findViewById(R.id.state_text_view)
        stateTextView.text=city.state

        val toastButton: Button = itemView.findViewById(R.id.toast_button)
        toastButton.setOnClickListener {
            Toast.makeText(itemView.context, city.population.toString(), Toast.LENGTH_LONG).show()
        }
    }
}