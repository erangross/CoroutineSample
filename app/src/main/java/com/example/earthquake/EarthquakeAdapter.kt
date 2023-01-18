package com.example.earthquake

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class EarthquakeAdapter(context: Context, earthquakes: ArrayList<Earthquake>) : ArrayAdapter<Earthquake>(context, 0, earthquakes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        val listItemView = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.earthquake_list_item, parent, false)

        // Find the earthquake at the given position in the list of earthquakes
        val currentEarthquake = getItem(position)

        // Find the TextView with view ID magnitude
        val magnitudeView = listItemView.findViewById<TextView>(R.id.magnitude)

        // Display the magnitude of the current earthquake in that TextView
        currentEarthquake?.magnitude?.also { magnitudeView.text = it }

        // Find the TextView with view ID location
        val locationView = listItemView.findViewById<TextView>(R.id.location)

        // Display the location of the current earthquake in that TextView
        currentEarthquake?.location?.also { locationView.text = it }

        // Find the TextView with view ID date
        val dateView = listItemView.findViewById<TextView>(R.id.date)
        // Display the date of the current earthquake in that TextView
        currentEarthquake?.date?.also { dateView.text = it }

        // Return the list item view that is now showing the appropriate data
        return listItemView
    }
}
