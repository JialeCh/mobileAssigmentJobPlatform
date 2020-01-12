package com.example.mobileassigmentjobplatform.Search


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileassigmentjobplatform.R
import com.example.mobileassigmentjobplatform.`class`.JobPost
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_searchpage.*


/**
 * A simple [Fragment] subclass.
 */
class searchFragment : Fragment() {

    private lateinit var viewAdapter: searchAdapter
    companion object {
      lateinit  var  num:String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_searchpage, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.w("search","onActivityCreated")

         btnSearch.setOnClickListener {
             if(search_EditText.text!=null){
                 readData( object: searchFragment .FirebaseCallback {

                     override fun oncallBack(arraylist :ArrayList<JobPost>){

                         if(arraylist.isEmpty()!= true){
                             var adapter=searchAdapter(arraylist)
                             recycleV_searchListing.adapter=adapter
                             adapter.notifyDataSetChanged()
                             recycleV_searchListing.layoutManager = LinearLayoutManager(activity)
                             recycleV_searchListing.setHasFixedSize(true)
                         }else{
                             Toast.makeText(getContext(),"nothing found",Toast.LENGTH_SHORT).show()

                         }

                     }
                 })
             }
         }


    }


    private  fun readData(firebaseCallback: FirebaseCallback){
        var arrayList = ArrayList<JobPost>()
        var db = FirebaseFirestore.getInstance()

        db.collection("JobPost").get().addOnSuccessListener { result ->

                for (document in result) {
                      var title = document[getString(R.string.jPitle_Fb)].toString()
                    if( title.contains(search_EditText.text)){
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
                    }
                }
                firebaseCallback.oncallBack(arrayList)

            }
            .addOnFailureListener { exception ->

            }
        }

    private interface FirebaseCallback{
        fun oncallBack(arraylist :ArrayList<JobPost>)
    }


    override fun onResume() {
        super.onResume()
        Log.w("search","onResume")
  //   (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
     //   (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }






}
