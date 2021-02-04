package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.myapplication.VideoEditor.PortraitCameraActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FragmentsMain : AppCompatActivity() {
    private lateinit var camerabutton: FloatingActionButton


    private var CAMERA_PERMISSION_REQUEST_CODE = 8888


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragmentebi_main)
        supportActionBar?.hide()

        camerabutton = findViewById(R.id.camerabutton)

        camerabutton.setOnClickListener() {

            startActivity(Intent(this, PortraitCameraActivity::class.java))
            intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK)
            Animatoo.animateSlideUp(this)


            }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfig = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_search,
            R.id.navigation_inbox,
            R.id.profileFragment
        ))
        setupActionBarWithNavController(navController, appBarConfig)
        navView.setupWithNavController(navController)
        
        
        
        



    }
    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        // request camera permission if it has not been grunted.
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@FragmentsMain,
                    "permission has been grunted.",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@FragmentsMain,
                    "[WARN] permission is not grunted.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}