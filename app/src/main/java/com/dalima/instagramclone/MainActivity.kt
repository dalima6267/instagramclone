package com.dalima.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor=Color.TRANSPARENT
        // handler is a class which provided delay for some time
        Handler(Looper.getMainLooper()).postDelayed({
if (FirebaseAuth.getInstance().currentUser==null)
         startActivity(Intent(this,SignUpActivity::class.java))
            else
    startActivity(Intent(this,HomeActivity::class.java))
    finish()


        },3000)
    }
}
