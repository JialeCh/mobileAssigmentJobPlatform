package com.example.mobileassigmentjobplatform



import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var  navcontroller = Navigation.findNavController(this,R.id.fragment2)
        var  appBarConfiguration= AppBarConfiguration.Builder(bottomNavigation.menu).build()

        NavigationUI.setupWithNavController(bottomNavigation, navcontroller)

    }





    override fun onPause() {
        super.onPause()
        Log.w("main", "pause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("main", "Destroy")
    }



//    fun insertdata(){
//
//        val db = FirebaseFirestore.getInstance()
//
//        for (i in 1..5) {
//            val data = hashMapOf(
//        "JPcompanyName" to "apple"+ i.toString(),
//        "JPdate" to "12/12/2017",
//        "JPimage" to "123",
//        "JPinform" to "good price",
//        "JPsalary" to 12.34+i,
//        "JPtitle" to "computer Since"+"i",
//        "location" to "kuala lumpur"+"i",
//        "userID"   to  " dKn2GWMZFbgpmtwpALFn"
//    )
//            db.collection("JobPost")
//                .add(data)
//                .addOnSuccessListener { documentReference ->
//                    Log.d("addhahah", "DocumentSnapshot written with ID: ${documentReference.id}")
//                }
//                .addOnFailureListener { e ->
//                    Log.w("addhahah", "Error adding document", e)
//                }
////
//        }




}
