package com.example.mobileassigmentjobplatform.Mypost

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileassigmentjobplatform.R
import com.example.mobileassigmentjobplatform.`class`.JobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_edit_post.*
import kotlinx.android.synthetic.main.fragment_post_listing.*
import java.util.ArrayList

class EditPost : Fragment() {

    lateinit var argst: EditPostArgs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_post, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        argst =  EditPostArgs.fromBundle(arguments!!)
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)


    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showData()
        btnSave.setOnClickListener{
            update()

        }
    }

    private fun showData(){
        Edit_Title.text = Editable.Factory.getInstance().newEditable(argst.titleName)
        Edit_Salary.text =Editable.Factory.getInstance().newEditable(argst.salary)
        Edit_inform.text=Editable.Factory.getInstance().newEditable(argst.inform)
        Edit_Date.text=Editable.Factory.getInstance().newEditable(argst.date)
        Edit_Location.text=Editable.Factory.getInstance().newEditable(argst.location)
        Edit_CompanyName.text=Editable.Factory.getInstance().newEditable(argst.companyName)
    }

    private fun update(){

        val progressDialog = ProgressDialog(getActivity())
        progressDialog.setTitle("Updating")
        progressDialog.setMessage("Please wait, this may take a while")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        var newSalary: String =  Edit_Salary.text.toString()
        var newTitle: String =  Edit_Title.text.toString()
        var newinform: String =  Edit_inform.text.toString()
        var newLocation: String =  Edit_Location.text.toString()
        var newDate: String =  Edit_Date.text.toString()
        var newCompanyName: String =  Edit_CompanyName.text.toString()
        var documentName : String = argst.jobPostID

        db.collection("JobPost").document(documentName)
            .update(
                mapOf(

                "JPcompanyName" to  newCompanyName,
                "JPDate" to  newDate,
                "JPImage" to "",
                "JPinform" to newinform,
                "JPSalary" to newSalary,
                "JPtitle" to  newTitle,
                "location" to newLocation,
                "userID" to user?.uid.toString()))
            .addOnSuccessListener {
                Log.d(Constraints.TAG, "DocumentSnapshot successfully updated!")
                Toast.makeText(
                    context,
                    "DocumentSnapshot successfully updated!",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(EditPostDirections.actionEditPostToMyPostRecycleVeiw(
                    newTitle, newSalary, "", newDate,newinform,newLocation,newCompanyName,documentName,user?.uid.toString()
                ))

                progressDialog.dismiss()
            }
            .addOnFailureListener { e ->
                Log.w(Constraints.TAG, "Error updating document", e)
                Toast.makeText(context, "Error updating document!", Toast.LENGTH_SHORT)
                    .show()
                progressDialog.dismiss()
            }

    }


}