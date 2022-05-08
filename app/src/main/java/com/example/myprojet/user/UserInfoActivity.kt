package com.example.myprojet.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.myprojet.R

class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)


        val buttonClicks= view?.findViewById<ImageButton>(R.id.take_picture_button)
        buttonClicks{

        }

    }

}