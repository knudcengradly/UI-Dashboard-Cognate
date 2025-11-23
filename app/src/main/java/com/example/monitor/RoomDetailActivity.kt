package com.example.monitor

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.*

class RoomDetailActivity : AppCompatActivity() {

    private lateinit var db: FirebaseDatabase
    private lateinit var refComPLab3: DatabaseReference

    private lateinit var textViewFacultyName1: TextView
    private lateinit var textViewStatus1: TextView
    private lateinit var textViewDoorStatus1: TextView
    private lateinit var textViewTime1: TextView
    private lateinit var backgroundImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complab3_dashboard)

        val toolbar: Toolbar = findViewById(R.id.toolbar_room_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        db = FirebaseDatabase.getInstance()
        refComPLab3 = db.reference.child("CompLab3").child("Latest")

        textViewFacultyName1 = findViewById(R.id.textViewFacultyName1)
        textViewStatus1 = findViewById(R.id.textViewStatus1)
        textViewDoorStatus1 = findViewById(R.id.textViewDoorStatus1)
        textViewTime1 = findViewById(R.id.textViewTime1)
        backgroundImage = findViewById(R.id.background_image)

        // Apply grayscale filter
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        backgroundImage.colorFilter = ColorMatrixColorFilter(colorMatrix)

        monitorRoom(refComPLab3, textViewFacultyName1, textViewStatus1, textViewDoorStatus1, textViewTime1)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
