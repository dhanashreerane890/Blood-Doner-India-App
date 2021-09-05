package com.dev.blooddonor.user_interface.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dev.blooddonor.LoginActivity
import com.dev.blooddonor.R
import com.dev.blooddonor.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        viewModel.fetchCurrentUser(auth.uid!!)

        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.currentUsers.observe(viewLifecycleOwner, Observer { user ->
            tv_name.text = user[0].name
            if (user != null) {
                if (user[0].profileImage == "") {
                    ivBecomeDonor.setImageResource(R.drawable.profile_image)
                } else {
                    context?.let { Glide.with(it).load(user[0].profileImage).into(ivBecomeDonor) }
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnUpdateInfo.setOnClickListener {
            val action = DashboardFragmentDirections.actionGlobalUpdateFragment()
            findNavController().navigate(action)
        }
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }


    }


}