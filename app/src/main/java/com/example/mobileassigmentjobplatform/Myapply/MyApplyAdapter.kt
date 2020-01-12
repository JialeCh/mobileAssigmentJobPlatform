package com.example.mobileassigmentjobplatform.Myapply

import android.app.AlertDialog
import android.icu.text.Transliterator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassigmentjobplatform.R
import com.example.mobileassigmentjobplatform.`class`.JobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.postlist_row.view.*

class MyApplyAdapter(var jobListItem :ArrayList<JobPost> ): RecyclerView.Adapter<MyApplyAdapter.MyViewHolder>() {

    class MyViewHolder (var view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyApplyAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.postlist_row, parent, false)
        return MyApplyAdapter.MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return jobListItem.size
    }

    override fun onBindViewHolder(holder: MyApplyAdapter.MyViewHolder, position: Int) {
        holder.view.txtSalary.setText(jobListItem[position].getJpSalary().toString())
        holder.view.txtLocation.text=jobListItem[position].getLocation()
        holder.view.txtCompanyName.setText(jobListItem[position].getjpCompanyName())
        holder.view.txtDate.setText(jobListItem[position].getJpDate())
        holder.view.txtTitle.setText(jobListItem[position].getJptitle())
        holder.view.setOnClickListener {
            it.findNavController().navigate(MyApplyDirections.actionMyApplyToDetailFragment(jobListItem[position].getJptitle().toString(),jobListItem[position].getJpSalary().toString(),
                jobListItem[position].getJpImage().toString(),  jobListItem[position].getJpDate().toString(),jobListItem[position].getJpinform().toString(),jobListItem[position].getLocation().toString()
                ,jobListItem[position].getjpCompanyName().toString(),jobListItem[position].getjpID().toString(),jobListItem[position].getUserID().toString()))
        }

        holder.view.setOnLongClickListener{
            Log.w("deltemy","goood man")
            Log.w("deltemy",holder.adapterPosition.toString())
            val builder = AlertDialog.Builder(holder.view.getContext())
            builder.setMessage("Are you want to delete?")

            builder.setNegativeButton("YES"){dialog, which ->
                Toast.makeText(holder.view.getContext(),"Delete",Toast.LENGTH_SHORT).show()
                delete(jobListItem[holder.adapterPosition].getjpID().toString())
                jobListItem.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
                Log.w("deltemy",holder.adapterPosition.toString())
            }

            builder.setPositiveButton("cancel"){dialog,which ->
                Toast.makeText(holder.view.getContext(),"cancel",Toast.LENGTH_SHORT).show()
            }

            builder.show()
            return@setOnLongClickListener true
        }
    }

    fun removeItem(position: Int, viewHolder: RecyclerView.ViewHolder) {

        jobListItem .removeAt(position)
        notifyItemRemoved(position)

    }

    private fun delete(deleteItem :String){
        var db = FirebaseFirestore.getInstance()
        var auth = FirebaseAuth.getInstance()

        // Create a reference to the cities collection

        val ref =  db.collection("User").document(auth.currentUser?.uid.toString()).collection("interestJob")
            .get().addOnSuccessListener {result ->
                for (document in result) {
                    if(deleteItem == document["FovarateID"]){
                        db.collection("User").document(auth.currentUser?.uid.toString()).collection("interestJob").document(document.id.toString())
                            .delete()
                            .addOnSuccessListener { Log.d("delete", "DocumentSnapshot successfully deleted!") }
                            .addOnFailureListener { e -> Log.w("delete", "Error deleting document", e) }
                    }
                }
            }


    }

}