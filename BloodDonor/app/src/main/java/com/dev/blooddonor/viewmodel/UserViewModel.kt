package com.dev.blooddonor.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.blooddonor.constant.Constant.NODE_USER
import com.dev.blooddonor.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserViewModel : ViewModel() {
    private var auth = FirebaseAuth.getInstance()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val _currentUsers = MutableLiveData<List<User>>()
    val currentUsers: LiveData<List<User>>
        get() = _currentUsers

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result


    private val dbUser = FirebaseDatabase.getInstance().getReference(NODE_USER)

    val _km = MutableLiveData<String>()
    val km: LiveData<String>
        get() = _km

    val _kms = MutableLiveData<String>()
    val kms: LiveData<String>
        get() = _kms

    fun addAuthor(user: User) {
        user.id = dbUser.push().key
        dbUser.child(user.id!!).setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }


    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
            val user = snapshot.getValue(User::class.java)
            user?.id = snapshot.key
            _user.value = user!!
        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val author = snapshot.getValue(User::class.java)
            author?.id = snapshot.key
            _user.value = author!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val author = snapshot.getValue(User::class.java)
            author?.id = snapshot.key
            _user.value = author!!
        }
    }

    fun getRealtimeUpdates() {
        dbUser.addChildEventListener(childEventListener)
    }


    fun fetchFilteredAuthors(index: String) {
        val dbAuthors = FirebaseDatabase.getInstance().getReference(NODE_USER)
            .orderByChild("bloodGroup")
            .equalTo(index)

        dbAuthors.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val authors = mutableListOf<User>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(User::class.java)
                        author?.id = authorSnapshot.key
                        Log.d("TAG", "onDataChange: " + authorSnapshot.key)
                        author?.let { authors.add(it) }
                    }
                    _users.value = authors

                    //.child("-MbPo6jAeHr-u2dQZpcJ")
                }
            }
        })
    }


    fun fetchCurrentUser(userId: String) {
        Log.d("SK", "fetchCurrentUser: " + userId)
        val userCur = FirebaseDatabase.getInstance().getReference(NODE_USER)
            .orderByChild("userId")
            .equalTo(userId)

        userCur.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("TAG", "onCancelled: " + error)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val currentUser = mutableListOf<User>()
                    for (authorSnapshot in snapshot.children) {
                        val user = authorSnapshot.getValue(User::class.java)
                        user?.id = authorSnapshot.key
                        Log.d("TAG", "onDataChange: " + authorSnapshot.key)
                        user?.let { currentUser.add(it) }
                    }
                    _currentUsers.value = currentUser

                }
            }
        })
    }

    fun updateAuthor(author: User) {
        dbUser.child(author.id!!).setValue(author)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }


}