package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userEmail = prefs.getString("email", null)
        if (userEmail != null) {
            startActivity(Intent(this, MoviesActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView9)
        textView.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        val button = findViewById<Button>(R.id.button8)
        val emailField = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordField = findViewById<EditText>(R.id.editTextTextPassword)

        button.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val db = AppDatabase.getDatabase(this)

                lifecycleScope.launch {
                    val user = db.userDao().getUserByEmailAndPassword(email, password)
                    if (user != null) {
                        prefs.edit()
                            .putString("email", user.mail)
                            .putString("name", user.name)
                            .apply()

                        startActivity(Intent(this@MainActivity, MoviesActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
