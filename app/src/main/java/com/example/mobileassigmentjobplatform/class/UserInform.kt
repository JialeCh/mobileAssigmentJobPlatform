package com.example.mobileassigmentjobplatform.`class`

class UserInform() {
    private var  userName:String =""
    private var  emailAddress:String =""
    private var phoneNumber:String =""

    private var profile :String =""

    constructor(userName:String,emailAddress:String, phoneNumber:String,profile:String):this(){
        this.userName =userName
        this.emailAddress=emailAddress
        this.phoneNumber=phoneNumber
        this.profile =profile

    }

    fun getuserName(): String? {
        return userName
    }

    fun setemailAddress(emailAddress: String?) {
        this.emailAddress = emailAddress!!
    }

    fun getemailAddress(): String? {
        return emailAddress
    }

    fun setuserName(userName: String?) {
        this.userName = userName!!
    }

    fun getphoneNumber(): String? {
        return phoneNumber
    }

    fun setphoneNumber(phoneNumber: String?) {
        this.phoneNumber = phoneNumber!!
    }

    fun getprofile(): String? {
        return profile
    }

    fun setprofile(jpSalary: String?) {
        this.profile = profile!!
    }


    override fun toString(): String {
        return "User(username='$userName',email='$emailAddress',phone num='$phoneNumber', profile=$profile)"
    }
}