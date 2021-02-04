package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.myapplication.data.realtimedatabasepersoninfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var etEmailAddress: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSingUp: AppCompatButton
    private lateinit var btnLogin: ImageView

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var age : EditText
    private lateinit var dataBase: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        dataBase = FirebaseDatabase.getInstance().getReference("userInfo")

        firstName = findViewById(R.id.yourFirstNameEditText)
        lastName = findViewById(R.id.yourLastNameEditText)
        age = findViewById(R.id.ageEditText)
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnSingUp = findViewById(R.id.btnSingUp)
        btnLogin = findViewById(R.id.backtologin)

        btnLogin.setOnClickListener(){
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        btnSingUp.setOnClickListener(){
            val frsname = firstName.text.toString()
            val lstname = lastName.text.toString()
            val age = age.text.toString()
            val email = etEmailAddress.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            if(password==confirmPassword) {

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Error1", Toast.LENGTH_SHORT).show()

                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val p = realtimedatabasepersoninfo(frsname,lstname,age)
                                if(firebaseAuth.currentUser?.uid !=null){
                                    dataBase.child(firebaseAuth.currentUser?.uid!!).setValue(p).addOnCompleteListener {task ->
                                        if(task.isSuccessful){
                                            Toast.makeText(this,"ინფორმაცია წარმატებით დაემატა",Toast.LENGTH_SHORT).show()
                                        }else{
                                            Toast.makeText(this,"gaekuke aqedan",Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                }







                                startActivity(Intent(this, FragmentsMain::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Error2", Toast.LENGTH_SHORT).show()
                            }
                        }

                }
            }
        }
    }

    private fun singUpUser (){
        val email = etEmailAddress.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank.",Toast.LENGTH_SHORT).show()
            return
            }

        if(password != confirmPassword){
            Toast.makeText(this,"Password and Confirm Password doesn't match ",Toast.LENGTH_SHORT).show()
            return
        }



        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"Error Creating User.",Toast.LENGTH_SHORT).show()
                }
            }
    }

}