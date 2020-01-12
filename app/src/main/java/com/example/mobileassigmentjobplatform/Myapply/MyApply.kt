package com.example.mobileassigmentjobplatform.Myapply


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mobileassigmentjobplatform.R
import com.example.mobileassigmentjobplatform.`class`.JobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_my_apply.*

/**
 * A simple [Fragment] subclass.
 */
class MyApply : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        readData( object: MyApply.FirebaseCallback {
            override fun oncallBack(arraylist :ArrayList<JobPost>){

                var adapter= MyApplyAdapter(arraylist)
                myApply_RecycleVeiw.adapter=adapter
            }
        }
        )


     return inflater.inflate(R.layout.fragment_my_apply, container, false)
    }



     override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         myApply_RecycleVeiw.layoutManager= LinearLayoutManager(this.context)

    }


    fun readData(firebaseCallback: MyApply.FirebaseCallback) {
        var arrayListJPID = ArrayList<String>()
        var arrayList = ArrayList<JobPost>()
        var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()

//        db.collection("User").document(auth.currentUser?.uid.toString()).collection("interSet")


            db.collection("User").document(auth.currentUser?.uid.toString())
            .collection("interestJob").get().addOnSuccessListener { result ->
                for (document in result) {
                    arrayListJPID.add(document["FovarateID"].toString())
                }

                    for(id in arrayListJPID){
                        db.collection("JobPost").document(id).get().addOnSuccessListener {document ->
                            if (document != null) {
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
                                firebaseCallback.oncallBack(arrayList)
                            }else{

                            }
                    }
                    }
            }
    }
    interface FirebaseCallback{
        fun oncallBack(arraylist :ArrayList<JobPost>)
    }
}








