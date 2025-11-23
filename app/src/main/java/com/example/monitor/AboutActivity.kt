package com.example.monitor

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class AboutActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // The line that forced the app to draw under the status bar has been removed.
        setContentView(R.layout.activity_about)

        val toolbar: Toolbar = findViewById(R.id.toolbar_about)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout_about)
        navView = findViewById(R.id.nav_view_about)
        val hamburgerMenu: ImageView = findViewById(R.id.hamburger_menu_about)

        hamburgerMenu.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, Monitor::class.java)
                    startActivity(intent)
                }
                R.id.nav_schedule -> {
                    val intent = Intent(this, ScheduleActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_about -> {
                    drawerLayout.closeDrawers()
                }
                R.id.nav_exit -> finishAffinity()
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}