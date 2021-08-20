package com.example.kcmav1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.kcmav1.utils.AppPreferences


class UserProfileFragment : AppCompatActivity() {
    lateinit var text_email:TextView
    lateinit var text_name:TextView
    lateinit var text_number:TextView
    lateinit var text_diocese:TextView
    lateinit var my_toolbar:Toolbar
    lateinit var logout:Button
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_user_profile)
        my_toolbar=findViewById(R.id.prof_toolbar)
        setSupportActionBar(my_toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        AppPreferences.init(this)
        text_email = findViewById(R.id.profile_email)
        text_name = findViewById(R.id.profile_username)
        text_number = findViewById(R.id.profile_number)
        text_diocese = findViewById(R.id.profile_diocese)
        logout=findViewById(R.id.logout_user)
        if (AppPreferences.isLogin) {
            text_email.text =  AppPreferences.email
            text_number.text = AppPreferences.number
            text_name.text = AppPreferences.username
            text_diocese.text = AppPreferences.diocese
        }

        logout.setOnClickListener(View.OnClickListener {

            AppPreferences.logOut()
            AppPreferences.isLogin==false
            Toast.makeText(applicationContext,"Logged out successfully",Toast.LENGTH_LONG).show()
            val i=Intent(applicationContext,LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finishAffinity()
            finish()
        })
        }


    override fun onBackPressed() {

        super.onBackPressed()
        this.finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    }




