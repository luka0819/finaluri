package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.example.myapplication.fragments.ProfileFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            supportActionBar?.hide()
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)


            mAuth=FirebaseAuth.getInstance()
            val user = mAuth.currentUser
/// თუ მომხმარებელის აქაუნთი შეყვანილია მას პირდაპირ მთავარ გვერდზე გადააგდებს თუ არადა შესვლის გვერდზე
            Handler().postDelayed({
                if (user!=null){
                    val TikTokMainActivity = Intent(this, FragmentsMain::class.java)
                    startActivity(TikTokMainActivity)
                    finish()
                }
                else{
                    val SignInActivity = Intent(this, SignInActivity::class.java)
                    startActivity(SignInActivity)
                    finish()
                }

            },4000)
    }
}