package com.dalima.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dalima.instagramclone.databinding.ActivityLoginBinding
import com.dalima.instagramclone.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding.btnLogin.setOnClickListener {
            if (binding.txtemail.editText?.text.toString().equals("") or
                binding.txtpassword.editText?.text.toString().equals("")){
                Toast.makeText(this@LoginActivity, "please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else{
                var user= User(binding.txtemail.editText?.text.toString(),
                        binding.txtpassword.editText?.text.toString())
                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!).addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this@LoginActivity, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}