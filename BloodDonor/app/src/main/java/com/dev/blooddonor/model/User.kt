package com.dev.blooddonor.model

import com.google.firebase.database.Exclude

data class User(
        @get:Exclude
        var id: String? = null,
        var name:String? = null,
        var eamil:String? = null,
        var phoneNumber:String? = null,
        var age:String? = null,
        var bloodGroup:String? = null,
        var userId:String? = null,
        var profileImage:String? = null,
        var userAddress:String? = null
        )






