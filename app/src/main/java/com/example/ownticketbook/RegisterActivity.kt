package com.example.ownticketbook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ownticketbook.models.User
import com.example.ownticketbook.services.AuthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection

class RegisterActivity : AppCompatActivity()
{
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        authService = AuthService()

        val txtfullname = findViewById<EditText>(R.id.txtfullname)
        val txtphonenumber = findViewById<EditText>(R.id.txtphonenumber)
        val txtusername = findViewById<EditText>(R.id.txtusername)
        val txtpassword = findViewById<EditText>(R.id.txtpassword)
        val txtcomformpassword = findViewById<EditText>(R.id.txtcomformpassword)

        findViewById<Button>(R.id.btnsignup).setOnClickListener {

            val FullName = txtfullname.text.toString()
            val MobileNumber = txtphonenumber.text.toString()
            val Username = txtusername.text.toString()
            val Password = txtpassword.text.toString()
            val comformpassword = txtcomformpassword.text.toString()

            if (Password == comformpassword)
            {
                val user = User(
                    FullName = FullName,
                    MobileNumber = MobileNumber,
                    Username = Username,
                    Password = Password
                )
                CoroutineScope(Dispatchers.IO).launch {

                    val response = authService.register(user)
                    if (response.code == HttpURLConnection.HTTP_CREATED)
                    {
                        click()
                    } else if (response.code == HttpURLConnection.HTTP_CONFLICT)
                    {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Could not register!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            } else
                Toast.makeText(
                    this@RegisterActivity,
                    "Passwords do not match!",
                    Toast.LENGTH_LONG
                ).show()
        }
    }

    private fun click()
    {
        val btnregister = findViewById<Button>(R.id.btnsignup)

        btnregister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
