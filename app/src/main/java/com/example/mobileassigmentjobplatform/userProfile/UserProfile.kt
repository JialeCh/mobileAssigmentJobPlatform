package com.example.mobileassigmentjobplatform.userProfile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import com.example.mobileassigmentjobplatform.LogIn
import com.example.mobileassigmentjobplatform.R

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_user_profile.*


/**
 * A simple [Fragment] subclass.
 */
class UserProfile : Fragment() {

    val mAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        btnEdit.setOnClickListener{
            it.findNavController().navigate(UserProfileDirections.actionUserProfileToEditProfile())
        }
        btnSignOut.setOnClickListener(){
            mAuth.signOut()
            LogIn.getActivity()
        }
    }

    private fun getData(){
        val user = mAuth.currentUser
        val uid = user?.uid.toString()

        val docRef = db.collection("User").document(uid)
        docRef.get() .addOnSuccessListener {document ->
            if (document != null) {
                var  name =document["Name"]
                tvName.text = name.toString()
                var  email =document["Email"]
                tvEmail.text = email.toString()
                var  gender =document["Gender"]
                tvGender.text = gender.toString()
                var  IC =document["IC"]
                tvIC.text = IC.toString()
                var  phoneNum =document["Phone Number"]
                tvPhoneNum.text = phoneNum.toString()
                var  salary =document["Salary"]
                tvSalary.text = salary.toString()
                var education = document["highEducation"]
                tvHighestEducation.text = education.toString()
                var  city =document["City"]
                tvCity.text = city.toString()
                var  Introduction =document["Introduction"]
                tvIntoduction.text = Introduction.toString()
                var imageUrl: Uri? = document["profile"].toString().toUri()
                imagebtn.setImageURI(imageUrl)

                Log.d("document", "DocumentSnapshot data: ${document["Email"]}")
            } else {
                Log.d("document", "No such document")
            }
        }
    }

}
