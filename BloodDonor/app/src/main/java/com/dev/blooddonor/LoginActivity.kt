package com.dev.blooddonor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.dev.blooddonor.dialog.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        Handler().postDelayed(
            {
                lotie_welcome.loop(false)

            }, 500
        )
        val loadingDialog = LoadingDialog(this)

        btnLogin.setOnClickListener {

            val email = textInputLayoutEmail.editText?.text.toString()
            val password = textInputLayoutPassword.editText?.text.toString()
            if (email.isEmpty()) {
                textInputLayoutEmail.error = "required"
                return@setOnClickListener

            }
            if (password.isEmpty() || password.length > 6) {
                textInputLayoutPassword.error = "required"
                return@setOnClickListener
            }
            loadingDialog.startLoadingDialog()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {


                    val intent = Intent(this, MainActivity::class.java)
                    loadingDialog.dismissDialog()

                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "This account is not available please Create Account",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadingDialog.dismissDialog()
                }
            }

        }
        tvCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}