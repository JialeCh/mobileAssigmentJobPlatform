package com.example.mobileassigmentjobplatform.postlisting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileassigmentjobplatform.R
import com.example.mobileassigmentjobplatform.`class`.JobPost
import kotlinx.android.synthetic.main.postlist_row.view.*



class PostListAdapter() : RecyclerView.Adapter<PostListAdapter.MyViewHolder>() {
    lateinit var jobListItem :ArrayList<JobPost>

    constructor(jobListItem :ArrayList<JobPost>):this(){
      this.jobListItem =jobListItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapter.MyViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.postlist_row, parent, false)
        return PostListAdapter.MyViewHolder(v)

    }

    override fun getItemCount(): Int {
   return  jobListItem.size
    }

    override fun onBindViewHolder(holder: PostListAdapter.MyViewHolder, position: Int) {
       // holder.bindItems(jobListItem[position])
        holder.view.txtSalary.setText(jobListItem[position].getJpSalary().toString())
        holder.view.txtLocation.text=jobListItem[position].getLocation()
        holder.view.txtCompanyName.setText(jobListItem[position].getjpCompanyName())
        holder.view.txtDate.setText(jobListItem[position].getJpDate())
        holder.view.txtTitle.setText(jobListItem[position].getJptitle())
        holder.view.setOnClickListener {
            it.findNavController()
                .navigate(PostListingDirections.actionPostListingToDetailFragment(
                    jobListItem[position].getJptitle().toString(),jobListItem[position].getJpSalary().toString(),
                jobListItem[position].getJpImage().toString(),  jobListItem[position].getJpDate().toString(),jobListItem[position].getJpinform().toString(),jobListItem[position].getLocation().toString()
                ,jobListItem[position].getjpCompanyName().toString(),jobListItem[position].getjpID().toString(),jobListItem[position].getUserID().toString()))
    }

    }
   class MyViewHolder (var view: View) : RecyclerView.ViewHolder(view) {

    }

}