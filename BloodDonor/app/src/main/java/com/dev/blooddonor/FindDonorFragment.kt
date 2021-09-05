package com.dev.blooddonor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.dev.blooddonor.adapter.AuthorsAdapter
import com.dev.blooddonor.listener.OnClickListeners
import com.dev.blooddonor.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_find_donor.*


class FindDonorFragment : Fragment(), OnClickListeners {

    val MY_PERMISSIONS_REQUEST_CALL_PHONE = 123
    val args: FindDonorFragmentArgs by navArgs()
    private lateinit var viewModel: UserViewModel
    private val adapter = AuthorsAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_find_donor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bloodGroup = args.bloodGroup
        Log.d("TAG", "onViewCreated: sk" + bloodGroup)
        recyclerView.adapter = adapter
        viewModel.getRealtimeUpdates()
        viewModel.fetchFilteredAuthors(bloodGroup)
        viewModel.users.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "onViewCreated: " + it)
            adapter.setAuthors(it)
        })

    }

    override fun onClicked(position: Int, phoneNumber: String) {
        val uri = "tel:" + phoneNumber.trim()
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(uri)
        // Here, thisActivity is the current activity
        // Here, thisActivity is the current activity
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CALL_PHONE
                )
            }
            != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.CALL_PHONE),
                    MY_PERMISSIONS_REQUEST_CALL_PHONE
                )
            }

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            try {
                requireContext().startActivity(intent)
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

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the phone call
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
    }

}