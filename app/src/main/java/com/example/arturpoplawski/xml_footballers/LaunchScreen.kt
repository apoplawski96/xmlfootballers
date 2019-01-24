package com.example.arturpoplawski.xml_footballers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class LaunchScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        val redCircle = findViewById(R.id.redCircleLaunchScreen) as CircleImageView
        val liverBird = findViewById(R.id.yellowLiverBird) as ImageView
        Picasso.get().load(R.mipmap.lfc_red).into(redCircle);

        liverBird.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

    }
}