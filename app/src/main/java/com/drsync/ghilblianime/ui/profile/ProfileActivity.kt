package com.drsync.ghilblianime.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.drsync.ghilblianime.databinding.ActivityProfileBinding
import com.drsync.ghilblianime.util.Constants.createProgress

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            email.setOnClickListener{
                emailIntent()
            }
            github.setOnClickListener{
                githubIntent()
            }
            facebook.setOnClickListener {
                facebookIntent()
            }
            instagram.setOnClickListener {
                instagramIntent()
            }
            Glide.with(this@ProfileActivity)
                .load("https://avatars.githubusercontent.com/u/67430854?v=4")
                .placeholder(this@ProfileActivity.createProgress())
                .error(android.R.color.darker_gray)
                .into(imgProfile)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun emailIntent() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("m.b.fajri06@gmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Any subject if you want")
        intent.setPackage("com.google.android.gm")
        if (intent.resolveActivity(packageManager) != null) startActivity(intent) else Toast.makeText(
            this,
            "Gmail App is not installed",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun instagramIntent() {
        val url = "https://www.instagram.com/"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun facebookIntent() {
        val url = "https://id.linkedin.com/in/bennyfajri"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun githubIntent() {
        val url = "https://github.com/bennyfajri"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}