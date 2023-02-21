package com.kudos.mydoublescreenapp

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.gcacace.signaturepad.views.SignaturePad
import com.kudos.mydoublescreenapp.Utils.SecondaryDisplayCallback
import com.kudos.mydoublescreenapp.databinding.ActivityCustomerDisplayBinding


class CustomerDisplay(
    display: Display,
    context: Context,
    callback: SecondaryDisplayCallback
) : Presentation(context, display) {

    private var mCallback: SecondaryDisplayCallback? = null

    init {
        mCallback = callback
    }

    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: ActivityCustomerDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityCustomerDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initList()
        binding.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {
                Log.d("SIGN-PAD", "onStartSigning: Called")
            }

            override fun onSigned() {
                Log.d("SIGN-PAD", "onSigned: Called")
            }

            override fun onClear() {
                Log.d("SIGN-PAD", "onClear: Called")
            }
        })
        binding.actionButton.setOnClickListener {
            Log.d("TAG", "HELLO LOG: ")
            mCallback?.onButtonClicked()
            //Toast.makeText(context.applicationContext, "HELLO !!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initList() {
        cartAdapter = CartAdapter {}
        binding.rvCartList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }
    }

    fun updateList(cartItems: List<CartItem>) {
        if (cartItems.isEmpty()) {
            binding.mainLayout.visibility = View.GONE
            binding.splashLayout.visibility = View.VISIBLE
        } else {
            binding.mainLayout.visibility = View.VISIBLE
            binding.splashLayout.visibility = View.GONE
            setupCartItems(cartItems)
        }
    }

    private fun setupCartItems(cartItems: List<CartItem>) {
        cartAdapter.submitList(cartItems)
        cartAdapter.notifyDataSetChanged()
    }
}