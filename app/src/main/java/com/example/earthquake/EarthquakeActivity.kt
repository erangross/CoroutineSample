package com.example.earthquake

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EarthquakeActivity : AppCompatActivity() {

    private lateinit var mAdapter: EarthquakeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)

        // Find a reference to the {@link ListView} in the layout
        val earthquakeListView = findViewById<ListView>(R.id.list)

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = EarthquakeAdapter(this, ArrayList())

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.adapter = mAdapter

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, l ->
            // Find the current earthquake that was clicked on
            val currentEarthquake = mAdapter.getItem(position)

            // Convert the String URL into a URI object (to pass into the Intent constructor)
            val earthquakeUri = Uri.parse(currentEarthquake?.url)

            // Create a new intent to view the earthquake URI
            val websiteIntent = Intent(Intent.ACTION_VIEW, earthquakeUri)

            // Send the intent to launch a new activity
            startActivity(websiteIntent)
        }

        // Start the AsyncTask to fetch the earthquake data
        val url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10"
        doBackgroundTask(url)
    }


    private fun doBackgroundTask(url: String) {
        // Start a coroutine in the main thread
        GlobalScope.launch(Dispatchers.Main) {
            // Perform a long-running task in the background
            val result = withContext(Dispatchers.IO) {
                // Suspend the coroutine until the task is completed
                withContext(Dispatchers.Default) {
                    // Suspend the coroutine until the task is completed
                    QueryUtils.fetchEarthquakeData(url)
                }
            }
            // Clear the adapter of previous earthquake data
            mAdapter.clear()

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (result.isNotEmpty()) {
                mAdapter.addAll(result)
            }
        }
    }

}
