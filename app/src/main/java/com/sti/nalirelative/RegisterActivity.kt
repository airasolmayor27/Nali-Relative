package com.sti.nalirelative

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import at.favre.lib.crypto.bcrypt.BCrypt
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private val BASE_URL = "https://naliproject.xyz/api/user/"
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var mobileNumberEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var  confirmPasswordEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the back button in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        // Initialize your EditText fields
        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)

        val registerButton: Button = findViewById(R.id.submitButton)
        registerButton.setOnClickListener {
            // Get user input from EditText fields
            val name = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val mobile = mobileNumberEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (name.isBlank() || !name.matches(Regex("^[a-zA-Z ]+\$"))) {
                usernameEditText.error = "Invalid username"
                return@setOnClickListener
            }

            // Validate email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Invalid email address"
                return@setOnClickListener
            }

            // Validate mobile number (must be 11 digits)
            if (!Pattern.compile("^[0-9]{11}\$").matcher(mobile).matches()) {
                mobileNumberEditText.error = "Invalid mobile number (11 digits required)"
                return@setOnClickListener
            }

            // Validate password (must be at least 8 characters and match confirmPassword)
            if (password.length < 8) {
                passwordEditText.error = "Password must be at least 8 characters"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                confirmPasswordEditText.error = "Passwords do not match"
                return@setOnClickListener
            }

            val roleId = 16;
            val isAdmin = 2

            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = current.format(formatter)

            val createdBy = 1
            val createdDtm = formatted
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
                .baseUrl("https://naliproject.xyz/api/user/") // Replace with your CodeIgniter API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient) // Set the OkHttpClient with the custom logging interceptor
                .build()
                .create(ApiInterface::class.java)

            // Create an instance of the ApiInterface

            val bcryptHashString = BCrypt.withDefaults().hashToString(10, password.toCharArray())
            // Make the API call for registration
            val call = apiInterface.register(
                name,
                email,
                bcryptHashString,
                roleId, // Replace with the actual roleId value if needed
                mobile,
                isAdmin, // Replace with the actual isAdmin value if needed
                createdBy,
                createdDtm
            )

            // Enqueue the call to run asynchronously
            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.code() == 201) { // 201 Created is typically returned for successful registration
                        val apiResponse = response.body()
                        if (apiResponse != null && apiResponse.status == true) {
                            Log.e("API", apiResponse.toString())
                            // Registration was successful
                            Toast.makeText(applicationContext, "${apiResponse.message}", Toast.LENGTH_SHORT).show()
                            // Registration was successful, navigate to LoginActivity
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish() // Optional: Close the RegisterActivity if you don't want to go back to it
                        } else {
                            Log.e("API", apiResponse.toString())
                            if (apiResponse != null && "Email already exists" == apiResponse.message) {
                                // Handle the case where the email already exists
                                // Display an appropriate message to the user
                                Toast.makeText(applicationContext, "Email already exists", Toast.LENGTH_SHORT).show()
                            } else {
                                // Registration failed for other reasons
                                Toast.makeText(applicationContext, "Registration failed: ${apiResponse?.message}", Toast.LENGTH_SHORT).show()
                                // Handle failure, maybe show an error message
                            }
                        }
                    } else {
                        // Registration request failed
                        Toast.makeText(applicationContext, "Registration request failed", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // Registration request failed
                    Toast.makeText(applicationContext, "Registration request failed.", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        }

    }



}