package com.example.arturpoplawski.xml_footballers

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class LaunchScreen : AppCompatActivity() {

    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        val wifiConnector = WifiConnector(applicationContext, Logger())

        val redCircle = findViewById(R.id.redCircleLaunchScreen) as CircleImageView
        val liverBird = findViewById(R.id.yellowLiverBird) as ImageView
        Picasso.get().load(R.mipmap.lfc_red).into(redCircle);

        liverBird.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)

            if (i%2 == 0) {
                Log.d("2137", "zidoo")
                wifiConnector.connect("Z9X", "11223344")
                Toast.makeText(this, "Connecting to Z9X", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("2137", "tele")
                wifiConnector.connect("MI 9", "11223344")
                Toast.makeText(this, "Connecting to Z9X", Toast.LENGTH_SHORT).show()
//                wifiConnector.connect("Livebox-4B28", "ibanez68")
            }
            i++
        }

    }
}