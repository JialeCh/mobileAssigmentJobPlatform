package com.example.mobileassigmentjobplatform

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*

class LogIn : AppCompatActivity() {

    var skip: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        btnLogIn.setOnClickListener {

            val email = email_login.text.toString()
            val pass = pass_login.text.toString()
            if (email.isEmpty()){
                email_login.error = "Email Required"
                email_login.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                email_login.error = "Valid Email Required"
                email_login.requestFocus()
                return@setOnClickListener
            }
            if (pass.isEmpty() || pass.length < 6){
                pass_login.error = "6 char password required"
                pass_login.requestFocus()
                return@setOnClickListener
            }
            loginUser(email,pass)
        }

        sign_up_text_view.setOnClickListener {
            register()
        }
        Forgot_Password.setOnClickListener {
            recovery()
        }

    }




    private fun loginUser(email : String,pass :String) {

        val progressDialog = ProgressDialog(this@LogIn)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("Please wait, this may take a while")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        val myAuth: FirebaseAuth = FirebaseAuth.getInstance()

        myAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task ->
            if(task.isSuccessful)
            {
                progressDialog.dismiss()
                Toast.makeText(this,"You are successfully login! ! !",Toast.LENGTH_LONG).show()
                val intent = Intent(this@LogIn, MainActivity::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(intent)

                finish()

            }
            else
            {
                val message = task.exception.toString()
                Toast.makeText(this,"Wrong Passord or Email entered", Toast.LENGTH_LONG).show()
                myAuth.signOut()
                progressDialog.dismiss()
            }

        }
    }

    private fun register(){

        startActivity(Intent(this, Register :: class.java))
    }

    private fun recovery(){

        startActivity(Intent(this, ResetPassword:: class.java))
    }


}

