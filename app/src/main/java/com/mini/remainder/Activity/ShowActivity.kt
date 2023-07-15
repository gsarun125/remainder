package com.mini.remainder.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mini.remainder.R
import com.mini.remainder.databinding.ActivityShowBinding


class ShowActivity : AppCompatActivity() {
    lateinit var binding:ActivityShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val a = i.getStringExtra("a")
        binding.textView.text =a

        binding.back.setOnClickListener {
            val i=Intent(this,HomeActivity::class.java)
            startActivity(i)
        }


    }
}