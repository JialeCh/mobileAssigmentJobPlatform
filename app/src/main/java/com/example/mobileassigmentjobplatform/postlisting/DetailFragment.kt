package com.example.mobileassigmentjobplatform.postlisting


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobileassigmentjobplatform.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    lateinit var db:FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var args : DetailFragmentArgs


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        args = DetailFragmentArgs.fromBundle(arguments!!)
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args =DetailFragmentArgs.fromBundle(arguments!!)
        showData()
        // imgView_MyPost id
        //args.image  image url
    }

    private fun showData(){
        txtMypost_Title.text =args.titleName
        txtMyPost_Salary.text =args.salary
        txtDetail_inform.text=args.inform
        txtMyPost_Date.text=args.date
        txtMypost_Location.text=args.location
        txtMyPost_CompanyName.text=args.companyName
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detailuppernavigation,menu)
        readData( object: FirebaseCallback {
            override fun oncallBack(valid:Boolean,deleteItemID:String){
                if(valid == false) { //  no exist
                    menu.getItem(0).setIcon(R.drawable.loveempty_24dp)
                }else{ // if  exist
                    menu.getItem(0).setIcon(R.drawable.love_24dp)
                }
            }

            override fun oncallBackgetEmail(email: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.item_contact ->{Toast.makeText(activity,"contact",Toast.LENGTH_LONG)
                sendEmail()
                return true
            }
            R.id.item_favorite ->{
                addFavourite(item)
                R.id.item_favorite
                return true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }

    }

    private fun sendEmail(){

        getreceiperEmail(object: FirebaseCallback {
            override fun oncallBack(valid: Boolean, deleteItemID: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun oncallBackgetEmail(email: String) {
                val subject = "subject of you email"
                val eMailMessageTxt = "Add Message here"
                val eMailId1 = "emailId1@gmail.com"
                val eMailIds: Array<String> = arrayOf( email)

                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "text/plain"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, eMailIds)
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                emailIntent.putExtra(Intent.EXTRA_TEXT, eMailMessageTxt)
                emailIntent.setType("message/rfc822")

                try {
                    activity?.startActivity(Intent.createChooser(emailIntent, "Send email using..."))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(activity,"No email clients installed.",Toast.LENGTH_SHORT).show()
                }
            }


        })




    }


    private fun getreceiperEmail(firebaseCallback: FirebaseCallback) {
        var email:String=" "
        val docRef = db.collection("User").document(args.AuthorID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    firebaseCallback.oncallBackgetEmail(document["Email"].toString())
                } else {
                    Log.d("hahahh", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("hahahh", "get failed with ", exception)
            }

    }

    private fun addFavourite(item: MenuItem){
        readData( object: FirebaseCallback {

            override fun oncallBack(valid:Boolean,deleteItemID:String){
                if(valid == false) { // add favourite if no exist
                    val data = hashMapOf(
                        "FovarateID" to args.jobPostID
                    )
                    db.collection("User").document(auth.currentUser?.uid.toString())
                        .collection("interestJob")
                        .add(data)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(getContext(), "had been add", Toast.LENGTH_SHORT).show()
                            item.setIcon(R.drawable.love_24dp)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(getContext(), "fail to add try again", Toast.LENGTH_SHORT).show()
                        }
                }else{ // delete favourite if  exist
                    db.collection("User").document(auth.currentUser?.uid.toString()).collection("interestJob").document(deleteItemID)
                        .delete()
                        .addOnSuccessListener {
                            item.setIcon(R.drawable.loveempty_24dp)
                            Toast.makeText(getContext(), "cancel Favourite", Toast.LENGTH_SHORT).show() }
                        .addOnFailureListener { Toast.makeText(getContext(), "fail cancel Favourite", Toast.LENGTH_SHORT).show() }

                }

            }

            override fun oncallBackgetEmail(email: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        )
    }
    private  fun readData(firebaseCallback: FirebaseCallback){
        var valid:Boolean= false
        var deleteItem:String=" "
        db.collection("User")
            .document(auth.currentUser?.uid.toString()).collection("interestJob")
            .get().addOnSuccessListener {result ->
                for (document in result) {
                    var favourateID =document["FovarateID"].toString()
                    if( args.jobPostID.compareTo(favourateID) ==0 ) {
                        valid= true
                        deleteItem=document.id
                        break
                    }
                }
                firebaseCallback.oncallBack(valid,deleteItem)
            }
    }

    interface FirebaseCallback{
        fun oncallBack(valid:Boolean, deleteItemID:String)
        fun oncallBackgetEmail(email:String)
    }



}






