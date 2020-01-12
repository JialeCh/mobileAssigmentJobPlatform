package com.example.mobileassigmentjobplatform.Mypost


import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.mobileassigmentjobplatform.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_my_post_recycle_veiw.*


class MyPostRecycleVeiw : Fragment() {

    lateinit var args: MyPostRecycleVeiwArgs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_post_recycle_veiw, container, false)



    }

    override fun onCreate(savedInstanceState: Bundle?) {

        args = MyPostRecycleVeiwArgs.fromBundle(arguments!!)
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = MyPostRecycleVeiwArgs.fromBundle(arguments!!)
        showData()
        btnEdit.setOnClickListener {
            it.findNavController().navigate(MyPostRecycleVeiwDirections.actionMyPostRecycleVeiwToEditPost(
                args.titleName,  args.salary,args.image,args.date,args.inform,args.location,args.companyName,args.jobPostID,args.AuthorID
            ))

        }
        btnDelete.setOnClickListener{
            deleteData(args.jobPostID)
        }
    }


    private fun showData(){
        txtMypost_Title.text =args.titleName
        txtMyPost_Salary.text =args.salary
        txtDetail_inform.text=args.inform
        txtMyPost_Date.text=args.date
        txtMypost_Location.text=args.location
        txtMyPost_CompanyName.text=args.companyName
    }

    private fun deleteData(jobID : String){
        val progressDialog = ProgressDialog(getActivity())
        progressDialog.setTitle("Deleting")
        progressDialog.setMessage("Please wait, this may take a while")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        val db = FirebaseFirestore.getInstance()

        val documentname =jobID
        db.collection("JobPost").document(documentname)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!")
                Toast.makeText(
                    context,
                    "DocumentSnapshot successfully deleted!",
                    Toast.LENGTH_SHORT
                ).show()
                getFragmentManager()?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                progressDialog.dismiss()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e)
                Toast.makeText(
                    context,
                    "Error deleting document !",
                    Toast.LENGTH_SHORT
                ).show()
                getFragmentManager()?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                progressDialog.dismiss()
            }


    }

}
