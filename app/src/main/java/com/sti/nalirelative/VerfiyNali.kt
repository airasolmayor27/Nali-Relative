package com.sti.nalirelative

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VerfiyNali : AppCompatActivity() {

    private lateinit var quitButton:Button
    private lateinit var registerButton: Button
    private lateinit var deviceEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verfiy_nali)

        quitButton = findViewById(R.id.quit_device)

        registerButton = findViewById(R.id.register_device)

        deviceEditText = findViewById(R.id.deviceEditText)

        // Inside your onCreateView method where you set up the OkHttpClient
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                // Log the message as desired, e.g., to Android Logcat
                // You can also write it to a file or use any other logging mechanism
                Log.d("API Request", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
        }
        // Inside onCreateView method

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
// Create an instance of your Retrofit service interface with the custom OkHttpClient
        val apiInterface = Retrofit.Builder()
            .baseUrl("https://naliproject.xyz/api/") // Replace with your CodeIgniter API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // Set the OkHttpClient with the custom logging interceptor
            .build()
            .create(ApiInterface::class.java)


        val sessionManager = SessionManager(this)
        val UserID = sessionManager.getUserID()

        showToast(UserID.toString())


        registerButton.setOnClickListener {
           val naliID =  deviceEditText.text

            // Inside your onCreateView method where you set up the OkHttpClient
            val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    // Log the message as desired, e.g., to Android Logcat
                    // You can also write it to a file or use any other logging mechanism
                    Log.d("API Request", message)
                }
            }).apply {
                level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
            }
            // Inside onCreateView method

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
// Create an instance of your Retrofit service interface with the custom OkHttpClient
            val apiInterface = Retrofit.Builder()
                .baseUrl("https://naliproject.xyz/api/") // Replace with your CodeIgniter API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient) // Set the OkHttpClient with the custom logging interceptor
                .build()
                .create(ApiInterface::class.java)

            val call = apiInterface.addrelative(naliID.toString(),UserID.toString())

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(callchecker: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse1 = response.body()
                        Log.e("USER",apiResponse1.toString())
                        if (apiResponse1 != null) {
                            if (apiResponse1.status == true) {
                                val message = apiResponse1.message
                               showToast(message.toString())
                                finish()
                            } else {
                                showToast("Status False")
                            }
                        } else {
                            // Handle the response here when the API response is null
                            showToast("API response is null")
                        }
                    } else {
                        // Handle the error response here
                        showToast("Failed to retrieve user information")
                    }
                }

                override fun onFailure(callchecker: Call<ApiResponse>, t: Throwable) {
                    // Handle failure here
                }
            })

        }

        quitButton.setOnClickListener { showLogoutConfirmationDialog() }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    // Handle the back button press
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun addrelativeifvalid(device_id : String,relID : String){

    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Close")
        builder.setMessage("Are you sure you want to close?")
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            // User clicked Yes, perform logout
            val sessionManager = SessionManager(this)
            sessionManager.logoutUser()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            finish()
        }
        builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
            // User clicked No, dismiss the dialog
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

}