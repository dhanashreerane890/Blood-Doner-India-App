package com.dev.blooddonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.blooddonor.dialog.LoadingDialog
import com.dev.blooddonor.model.User
import com.dev.blooddonor.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.view.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val loadingDialog = LoadingDialog(this)
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        btnSignUp.setOnClickListener {
            val email = textInputLayoutEmailSignUp.editText?.text.toString()
            val password = textInputLayoutPasswordSignUp.editText?.text.toString()
            val name = textInputLayoutNameSignUp.editText?.text.toString()
            val phoneNumber = textInputLayoutPhoneNumberSignUp.editText?.text.toString()
            val age = textInputLayoutAgeSignUp.editText?.text.toString()
            val bloodGroup = textInputLayout2.autoCompleteTextView.text.toString()
            if (name.isEmpty()) {
                textInputLayoutNameSignUp.error = "required"
                return@setOnClickListener

            }
            if (email.isEmpty()) {
                textInputLayoutEmailSignUp.error = "required"
                return@setOnClickListener

            }
            if (password.isEmpty() && password.length > 6) {
                textInputLayoutPasswordSignUp.error = "required"
                return@setOnClickListener

            }
            if (phoneNumber.isEmpty()) {
                textInputLayoutPhoneNumberSignUp.error = "required"
                return@setOnClickListener

            }

            if (age.isEmpty()) {
                textInputLayoutAgeSignUp.error = "required"
                return@setOnClickListener

            }

            if (bloodGroup.isEmpty()) {
                textInputLayout2.error = "required"
                return@setOnClickListener

            }
            loadingDialog.startLoadingDialog()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                    val user = User("", name, email, phoneNumber, age, bloodGroup, auth.uid, "", "")
                    viewModel.addAuthor(user)
                    loadingDialog.dismissDialog()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    loadingDialog.dismissDialog()
                }
            }


        }

        viewModel.result.observe(this, Observer {
            val message = if (it == null) {
                getString(R.string.user_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

        })


    }


    override fun onResume() {
        super.onResume()
        val bloodgroups = resources.getStringArray(R.array.blood_groups)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, bloodgroups)
        autoCompleteTextView.setAdapter(arrayAdapter)
    }
}