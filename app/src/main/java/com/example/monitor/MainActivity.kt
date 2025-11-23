package com.example.monitor

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class Monitor : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var refComPLab3: DatabaseReference
    private lateinit var refCpELab: DatabaseReference

    private lateinit var textViewFacultyName1: TextView
    private lateinit var textViewStatus1: TextView
    private lateinit var textViewDoorStatus1: TextView
    private lateinit var textViewTime1: TextView

    private lateinit var textViewFacultyName2: TextView
    private lateinit var textViewStatus2: TextView
    private lateinit var textViewDoorStatus2: TextView
    private lateinit var textViewTime2: TextView

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var backgroundImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        db = FirebaseDatabase.getInstance()
        refComPLab3 = db.reference.child("CompLab3").child("Latest")
        refCpELab = db.reference.child("CpeLab").child("Latest")

        // Room 1 (ComPLab3)
        textViewFacultyName1 = findViewById(R.id.textViewFacultyName1)
        textViewStatus1 = findViewById(R.id.textViewStatus1)
        textViewDoorStatus1 = findViewById(R.id.textViewDoorStatus1)
        textViewTime1 = findViewById(R.id.textViewTime1)

        // Room 2 (CpELab)
        textViewFacultyName2 = findViewById(R.id.textViewFacultyName2)
        textViewStatus2 = findViewById(R.id.textViewStatus2)
        textViewDoorStatus2 = findViewById(R.id.textViewDoorStatus2)
        textViewTime2 = findViewById(R.id.textViewTime2)

        // Find the background image
        backgroundImage = findViewById(R.id.background_image)

        // Apply grayscale filter
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        backgroundImage.colorFilter = ColorMatrixColorFilter(colorMatrix)

        monitorRoom(refComPLab3, textViewFacultyName1, textViewStatus1, textViewDoorStatus1, textViewTime1)
        monitorRoom(refCpELab, textViewFacultyName2, textViewStatus2, textViewDoorStatus2, textViewTime2)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val hamburgerMenu: ImageView = findViewById(R.id.hamburger_menu)

        hamburgerMenu.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawers()
                }
                R.id.nav_schedule -> {
                    val intent = Intent(this, ScheduleActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_about -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_exit -> finish()
            }
            drawerLayout.closeDrawers()
            true
        }

        val room1Card: LinearLayout = findViewById(R.id.card_room_1)
        room1Card.setOnClickListener {
            val intent = Intent(this, RoomDetailActivity::class.java)
            startActivity(intent)
        }

        // --- NEW CODE STARTS HERE ---
        val room2Card: LinearLayout = findViewById(R.id.card_room_2)
        room2Card.setOnClickListener {
            val intent = Intent(this, CpeLabDetailActivity::class.java)
            startActivity(intent)
        }
        // --- NEW CODE ENDS HERE ---
    }

    private fun monitorRoom(
        ref: DatabaseReference,
        facultyView: TextView,
        statusView: TextView,
        doorView: TextView,
        timeView: TextView
    ) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    facultyView.text = "Instructor: " + snapshot.child("facultyName").getValue(String::class.java).orEmpty()
                    statusView.text = "Status: " + snapshot.child("facultyStatus").getValue(String::class.java).orEmpty()
                    doorView.text = "Door: " + snapshot.child("doorStatus").getValue(String::class.java).orEmpty()
                    timeView.text = "Time: " + snapshot.child("timestamp").getValue(String::class.java).orEmpty()
                } else {
                    statusView.text = "Status: No data"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                statusView.text = "Error: ${error.message}"
            }
        })
    }
}