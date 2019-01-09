package com.sserra.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_layout.view.*
import kotlinx.android.synthetic.main.recycler_activity.*

class RecyclerViewActivity : AppCompatActivity() {

    companion object {
        fun start(ctx: Context) = ctx.startActivity(Intent(ctx, RecyclerViewActivity::class.java))
    }

    class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CitiesHolder>() {

        private var cities: List<String> = listOf(
                "Albania", "Andorra", "Armenia", "Austria", "Azerbaijan", "Belarus", "Belgium",
                "Bosnia and Herzegovina", "Bulgaria", "Croatia", "Cyprus", "Czech", "Republic",
                "Denmark", "Estonia", "Finland", "France", "Georgia", "Germany", "Greece", "Hungary",
                "Iceland", "Ireland", "Italy", "Kazakhstan", "Kosovo", "Latvia", "Liechtenstein", "Lithuania",
                "Luxembourg", "Macedonia", "Malta", "Moldova", "Montenegro", "Netherlands",
                "Norway", "Poland", "Portugal", "Romania", "Russia", "San Marino", "Serbia", "Slovakia",
                "Slovenia", "Spain", "Sweden", "Switzerland", "Turkey", "Ukraine", "United Kingdom"
        )

        class CitiesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(city: String) {
                itemView.name.text = city
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesHolder {
            val inflater = LayoutInflater.from(parent.context)
            return CitiesHolder(inflater.inflate(R.layout.city_layout, parent, false))
        }

        override fun getItemCount(): Int = cities.size

        override fun onBindViewHolder(holder: CitiesHolder, position: Int) {
            holder.bind(cities[position])
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_activity)

        list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CitiesAdapter()
        }
    }
}