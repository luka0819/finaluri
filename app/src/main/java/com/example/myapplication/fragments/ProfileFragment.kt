package com.example.myapplication.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.ImageView.ScaleType.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.SignInActivity
import com.example.myapplication.data.realtimedatabasepersoninfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView as CircleImageView1


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var id_txt: TextView
    private lateinit var id_name: TextView
    private lateinit var id_email: TextView
    private lateinit var imageview: ImageView
    private lateinit var imageview1: CircleImageView1



    private lateinit var gamosvla: Button
    private lateinit var dataBase: DatabaseReference
    private lateinit var saxeli: TextView
    private lateinit var gvari: TextView
    private lateinit var asaki :TextView
    private lateinit var saxeli1 :TextView
    private lateinit var gvari1 :TextView
    private lateinit var shecvla : Button






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth= FirebaseAuth.getInstance()
        dataBase = FirebaseDatabase.getInstance().getReference("userInfo")


        val currentuser = mAuth.currentUser

        id_name = view.findViewById(R.id.id_name)
        id_email= view.findViewById(R.id.id_email)
        imageview = view.findViewById(R.id.imageview)
        gamosvla = view.findViewById(R.id.gamosvla)

        asaki = view.findViewById(R.id.asaki)
        saxeli1 = view.findViewById(R.id.saxeli1)
        gvari = view.findViewById(R.id.gvari1)
        imageview1 = view.findViewById(R.id.imageview1)

        shecvla = view.findViewById(R.id.changeprofile)



        id_email.text = currentuser?.email
        id_name.text = currentuser?.displayName

        if(mAuth.currentUser?.uid != null){
            dataBase.child(mAuth.currentUser?.uid!!).addValueEventListener(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    val b = snapshot.getValue(realtimedatabasepersoninfo::class.java)
                    if (b!=null){

                        saxeli1.text = b.firstname


                        gvari1.text = b.lastname
                        asaki.text = b.age

                    }
                }


                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
            shecvla.setOnClickListener {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (getContext()?.let { it1 ->
                            ActivityCompat.checkSelfPermission(it1,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        } ==







                        PackageManager.PERMISSION_DENIED){
                        //permission denied
                        val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                        //show popup to request runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, IMAGE_PICK_CODE)
                    }
                }
                else{
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, IMAGE_PICK_CODE)
                }
            }
        }





        Glide.with(this)
            .load(currentuser?.photoUrl)
            .circleCrop()
            .into(imageview)


        gamosvla.setOnClickListener{
            startActivity(Intent(getContext(),SignInActivity::class.java))
            getActivity()?.finish()

            mAuth.signOut()
        }


}companion object {
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
}
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, IMAGE_PICK_CODE)
                }
                else{
                    //permission from popup denied

                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageview1.setImageURI(data?.data)

        }
    }



}