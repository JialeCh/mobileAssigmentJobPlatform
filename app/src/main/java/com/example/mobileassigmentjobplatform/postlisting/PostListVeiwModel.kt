package com.example.mobileassigmentjobplatform.postlisting

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mobileassigmentjobplatform.`class`.JobPost
import com.google.firebase.firestore.FirebaseFirestore

class PostListVeiwModel  : ViewModel(){

  private  var _postListArray=ArrayList<JobPost>()



     var postListArray: ArrayList<JobPost>
        get() = _postListArray
        set(value) {
            _postListArray =value
        }



    fun insert_data():ArrayList<JobPost>{
         var x=ArrayList<JobPost>()
        readData( object: FirebaseCallback {

            override fun oncallBack(arraylist :ArrayList<JobPost>){
                x =arraylist
            }
        }
        )
        return x;
    }



    private  fun readData(firebaseCallback: FirebaseCallback){

        Log.w("xue1","read ")
        var arrayList = ArrayList<JobPost>()
        var db = FirebaseFirestore.getInstance()


        db.collection("JobPost").get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    arrayList.add(
                        JobPost(document["Jpdate"].toString(),document["JPimage"].toString(),
                            document["JPinform"].toString(),
                            document["JPsalary"].toString(),
                            document["JPtitle"].toString(),document["location"].toString(),
                            document["userID"].toString(),document["JPcompanyName"].toString(),document.id)
                    )

                }
                firebaseCallback.oncallBack(arrayList)

            }
            .addOnFailureListener { exception ->

            }

    }

    private interface FirebaseCallback{
        fun oncallBack(arraylist :ArrayList<JobPost>)
    }





}