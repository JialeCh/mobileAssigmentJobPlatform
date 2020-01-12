package com.example.mobileassigmentjobplatform.userProfile

import android.app.Activity
import android.content.ContentValues
import android.content.Context
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
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
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
    private val storage = FirebaseStorage.getInstance()

    private var checker = ""
    private var myUrl = ""
    private  var imageUri: Uri? =null
    private  var storageProfilePicRef: StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        storageProfilePicRef = storage.reference.child("ProfileUser")

        imagebtn.setOnClickListener{
            checker = "clicked"
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(context as Activity,this)

        }

        btnSave.setOnClickListener{
            if (checker == "clicked"){
                updateInfo()
                UpdateImageAndUpdateInfo()
            }else{
                updateInfo()
            }

        }
    }

    private fun updateInfo() {

        val username: String = txtName.text.toString()
        val ic:String = txtIC.text.toString()
        val phoneNum = txtPhoneNum.text.toString()
        val education: String = txtEducation.text.toString()
        val city: String = txtCity.text.toString()
        val salary : String = txtSalary.toString()
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
            transaction.update(userRef,"Salary",salary)
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

    private fun UpdateImageAndUpdateInfo() {
        when{
            imageUri == null -> Toast.makeText(context,"Please select image first !", Toast.LENGTH_LONG).show()

            else -> {

                val fileref = storageProfilePicRef!!.child(mAuth.uid + "jpg")

                val username: String = txtName.text.toString()
                val ic:String = txtIC.text.toString()
                val phoneNum = txtPhoneNum.text.toString()
                val education: String = txtEducation.text.toString()
                val city: String = txtCity.text.toString()
                val salary : String = txtSalary.toString()
                val introduction: String = txtIntroduction.text.toString()
                val userRef = db.collection("User").document(mAuth.uid.toString())
                var gender = " "

                if(rbMale.isChecked) {
                    gender = "Male"
                }else {
                    gender = "Female"
                }

                var uploadTask: StorageTask<*>
                uploadTask = fileref.putFile(imageUri!!)
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                    if(!task.isSuccessful){
                        task.exception?.let {
                            throw it
                            //progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileref.downloadUrl
                } ).addOnCompleteListener ( OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful) {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()

                        db.runTransaction { transaction ->

                            transaction.update(userRef, "Name", username)
                            transaction.update(userRef, "Gender", gender)
                            transaction.update(userRef, "IC", ic)
                            transaction.update(userRef, "Phone Number", phoneNum)
                            transaction.update(userRef, "highEducation", education)
                            transaction.update(userRef, "City", city)
                            transaction.update(userRef, "Salary", salary)
                            transaction.update(userRef, "Introduction", introduction)

                            transaction.update(userRef, "Image", myUrl)
                        }

                        Toast.makeText(
                            context,
                            "Account info has been successful updated !",
                            Toast.LENGTH_LONG
                        ).show()

                        //val intent = Intent(this, Default_Menu::class.java)

                        //progressDialog.dismiss()
                    } else {
                        //progressDialog.dismiss()
                    }
                } )
            }
        }
    }


    companion object {

        private lateinit var Context: Context

        fun setContext(con: Context) {
            Context=con
        }
    }
}
