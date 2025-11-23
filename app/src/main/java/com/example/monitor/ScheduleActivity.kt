package com.example.monitor

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class ScheduleActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // The line that forced the app to draw under the status bar has been removed.
        setContentView(R.layout.activity_schedule)

        val toolbar: Toolbar = findViewById(R.id.toolbar_schedule)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout_schedule)
        navView = findViewById(R.id.nav_view_schedule)
        val hamburgerMenu: ImageView = findViewById(R.id.hamburger_menu_schedule)

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
                    drawerLayout.closeDrawers()
                }
                R.id.nav_about -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_exit -> finishAffinity()
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}