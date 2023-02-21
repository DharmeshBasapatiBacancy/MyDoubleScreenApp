package com.kudos.mydoublescreenapp

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display
import com.kudos.mydoublescreenapp.databinding.AnotherCustomerDisplayViewBinding

class AnotherCustomerDisplay(
    display: Display,
    context: Context,
) : Presentation(context, display) {

    private lateinit var binding: AnotherCustomerDisplayViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AnotherCustomerDisplayViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}