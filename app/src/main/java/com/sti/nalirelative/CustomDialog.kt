package com.sti.nalirelative
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
class CustomDialog(context: Context, private val link: String) : Dialog(context) {

    private lateinit var titleTextView: TextView
    private lateinit var closeButton: Button
    private lateinit var openLinkButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog)
        // Set the window size
        val window = window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        titleTextView = findViewById(R.id.dialogTitle)
        closeButton = findViewById(R.id.dialogButton)
        openLinkButton = findViewById(R.id.openLinkButton)
        titleTextView.text = "Custom Dialog Title"
        closeButton.setOnClickListener {
            dismiss()
        }
        openLinkButton.setOnClickListener {
            // Open the link here (e.g., using Intent to open a web page)
            openLink(link)
        }
    }

    private fun openLink(link: String) {
        // You need to implement the logic to open the link here
        // For example, you can use an Intent to open a web page
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(intent)
    }
    // You can add more methods or modify the behavior as needed
    fun setDialogTitle(title: String) {
        titleTextView.text = title
    }

    fun setDialogDetails(details: String) {
        val detailsTextView: TextView = findViewById(R.id.dialogDetails)
        detailsTextView.text = details
    }

    fun setDialogLocation(location: String) {
        val locationTextView: TextView = findViewById(R.id.dialogLocation)
        locationTextView.text = location
    }

    fun setDialogMessage(message: String) {
        val messageTextView: TextView = findViewById(R.id.dialogMessage)
        messageTextView.text = message
    }

    fun setDialogDate(nalidate: String) {
        val dateTextView: TextView = findViewById(R.id.dialogDate)
        dateTextView.text = nalidate
    }

    fun setOnCloseButtonClickListener(listener: () -> Unit) {
        closeButton.setOnClickListener {
            listener.invoke()
            dismiss()
        }
    }
}

