package com.kudos.mydoublescreenapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gcacace.signaturepad.views.SignaturePad.OnSignedListener
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.kudos.mydoublescreenapp.Utils.getCustomerDisplay
import com.kudos.mydoublescreenapp.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.util.*

class MainActivity : AppCompatActivity(), Utils.SecondaryDisplayCallback {

    private var itemsList: MutableList<CartItem> = mutableListOf()
    private var i: Int = 0
    private lateinit var binding: ActivityMainBinding
    private var presentation: CustomerDisplay? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            clearSignButton.setOnClickListener {
                signaturePad.clear()
                canvasView.clear()
            }
            signaturePad.setOnSignedListener(object : OnSignedListener {
                override fun onStartSigning() {
                    Log.d("SIGN-PAD", "onStartSigning: Called")
                    yourSignLabelLayout.visibility = View.INVISIBLE
                }

                override fun onSigned() {
                    Log.d("SIGN-PAD", "onSigned: Called")
                }

                override fun onClear() {
                    Log.d("SIGN-PAD", "onClear: Called")
                    yourSignLabelLayout.visibility = View.VISIBLE
                }
            })
            showSignButton.setOnClickListener {
                showSignatureInImageView()
            }
        }

        getCustomerDisplay(this)?.let { display ->
            presentation = CustomerDisplay(display, applicationContext, this)
        }

        binding.setTextButton.setOnClickListener {
            i++
            itemsList.add(CartItem(i, "Item Name #$i"))
            presentation?.updateList(itemsList)
            if (i == 10) {
                i = 0
                itemsList.clear()
                presentation?.updateList(emptyList())
            }
        }

        binding.goToSecondScreenButton.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        Log.d("TAG", "US FORMAT NUMBER (2099216581): ${getUSFormatNumber("2099216581")}")
    }

    private fun showSignatureInImageView() {
        //val signatureBitmap = binding.signaturePad.transparentSignatureBitmap
        val signatureBitmap = binding.canvasView.getBitmap()
        val file = File(filesDir, "signature_${Date().time}.png")
        val fos = FileOutputStream(file)
        signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        val bmp = BitmapFactory.decodeFile(file.absolutePath)
        binding.viewSignImageView.setImageBitmap(bmp)
        fos.close()
    }

    private fun getUSFormatNumber(inputNumber: String): String {

        val pnu: PhoneNumberUtil = PhoneNumberUtil.getInstance()
        var outPutNumber = inputNumber
        try {
            val pn: Phonenumber.PhoneNumber = pnu.parse(inputNumber, "US")
            outPutNumber =
                pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)

        } catch (e: NumberFormatException) {
            return inputNumber
        }
        return outPutNumber
    }


    override fun onResume() {
        presentation?.show()
        super.onResume()
    }

    override fun onPause() {
        presentation?.hide()
        super.onPause()
    }

    override fun onButtonClicked() {
        Toast.makeText(this, "OH HELLO", Toast.LENGTH_SHORT).show()
    }
}