package com.example.mobileassigmentjobplatform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.mobileassigmentjobplatform.userProfile.UserProfileDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_registration.*

class Register : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val myAuth: FirebaseAuth = FirebaseAuth.getInstance()
        btnNext.setOnClickListener {
            var email = emailTxt.text.toString()
            var password = passwordTxt.text.toString()

            if (email.isEmpty()){
                emailTxt.error = "Email Required"
                emailTxt.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailTxt.error = "Valid Email Required"
                emailTxt.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 6){
                passwordTxt.error = "6 char password required"
                passwordTxt.requestFocus()
                return@setOnClickListener
            }
            register(email, password)
        }

        sign_in_text_view.setOnClickListener {
            login()
        }
    }

    private fun register(email : String, password : String) {

        Log.w("register","in")
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.w("register","succeful")
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser

                    val data = hashMapOf(
                        "Name" to " ",
                        "Email" to email,
                        "Gender" to " ",
                        "IC" to " ",
                        "Phone Number" to " ",
                        "highEducation" to " ",
                        "City" to " ",
                        "Salary"   to  " ",
                        "Introduction"   to  " "
                    )

                    db.collection("User").document(user?.uid.toString()).set(data)

                    Toast.makeText(
                        baseContext, "succeful",
                        Toast.LENGTH_SHORT
                    ).show()

                    intent= Intent(this, LogIn ::class.java)
                    startActivity(intent)
                } else {
                    Log.w("register","unsucceful")
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
    }

    private fun login() {

        startActivity(Intent(this, LogIn :: class.java))
    }
}