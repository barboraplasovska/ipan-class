package com.example.todolist.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.R
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val authorizedUsers = mapOf(
        "MOM" to 1,
        "DAD" to 2,
        "ME" to 3,
        "SISTER" to 4,
        "GUEST" to 9
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton = findViewById<Button>(R.id.login_button)
        val loginField = findViewById<EditText>(R.id.login_field)
        val errorText = findViewById<TextView>(R.id.error_textview)

        loginButton.setOnClickListener {
            val username = loginField.text.toString()
            if (authorizedUsers.containsKey(username.uppercase())) {
                val userId = authorizedUsers[username.uppercase()]
                // Successful login, navigate to List screen
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("userId", userId)
                startActivity(intent)
            } else {
                // Display error for unknown users
                errorText.visibility = View.VISIBLE
                errorText.text = getString(R.string.wrong_login)
            }
        }
    }

    // Method to open the Epita website
    fun openEpitaWebsite(view: View) {
        val url = "https://www.epita.fr/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
