package com.kalmar.finalapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val citiesList: List<City> = listOf(
            City("New York City", "New York", 8622357),
            City("Los Angeles", "California", 4085014),
            City("Chicago", "Illinois", 2670406),
            City("Houston", "Texas", 2378146),
            City("Phoenix", "Arizona", 1743469),
            City("Philadelphia", "Pennsylvania", 1590402),
            City("San Antonio", "Texas", 1579504),
            City("San Diego", "California", 1469490),
            City("Dallas","Texas", 1400337),
            City("San Jose", "California", 1036242)
        )
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val citiesRecyclerView : RecyclerView = view.findViewById(R.id.cities_recycler_view)
        citiesRecyclerView.layoutManager=
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        citiesRecyclerView.adapter=CityAdapter(citiesList)
        return view
    }
}