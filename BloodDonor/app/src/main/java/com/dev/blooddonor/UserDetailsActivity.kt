package com.dev.blooddonor

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dev.blooddonor.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_user_details.*


class UserDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel

    //    private var source_add: String? = null
    private lateinit var firebaseUser: FirebaseUser
    val MY_PERMISSIONS_REQUEST_CALL_PHONE = 143

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.fetchCurrentUser(firebaseUser.uid)

        iv_back.setOnClickListener {
            onBackPressed()
        }
        if (intent != null && intent.extras != null) {
            user_name.text = intent.getStringExtra("name")
            user_age.text = intent.getStringExtra("age")
            user_blood_group.text = intent.getStringExtra("bloodGroup")
            user_phone.text = intent.getStringExtra("phoneNumber")
            user_email.text = intent.getStringExtra("email")
            if (intent.getStringExtra("imageUrl") == "") {
                user_image.setImageResource(R.drawable.profile_image)
            } else {
                Glide.with(this).load(intent.getStringExtra("imageUrl")).into(user_image)
            }
            user_address.text = intent.getStringExtra("address")

            user_send_email.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse("mailto:" + intent.getStringExtra("email"))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My email's subject")
                emailIntent.putExtra(Intent.EXTRA_TEXT, "My email's body")

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show()
                }
            }


            user_call.setOnClickListener {
                intent.getStringExtra("phoneNumber")?.let { it1 -> onCallUser(it1) }
            }
        }
        viewModel.currentUsers.observe(this, Observer {
            val source_add = it[0].userAddress
            Log.d("TAG", "onCreate: " + it[0].userAddress)


            find_location.setOnClickListener {
                val destination: String = user_address.text.toString()

                if (source_add?.isEmpty() == true) {
                    Toast.makeText(this, "Enter current user location", Toast.LENGTH_SHORT).show()
                }
                if (TextUtils.isEmpty(destination)) {
                    user_address.error = "Enter Destination Point"
                } else {
                    val sendstring = "http://maps.google.com/maps?saddr=" +
                            source_add +
                            "&daddr=" +
                            destination
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(sendstring)
                    )
                    startActivity(intent)
                }
                Log.d("TAG", "onCreate: " + "Hii dhanu")


            }

        })


    }

    fun onCallUser(phoneNumber: String) {
        val uri = "tel:" + phoneNumber.trim()
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(uri)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                MY_PERMISSIONS_REQUEST_CALL_PHONE
            )
        } else {
            try {
                startActivity(intent)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CALL_PHONE -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return
            }
        }
    }

}