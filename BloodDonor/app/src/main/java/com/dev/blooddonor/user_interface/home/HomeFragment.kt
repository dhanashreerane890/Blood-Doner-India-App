package com.dev.blooddonor.user_interface.home

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.dev.blooddonor.R
import com.dev.blooddonor.viewmodel.UserViewModel
import com.dev.blooddonor.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    var count1 = 0
    var count8 = 0
    var count2 = 0
    var count3 = 0
    var count4 = 0
    var count5 = 0
    var count6 = 0
    var count7 = 0

    //    var km:String?=null
    private lateinit var viewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val loadingDialog = activity?.let { LoadingDialog(it) }
        viewModel.km.observe(viewLifecycleOwner, Observer { value ->
            Log.d("TAG", "onActivityCreated: dhanu  " + value)


            btnFindDonor.setOnClickListener {
                if (value.equals("")) {
                    Toast.makeText(context, "Select Blood group", Toast.LENGTH_SHORT).show()
                }
                loadingDialog?.startLoadingDialog()
                val handler = Handler()
                handler.postDelayed({
                    loadingDialog?.dismissDialog()
                }, 1500)
                val action = HomeFragmentDirections.actionGlobalFindDonorFragment(value)
                findNavController().navigate(action)
            }
        })



        btnAPositive.setOnClickListener {
            viewModel._km.value = "A+"
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



        btnANegative.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count1)
            viewModel._km.value = "A-"
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

        btnBPositive.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
            viewModel._km.value = "B+"
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

        btnBNegative.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
            viewModel._km.value = "B-"
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

        btnOPositive.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
//            tvkm.text="O+"
            viewModel._km.value = "O+"
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

        btnONegative.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
//            km="O-"
            viewModel._km.value = "O-"
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

        btnABPositive.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
//            km="AB+"
            viewModel._km.value = "AB+"
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

        btnABNegative.setOnClickListener {
            Log.d("tag", "onViewCreated:" + count2)
//            km="AB-"
            viewModel._km.value = "AB-"
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

    fun colorChange() {
        if (count1 > 0) {
            color()
            btnAPositive.setBackgroundColor(Color.parseColor("#ff0000"))
            btnAPositive.setTextColor(Color.WHITE)
            Log.d("tag", "colorChange: " + Color.WHITE)

        } else if (count2 > 0) {
            color()
            btnANegative.setBackgroundColor(Color.parseColor("#ff0000"))
            btnANegative.setTextColor(Color.WHITE)
        } else if (count3 > 0) {
            color()
            btnBPositive.setBackgroundColor(Color.parseColor("#ff0000"))
            btnBPositive.setTextColor(Color.WHITE)
        } else if (count4 > 0) {
            color()
            btnBNegative.setBackgroundColor(Color.parseColor("#ff0000"))
            btnBNegative.setTextColor(Color.WHITE)
        } else if (count5 > 0) {
            color()
            btnOPositive.setBackgroundColor(Color.parseColor("#ff0000"))
            btnOPositive.setTextColor(Color.WHITE)
        } else if (count6 > 0) {
            color()
            btnONegative.setBackgroundColor(Color.parseColor("#ff0000"))
            btnONegative.setTextColor(Color.WHITE)
        } else if (count7 > 0) {
            color()
            btnABPositive.setBackgroundColor(Color.parseColor("#ff0000"))
            btnABPositive.setTextColor(Color.WHITE)
        } else if (count8 > 0) {
            color()
            btnABNegative.setBackgroundColor(Color.parseColor("#ff0000"))
            btnABNegative.setTextColor(Color.WHITE)
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
            btnANegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegative.setTextColor(Color.RED)
            btnBPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositive.setTextColor(Color.RED)
            btnBNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegative.setTextColor(Color.RED)
            btnOPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositive.setTextColor(Color.RED)
            btnONegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegative.setTextColor(Color.RED)
            btnABPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositive.setTextColor(Color.RED)
            btnABNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegative.setTextColor(Color.RED)

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
            btnAPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositive.setTextColor(Color.RED)
            btnBPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositive.setTextColor(Color.RED)
            btnBNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegative.setTextColor(Color.RED)
            btnOPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositive.setTextColor(Color.RED)
            btnONegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegative.setTextColor(Color.RED)
            btnABPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositive.setTextColor(Color.RED)
            btnABNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegative.setTextColor(Color.RED)
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
            btnAPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositive.setTextColor(Color.RED)
            btnANegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegative.setTextColor(Color.RED)
            btnBNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegative.setTextColor(Color.RED)
            btnOPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositive.setTextColor(Color.RED)
            btnONegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegative.setTextColor(Color.RED)
            btnABPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositive.setTextColor(Color.RED)
            btnABNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegative.setTextColor(Color.RED)

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

            btnAPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositive.setTextColor(Color.RED)
            btnANegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegative.setTextColor(Color.RED)
            btnBPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositive.setTextColor(Color.RED)
            btnOPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositive.setTextColor(Color.RED)
            btnONegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegative.setTextColor(Color.RED)
            btnABPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositive.setTextColor(Color.RED)
            btnABNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegative.setTextColor(Color.RED)

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

            btnAPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositive.setTextColor(Color.RED)
            btnANegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegative.setTextColor(Color.RED)
            btnBPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositive.setTextColor(Color.RED)
            btnBNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegative.setTextColor(Color.RED)
            btnONegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegative.setTextColor(Color.RED)
            btnABPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositive.setTextColor(Color.RED)
            btnABNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegative.setTextColor(Color.RED)

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

            btnAPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositive.setTextColor(Color.RED)
            btnANegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegative.setTextColor(Color.RED)
            btnBPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositive.setTextColor(Color.RED)
            btnBNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegative.setTextColor(Color.RED)
            btnOPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositive.setTextColor(Color.RED)
            btnABPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositive.setTextColor(Color.RED)
            btnABNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegative.setTextColor(Color.RED)

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

            btnAPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositive.setTextColor(Color.RED)
            btnANegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegative.setTextColor(Color.RED)
            btnBPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositive.setTextColor(Color.RED)
            btnBNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegative.setTextColor(Color.RED)
            btnOPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositive.setTextColor(Color.RED)
            btnONegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegative.setTextColor(Color.RED)
            btnABNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABNegative.setTextColor(Color.RED)

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

            btnAPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnAPositive.setTextColor(Color.RED)
            btnANegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnANegative.setTextColor(Color.RED)
            btnBPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBPositive.setTextColor(Color.RED)
            btnBNegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnBNegative.setTextColor(Color.RED)
            btnOPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnOPositive.setTextColor(Color.RED)
            btnONegative.setBackgroundColor(Color.parseColor("#ffffff"))
            btnONegative.setTextColor(Color.RED)
            btnABPositive.setBackgroundColor(Color.parseColor("#ffffff"))
            btnABPositive.setTextColor(Color.RED)

        }
    }


}