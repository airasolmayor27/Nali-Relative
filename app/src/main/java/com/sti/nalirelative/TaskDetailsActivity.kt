package com.sti.nalirelative

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class TaskDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the back button in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val taskId = intent.getIntExtra("TASK_ID", -1)
        val taskTitle = intent.getStringExtra("TASK_TITLE")
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION")
        val tasklocation_ = intent.getStringExtra("TASK_LOCATION")
        val taskDirection = intent.getStringExtra("TASK_DIRECTION")

        // Use these details to populate your TaskDetailsActivity UI
        val titleTextView: TextView = findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        val locationTextView: TextView = findViewById(R.id.locationTextView)

        val buttonDirection: Button = findViewById(R.id.respondButton)

        titleTextView.text = taskTitle
        descriptionTextView.text = taskDescription
        locationTextView.text = tasklocation_
        buttonDirection.setOnClickListener {
            // Replace "https://www.example.com" with the actual URL you want to open
            val url = taskDirection
            Log.e("URL",url.toString())
//
//            // Create an Intent with ACTION_VIEW and the URI of the webpage
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            // Start the activity with the intent
            startActivity(intent)
        }

    }
    private fun showToast(message:String){
        Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
    }

    // Handle the back button press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}