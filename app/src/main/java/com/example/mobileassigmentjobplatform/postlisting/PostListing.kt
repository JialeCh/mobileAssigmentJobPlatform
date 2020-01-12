package com.example.mobileassigmentjobplatform.postlisting


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileassigmentjobplatform.Mypost.MyPostRecycleVeiwDirections
import com.example.mobileassigmentjobplatform.R
import com.example.mobileassigmentjobplatform.`class`.JobPost
import com.example.mobileassigmentjobplatform.userProfile.EditProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.fragment_post_listing.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class PostListing : Fragment() {
private lateinit var    model:PostListVeiwModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_post_listing, container, false)
    }

    override fun onStart() {
        super.onStart()
        var user = FirebaseAuth.getInstance().getCurrentUser();

        val uid = user?.uid.toString()
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("User").document(uid)
        docRef.get() .addOnSuccessListener {document ->
            if (document != null) {
                var  name =document["Name"].toString()

                var  email =document["Email"].toString()
                var  gender =document["Gender"].toString()
                var  IC =document["IC"].toString()
                var  phoneNum =document["Phone Number"].toString()
                var  salary =document["Salary"].toString()

                var education = document["highEducation"].toString()

                var  city =document["City"].toString()

                var  Introduction =document["Introduction"].toString()

                var imageUrl: Uri? = document["profile"].toString().toUri()


                if(name == " "){
                   findNavController().navigate(PostListingDirections.actionPostListingToEditProfile())
                }
                else{
                    onResume()
                }
                Log.d("document", "DocumentSnapshot data: ${document["Email"]}")
            } else {
                Log.d("document", "No such document")
            }
        }
    }


    override fun onResume() {
        super.onResume()
        readData( object: PostListing.FirebaseCallback {

            override fun oncallBack(arraylist :ArrayList<JobPost>){
                try {
                    var adapter= PostListAdapter(arraylist)
                    recycleV_postListing.adapter=adapter
                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                   Log.w("error",e.printStackTrace().toString())
                }
            }
        }
        )
        recycleV_postListing.layoutManager = LinearLayoutManager(this.context)
        recycleV_postListing.setHasFixedSize(true)
    }

    fun readData(firebaseCallback: FirebaseCallback){

        Log.w("xue1","read ")
        var arrayList = ArrayList<JobPost>()
        var db = FirebaseFirestore.getInstance()

        Log.w("xue1","read ")
      db.collection("JobPost").get()
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
                        ))
                    Log.w("document1",arrayList.isEmpty().toString())
                }
                firebaseCallback.oncallBack(arrayList)

            }
            .addOnFailureListener { exception ->

            }

    }

  interface FirebaseCallback{
        fun oncallBack(arraylist :ArrayList<JobPost>)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.w("onresume","onDestroy")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.w("onresume","onPause")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.w("onresume","onDestroyView")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.w("onresume","onDetach")
//    }

}
