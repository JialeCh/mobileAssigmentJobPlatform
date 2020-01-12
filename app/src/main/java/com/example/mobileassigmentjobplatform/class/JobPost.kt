package com.example.mobileassigmentjobplatform.`class`

import android.os.Parcel
import android.os.Parcelable

class JobPost(){
    private var  jpID:String=""
    private var  jpDate:String =""
    private var  jpImage:String =""
    private var jpinform:String =""
    private var jpSalary :String =""
    private var jptitle :String =""
    private var location:String=""
    private var userID:String=""
    private var jpCompanyName:String=""



    // no jid
    constructor(jpDate:String,jpImage:String,jpinform:String, jpSalary :String,jptitle :String, location:String,userID:String,jpCompanyName:String):this(){
        this.jpDate =jpDate
        this.jpImage=jpImage
        this.jpinform=jpinform
        this.jpSalary =jpSalary
        this.jptitle=jptitle
        this.location=location
        this.userID=userID
        this.jpCompanyName=jpCompanyName

    }

   /// has jip
    constructor(jpDate:String,jpImage:String,jpinform:String, jpSalary :String,jptitle :String, location:String,userID:String,jpCompanyName:String,jpID:String)
            : this(jpDate,jpImage,jpinform, jpSalary ,jptitle, location,userID,jpCompanyName){
        this.jpID=jpID
    }

    fun getJpDate(): String? {
        return jpDate
    }

    fun setJpDate(jpDate: String?) {
        this.jpDate = jpDate!!
    }

    fun getJpImage(): String? {
        return jpImage
    }

    fun setJpImage(jpImage: String?) {
        this.jpImage = jpImage!!
    }

    fun getJpinform(): String? {
        return jpinform
    }

    fun setJpinform(jpinform: String?) {
        this.jpinform = jpinform!!
    }

    fun getJpSalary(): String? {
        return jpSalary
    }

    fun setJpSalary(jpSalary: String?) {
        this.jpSalary = jpSalary!!
    }

    fun getJptitle(): String? {
        return jptitle
    }

    fun setJptitle(jptitle: String?) {
        this.jptitle = jptitle!!
    }

    fun getLocation(): String? {
        return location
    }

    fun setLocation(location: String?) {
        this.location = location!!
    }

    fun getUserID(): String? {
        return userID
    }

    fun setUserID(userID: String?) {
        this.userID = userID!!
    }

    fun getjpCompanyName(): String? {
        return jpCompanyName
    }

    fun setjpCompanyName(jpCompanyName: String?) {
        this.jpCompanyName = jpCompanyName!!
    }

    fun getjpID(): String? {
        return jpID
    }

    fun setjpID(id:String){
        this.jpID =id
    }
    override fun toString(): String {
        return "JobPost(jpDate='$jpDate', jpImage='$jpImage', jpinform='$jpinform', jpSalary=$jpSalary, jptitle='$jptitle', location='$location', userID='$userID')"
    }




}