package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val prefs = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val name = prefs.getString("name", "Nombre no disponible")
        val email = prefs.getString("email", "Correo no disponible")

        val nameTextView = view.findViewById<TextView>(R.id.textViewName)
        val emailTextView = view.findViewById<TextView>(R.id.textViewEmail)

        nameTextView.text = name
        emailTextView.text = email


        val logoutButton = view.findViewById<Button>(R.id.buttonLogOut)
        logoutButton.setOnClickListener {
            val prefs = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()

            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }
}
