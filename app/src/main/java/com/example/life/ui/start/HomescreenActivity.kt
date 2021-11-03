package com.example.life.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.life.R
import com.example.life.databinding.ActivityHomescreenBinding
import com.example.life.ui.host.ContentActivity

class HomescreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomescreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_homescreen)

        binding.buttonGame.setOnClickListener{ContentActivity.open(this, true)}
        binding.buttonWiki.setOnClickListener{ContentActivity.open(this, false)}
    }
}