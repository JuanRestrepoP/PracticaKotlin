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
import com.example.myapplication.data.USER_SESSION_PREFS
import kotlinx.coroutines.launch
import android.Manifest
import android.content.pm.PackageManager
import com.example.permissionutils.PermissionHelper

private val LOCATION_REQUEST_CODE = 1001


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_permission_waiting)

        if (!PermissionHelper.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE)) {

            return
        }

        iniciarUI()
    }

    private fun iniciarUI() {
        val prefs = getSharedPreferences(USER_SESSION_PREFS, Context.MODE_PRIVATE)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
            iniciarUI()
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }

}
