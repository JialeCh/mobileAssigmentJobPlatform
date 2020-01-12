package com.example.mobileassigmentjobplatform.Mypost


import android.app.AlertDialog
import android.app.FragmentManager
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileassigmentjobplatform.R
import com.example.mobileassigmentjobplatform.`class`.JobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_my_post_list.*
import kotlinx.android.synthetic.main.fragment_post_listing.*
import kotlinx.android.synthetic.main.fragment_post_listing.recycleV_postListing
import java.util.*


class MyPost : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_my_post_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        readData(object : MyPost.FirebaseCallback {

            override fun oncallBack(arraylist: ArrayList<JobPost>) {

                try {
                    var adapter = PostAdapter(arraylist)
                    recycleV_postListing.adapter = adapter
                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    Log.w("error", e.printStackTrace().toString())
                }
            }
        }
        )

        recycleV_postListing.layoutManager = LinearLayoutManager(this.context)
        recycleV_postListing.setHasFixedSize(true)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         this.
        addNewPost.setOnClickListener {
            showDialog()
        }

    }
    fun showDialog() {
        val fm =requireFragmentManager()
        val myFragment = AddPost()

        myFragment.show(fm,"Add Post")

    }

    fun readData(firebaseCallback: FirebaseCallback) {
        val mAuth = FirebaseAuth.getInstance()
        Log.w("xue1", "read ")
        var arrayList = ArrayList<JobPost>()
        var db = FirebaseFirestore.getInstance()
        val user = mAuth.currentUser
        Log.w("xue1", "read ")
        db.collection("JobPost")
            .whereEqualTo("userID", user?.uid.toString())
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    arrayList.add(
                        JobPost(
                            document[getString(R.string.jPDate_Fb)].toString(),
                            document[getString(R.string.jPImage_Fb)].toString(),
                            document[getString(R.string.jPinform_Fb)].toString(),
                            document[getString(R.string.jPSalary_Fb)].toString(),
                            document[getString(R.string.jPitle_Fb)].toString(),
                            document[getString(R.string.locatione_Fb)].toString(),
                            document[getString(R.string.userID_Fb)].toString(),
                            document[getString(R.string.jPcompanyName_Fb)].toString(),
                            document.id
                        )
                    )
                    Log.w("document1", arrayList.isEmpty().toString())
                }
                firebaseCallback.oncallBack(arrayList)
            }
            .addOnFailureListener { exception ->
            }
    }

    interface FirebaseCallback {
        fun oncallBack(arraylist: ArrayList<JobPost>)
    }
}
