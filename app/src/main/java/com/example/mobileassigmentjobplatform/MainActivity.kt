package com.example.mobileassigmentjobplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var  navcontroller = Navigation.findNavController(this,R.id.fragment2)
        var  appBarConfiguration= AppBarConfiguration.Builder(bottomNavigation.menu).build()
        NavigationUI.setupActionBarWithNavController(this, fragment2.findNavController(), appBarConfiguration)
        NavigationUI.setupWithNavController(bottomNavigation, navcontroller)

    }
}
