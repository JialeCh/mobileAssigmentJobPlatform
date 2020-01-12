package com.example.mobileassigmentjobplatform

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.password_recoery.*

class ResetPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_recoery)

        btnReset.setOnClickListener{
            recovery()
        }

        btnCancel.setOnClickListener {
            //return back to login page
        }
    }

    private fun recovery() {

        val email: String = txtemail.text.toString()
        val auth = FirebaseAuth.getInstance()

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Password Recovery")
        progressDialog.setMessage("Please wait awhile!!!")
        progressDialog.setCanceledOnTouchOutside(false)

        if (TextUtils.isEmpty(email)) {
            progressDialog.dismiss()
            Toast.makeText(this, "Please Enter Email!!!", Toast.LENGTH_SHORT).show()

        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Please Enter Valid Email!!!", Toast.LENGTH_SHORT).show()

        } else {
            progressDialog.show()

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)

                        Toast.makeText(this, "Password reset are sent ", Toast.LENGTH_SHORT).show()
                    //return to login page
                    progressDialog.dismiss()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
