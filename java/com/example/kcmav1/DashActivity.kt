package com.example.kcmav1

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.MenuItemCompat
import com.example.kcmav1.dashboard.forums.ForumFragment
import com.example.kcmav1.dashboard.search.HomeFragment
import com.example.kcmav1.utils.AppPreferences
import com.example.kcmav1.utils.toast
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.okhttp.OkHttpClient
import de.hdodenhof.circleimageview.CircleImageView

class DashActivity : AppCompatActivity() {
    lateinit var open_search:MaterialCardView
    lateinit var open_upload:FloatingActionButton
    lateinit var user_name:TextView
    lateinit var dash_toolbar:Toolbar
    lateinit var open_forums_activity:MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        else{
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        setContentView(R.layout.activity_dash)
        AppPreferences.init(this)

        val client:OkHttpClient
        open_search=findViewById(R.id.open_search)
        open_upload=findViewById(R.id.open_upload_activity)
        open_forums_activity=findViewById(R.id.open_forums_activity)
        user_name=findViewById(R.id.welcome_text)
        dash_toolbar=findViewById(R.id.dash_toolbar)

        setSupportActionBar(dash_toolbar)

        if (AppPreferences.isLogin) {
            user_name.text = AppPreferences.username
        }
        open_search.setOnClickListener {
            val i=Intent(applicationContext,HomeFragment::class.java)
            startActivity(i)
        }

        open_upload.setOnClickListener {
            val i=Intent(applicationContext,UploadYourScriptsActivity::class.java)
            startActivity(i)
        }
        open_forums_activity.setOnClickListener {
            val i=Intent(applicationContext,ForumFragment::class.java)
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dash_menu,menu)


        val menu1:Menu=dash_toolbar.menu
        val item: MenuItem = menu1.findItem(R.id.menu_item1)
            val view:View=MenuItemCompat.getActionView(item)
            val profile:CircleImageView=view.findViewById(R.id.profile_image)
            profile.setOnClickListener {
                val i=Intent(applicationContext,UserProfileFragment::class.java)
                startActivity(i)
            }


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId)
        {
            R.id.menu_item2->{
                toast("Love Clicked")
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}