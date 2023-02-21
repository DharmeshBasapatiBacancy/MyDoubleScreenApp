package com.kudos.mydoublescreenapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kudos.mydoublescreenapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var presentation: AnotherCustomerDisplay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.getCustomerDisplay(this)?.let { display ->
            presentation = AnotherCustomerDisplay(display, applicationContext)
        }
        binding.goBackButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        presentation.show()
        super.onResume()
    }

    override fun onPause() {
        presentation.hide()
        super.onPause()
    }
}