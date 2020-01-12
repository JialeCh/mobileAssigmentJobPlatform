package com.example.mobileassigmentjobplatform.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobileassigmentjobplatform.LogIn
import com.example.mobileassigmentjobplatform.MainActivity
import com.example.mobileassigmentjobplatform.R
import java.lang.Exception

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val background = object : Thread(){
            override fun run() {
                try {
                    Thread.sleep(5000)

                    val intent = Intent(baseContext, LogIn::class.java)
                    startActivity(intent)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}
