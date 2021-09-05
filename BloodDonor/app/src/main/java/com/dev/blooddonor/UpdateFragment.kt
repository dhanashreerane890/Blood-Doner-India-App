package com.dev.blooddonor

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dev.blooddonor.constant.Constant
import com.dev.blooddonor.dialog.LoadingDialog
import com.dev.blooddonor.model.User
import com.dev.blooddonor.viewmodel.UserViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.IOException


class UpdateFragment : Fragment(R.layout.fragment_update) {
    private lateinit var viewModel: UserViewModel

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var loadingDialog: LoadingDialog

    private var filePath: Uri? = null
    private var bitmap: Bitmap? = null
    private var usersId: String? = null
    private var emails: String? = null
    private var curUserId: String? = null
    private var bloodGrp: String? = null
    private var user_images: String? = null

    private val PICK_IMAGE_REQUEST: Int = 5465

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    private var age = 0
    var count1 = 0
    var count2 = 0
    var count3 = 0
    var count4 = 0
    var count5 = 0
    var count6 = 0
    var count7 = 0
    var count8 = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        usersId = firebaseUser.uid
        viewModel.fetchCurrentUser(usersId!!)
        databaseReference = FirebaseDatabase.getInstance().reference.child(Constant.NODE_USER)



        viewModel.currentUsers.observe(viewLifecycleOwner, Observer { user ->
            Log.d("TAG", "onViewCreated:  sk " + user[0].userId)
            Log.d("TAG", "onViewCreated:  sk " + user[0].eamil)

            tv_userName.text = user[0].name
            et_name.setText(user[0].name)
            etAddress.setText(user[0].userAddress ?: "")
            etNumber.setText(user[0].phoneNumber)
            tvCount.text = user[0].age
            age = user[0].age?.toInt() ?: 0
            emails = user[0].eamil
            curUserId = user[0].id
            bloodGrp = user[0].bloodGroup
            user_images = user[0].profileImage
            if (user[0].profileImage == "") {
                ivUpdateProfile.setImageResource(R.drawable.profile_image)
            } else {
                Log.d("TAG", "onDataChange: " + user[0].profileImage)
                context?.let { Glide.with(it).load(user[0].profileImage).into(ivUpdateProfile) }
            }

        })


        storageRef = FirebaseStorage.getInstance().reference


        btnPlus.setOnClickListener {

            if (age <= 100) {
                age += 1
                updateAge()
            }
        }

        btnMinus.setOnClickListener {

            if (age > 0) {
                age -= 1
                updateAge()
            }
        }

        btnAPositiveUpdate.setOnClickListener {
            viewModel._kms.value = "A+"
            count1 = 1
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0
            count8 = 0
            Log.d("tag", "onViewCreated: " + count1)
            colorChange()
        }


        btnANegativeUpdate.setOnClickListener {
            viewModel._kms.value = "A-"
            Log.d("tag", "onViewCreated:" + count1)
            count1 = 0
            count2 = 1
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0
            count8 = 0
            colorChange()
        }

        btnBPositiveUpdate.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
            viewModel._kms.value = "B+"
            count1 = 0
            count2 = 0
            count3 = 1
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0
            count8 = 0
            colorChange()
        }

        btnBNegativeUpdate.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
            viewModel._kms.value = "B-"
            count1 = 0
            count2 = 0
            count3 = 0
            count4 = 1
            count5 = 0
            count6 = 0
            count7 = 0
            count8 = 0
            colorChange()
        }

        btnOPositiveUpdate.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
            viewModel._kms.value = "O+"
            count1 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 1
            count6 = 0
            count7 = 0
            count8 = 0
            colorChange()
        }

        btnONegativeUpdate.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
            viewModel._kms.value = "O-"
            count1 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 1
            count7 = 0
            count8 = 0
            colorChange()
        }

        btnABPositiveUpdate.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
            viewModel._kms.value = "AB+"
            count1 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 1
            count8 = 0
            colorChange()
        }

        btnABNegativeUpdate.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
            viewModel._kms.value = "AB-"
            count1 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0
            count8 = 1
            colorChange()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uploadProfile()
    }

    private fun uploadProfile() {
        btnChange.setOnClickListener {
            Dexter.withActivity(activity).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(
                            Intent.createChooser(intent, "Select Image"),
                            PICK_IMAGE_REQUEST
                        )
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()


        }

        viewModel.kms.observe(viewLifecycleOwner, Observer { grp ->
            bloodGrp = grp
            Log.d("TAG", "uploadProfile: " + grp)
        })


        loadingDialog = activity?.let { LoadingDialog(it) }!!
        btn_Update.setOnClickListener {
            val address = etAddress.text.toString()
            if (address.isEmpty()) {
                etAddress.error = "required"
                return@setOnClickListener
            }
            loadingDialog.startLoadingDialog()
            updateProfileImage()
            viewModel.result.observe(viewLifecycleOwner, Observer {
                if (it == null) {
                    loadingDialog.dismissDialog()

                } else {
                    loadingDialog.dismissDialog()
                }
                val message = if (it == null) {
                    getString(R.string.user_updated)
                } else {
                    getString(R.string.error, it.message)
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            })


        }

    }

    private fun updateProfileImage() {
        val uploader = storageRef.child("users/" + "img" + System.currentTimeMillis())
        filePath?.let {
            uploader.putFile(it).addOnSuccessListener {
                uploader.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                    override fun onSuccess(uri: Uri?) {

                        val user = User(
                            curUserId,
                            et_name.text.toString(),
                            emails,
                            etNumber.text.toString(),
                            tvCount.text.toString(),
                            bloodGrp,
                            firebaseUser.uid,
                            uri.toString() ?: user_images,
                            etAddress.text.toString() ?: ""
                        )
                        viewModel.updateAuthor(user)

                        Log.d("TAG", "onSuccess: " + uri.toString())

                    }

                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (data != null) {
                filePath = data.data
            }
            try {
                val inputStream = filePath?.let { context?.contentResolver?.openInputStream(it) }
                bitmap = BitmapFactory.decodeStream(inputStream)
                ivUpdateProfile.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    fun updateAge() {
        tvCount.text = "$age"
    }

    fun colorChange() {
        if (count1 > 0) {
            color()
            btnAPositiveUpdate.setBackgroundColor(Color.parseColor("#ff0000"))
            btnAPositiveUpdate.setTextColor(Color.WHITE)
            Log.d("tag", "colorChange: " + Color.WHITE)

        } else if (count2 > 0) {
            color()
            btnANegativeUpdate.setBackgroundColor(Color.parseColor("#ff0000"))
            btnANegativeUpdate.setTextColor(Color.WHITE)
        } else if (count3 > 0) {
            color()
            btnBPositiveUpdate.setBackgroundColor(Color.parseColor("#ff0000"))
            btnBPositiveUpdate.setTextColor(Color.WHITE)
        } else if (count4 > 0) {
            color()
            btnBNegativeUpdate.setBackgroundColor(Color.parseColor("#ff0000"))
            btnBNegativeUpdate.setTextColor(Color.WHITE)
        } else if (count5 > 0) {
            color()
            btnOPositiveUpdate.setBackgroundColor(Color.parseColor("#ff0000"))
            btnOPositiveUpdate.setTextColor(Color.WHITE)
        } else if (count6 > 0) {
            color()
            btnONegativeUpdate.setBackgroundColor(Color.parseColor("#ff0000"))
            btnONegativeUpdate.setTextColor(Color.WHITE)
        } else if (count7 > 0) {
            color()
            btnABPositiveUpdate.setBackgroundColor(Color.parseColor("#ff0000"))
            btnABPositiveUpdate.setTextColor(Color.WHITE)
        } else if (count8 > 0) {
            color()
            btnABNegativeUpdate.setBackgroundColor(Color.parseColor("#ff0000"))
            btnABNegativeUpdate.setTextColor(Color.WHITE)
        }
    }


    fun color() {
        if (count1 == 1 && count2 == 0 && count3 == 0 && count4 == 0 && count5 == 0 && count6 == 0 && count7 == 0 && count8 == 0) {
            count1 = 0
            count8 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0
            Log.d("tag", "color: " + count1)

            btnANegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegativeUpdate.setTextColor(Color.RED)
            btnBPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositiveUpdate.setTextColor(Color.RED)
            btnBNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegativeUpdate.setTextColor(Color.RED)
            btnOPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositiveUpdate.setTextColor(Color.RED)
            btnONegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegativeUpdate.setTextColor(Color.RED)
            btnABPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositiveUpdate.setTextColor(Color.RED)
            btnABNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegativeUpdate.setTextColor(Color.RED)

        }
        if (count1 == 0 && count2 == 1 && count3 == 0 && count4 == 0 && count5 == 0 && count6 == 0 && count7 == 0 && count8 == 0) {
            count1 = 0
            count8 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0
            btnAPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositiveUpdate.setTextColor(Color.RED)

            btnBPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositiveUpdate.setTextColor(Color.RED)
            btnBNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegativeUpdate.setTextColor(Color.RED)
            btnOPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositiveUpdate.setTextColor(Color.RED)
            btnONegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegativeUpdate.setTextColor(Color.RED)
            btnABPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositiveUpdate.setTextColor(Color.RED)
            btnABNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegativeUpdate.setTextColor(Color.RED)
        }
        if (count1 == 0 && count2 == 0 && count3 == 1 && count4 == 0 && count5 == 0 && count6 == 0 && count7 == 0 && count8 == 0) {
            count1 = 0
            count8 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0
            btnAPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositiveUpdate.setTextColor(Color.RED)
            btnANegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegativeUpdate.setTextColor(Color.RED)

            btnBNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegativeUpdate.setTextColor(Color.RED)
            btnOPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositiveUpdate.setTextColor(Color.RED)
            btnONegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegativeUpdate.setTextColor(Color.RED)
            btnABPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositiveUpdate.setTextColor(Color.RED)
            btnABNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegativeUpdate.setTextColor(Color.RED)

        }

        if (count1 == 0 && count2 == 0 && count3 == 0 && count4 == 1 && count5 == 0 && count6 == 0 && count7 == 0 && count8 == 0) {
            count1 = 0
            count8 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0

            btnAPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositiveUpdate.setTextColor(Color.RED)
            btnANegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegativeUpdate.setTextColor(Color.RED)
            btnBPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositiveUpdate.setTextColor(Color.RED)

            btnOPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositiveUpdate.setTextColor(Color.RED)
            btnONegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegativeUpdate.setTextColor(Color.RED)
            btnABPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositiveUpdate.setTextColor(Color.RED)
            btnABNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegativeUpdate.setTextColor(Color.RED)

        }

        if (count1 == 0 && count2 == 0 && count3 == 0 && count4 == 0 && count5 == 1 && count6 == 0 && count7 == 0 && count8 == 0) {
            count1 = 0
            count8 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0

            btnAPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositiveUpdate.setTextColor(Color.RED)
            btnANegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegativeUpdate.setTextColor(Color.RED)
            btnBPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositiveUpdate.setTextColor(Color.RED)
            btnBNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegativeUpdate.setTextColor(Color.RED)

            btnONegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegativeUpdate.setTextColor(Color.RED)
            btnABPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositiveUpdate.setTextColor(Color.RED)
            btnABNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegativeUpdate.setTextColor(Color.RED)

        }

        if (count1 == 0 && count2 == 0 && count3 == 0 && count4 == 0 && count5 == 0 && count6 == 1 && count7 == 0 && count8 == 0) {
            count1 = 0
            count8 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0

            btnAPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositiveUpdate.setTextColor(Color.RED)
            btnANegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegativeUpdate.setTextColor(Color.RED)
            btnBPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositiveUpdate.setTextColor(Color.RED)
            btnBNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegativeUpdate.setTextColor(Color.RED)
            btnOPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositiveUpdate.setTextColor(Color.RED)

            btnABPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositiveUpdate.setTextColor(Color.RED)
            btnABNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegativeUpdate.setTextColor(Color.RED)
        }

        if (count1 == 0 && count2 == 0 && count3 == 0 && count4 == 0 && count5 == 0 && count6 == 0 && count7 == 1 && count8 == 0) {
            count1 = 0
            count8 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0

            btnAPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositiveUpdate.setTextColor(Color.RED)
            btnANegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegativeUpdate.setTextColor(Color.RED)
            btnBPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositiveUpdate.setTextColor(Color.RED)
            btnBNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegativeUpdate.setTextColor(Color.RED)
            btnOPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositiveUpdate.setTextColor(Color.RED)
            btnONegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegativeUpdate.setTextColor(Color.RED)

            btnABNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegativeUpdate.setTextColor(Color.RED)

        }

        if (count1 == 0 && count2 == 0 && count3 == 0 && count4 == 0 && count5 == 0 && count6 == 0 && count7 == 0 && count8 == 1) {
            count1 = 0
            count8 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            count5 = 0
            count6 = 0
            count7 = 0

            btnAPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositiveUpdate.setTextColor(Color.RED)
            btnANegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegativeUpdate.setTextColor(Color.RED)
            btnBPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositiveUpdate.setTextColor(Color.RED)
            btnBNegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegativeUpdate.setTextColor(Color.RED)
            btnOPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositiveUpdate.setTextColor(Color.RED)
            btnONegativeUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegativeUpdate.setTextColor(Color.RED)
            btnABPositiveUpdate.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositiveUpdate.setTextColor(Color.RED)

        }
    }


}
