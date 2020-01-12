package com.example.mobileassigmentjobplatform.userProfile

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobileassigmentjobplatform.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.imagebtn
import kotlinx.android.synthetic.main.fragment_user_profile.*


class EditProfile : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_edit_profile, container, false)
    }

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getData()
        btnSave.setOnClickListener{
            updateInfo()
        }
        btnCancel.setOnClickListener{
           findNavController().navigate(EditProfileDirections.actionEditProfileToUserProfile())
        }

    }
    private fun getData() {
        val user = mAuth.currentUser
        val uid = user?.uid.toString()

        val docRef = db.collection("User").document(uid)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                var name = document["Name"].toString()
                txtName.text = Editable.Factory.getInstance().newEditable(name)
                var email = document["Email"]
                txtEmail.text = email.toString()
                var gender = document["Gender"].toString()
                if(gender.equals("Male")){
                    rbMale.isChecked =true
                    rbFemale.isChecked =false
                }
                else
                {
                    rbMale.isChecked =false
                    rbFemale.isChecked =true
                }

                var IC = document["IC"].toString()
                txtIC.text = Editable.Factory.getInstance().newEditable(IC)

                var phoneNum = document["Phone Number"].toString()
                txtPhoneNum.text = Editable.Factory.getInstance().newEditable(phoneNum)

                var salary = document["Salary"].toString()
                txtSalary.text = Editable.Factory.getInstance().newEditable(salary)

                var education = document["highEducation"].toString()
                txtEducation.text =Editable.Factory.getInstance().newEditable(education)

                var city = document["City"].toString()
                txtCity.text = Editable.Factory.getInstance().newEditable(city)

                var Introduction = document["Introduction"].toString()
                txtIntroduction.text = Editable.Factory.getInstance().newEditable(Introduction)

                var imageUrl: Uri? = document["profile"].toString().toUri()
                imagebtn.setImageURI(imageUrl)

                Log.d("document", "DocumentSnapshot data: ${document["Email"]}")
            } else {
                Log.d("document", "No such document")
            }
        }
    }
    private fun updateInfo() {

        val username: String = txtName.text.toString()

        val ic:String = txtIC.text.toString()
        val phoneNum = txtPhoneNum.text.toString()
        val education: String = txtEducation.text.toString()
        val city: String = txtCity.text.toString()
        val newsalary : String = "RM "+txtSalary.text.toString() +"/month"
        val introduction: String = txtIntroduction.text.toString()
        val userRef = db.collection("User").document(mAuth.uid.toString())
        var gender = " "

        if(rbMale.isChecked) {
            gender = "Male"
        }else {
            gender = "Female"
        }

        db.runTransaction{transaction ->

            transaction.update(userRef,"Name",username)
            transaction.update(userRef,"Gender",gender)
            transaction.update(userRef,"IC",ic)
            transaction.update(userRef,"Phone Number",phoneNum)
            transaction.update(userRef,"highEducation",education)
            transaction.update(userRef,"City",city)
            transaction.update(userRef,"Salary",newsalary)
            transaction.update(userRef,"Introduction",introduction)

        }

            .addOnSuccessListener {

                Toast.makeText(context, "Successfully edit", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Transaction failure.", e)
                Toast.makeText(context, "Failed to edit", Toast.LENGTH_SHORT).show()
            }
    }
}
