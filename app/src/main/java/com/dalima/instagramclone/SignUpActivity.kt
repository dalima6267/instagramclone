package com.dalima.instagramclone

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dalima.instagramclone.databinding.ActivitySignUpBinding
import com.dalima.instagramclone.models.User
import com.dalima.instagramclone.utils.USER_NODE
import com.dalima.instagramclone.utils.USER_PROFILE_FOLDER
import com.dalima.instagramclone.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class SignUpActivity : AppCompatActivity() {

    
    val binding by lazy {
   ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User

    private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let {

 uploadImage(uri, USER_PROFILE_FOLDER){
    if(it==null){

    }
    else{
        user.image=it
        binding.imgProfile.setImageURI(uri)
    }
}

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text = "<font color=#FF000000>Already have an Account</font> <font color=#1E88E5>login</font>"
        binding.txtlogin.setText(Html.fromHtml(text))
        user=User()
        if (intent.hasExtra("MODE")){
            if (intent.getIntExtra("MODE",-1)==1){
                binding.btnRegister.text="Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                         user =it.toObject<User>()!!
                        if (!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.imgProfile)


                        }
                        binding.txtusername.editText?.setText(user.name)
                        binding.txtemail.editText?.setText(user.email)
                        binding.txtpassword.editText?.setText(user.password)

                    }
            }
        }
        binding.btnRegister.setOnClickListener {
            if(intent.hasExtra("MODE")){
                if (intent.getIntExtra("MODE",-1)==1){
                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))

                            finish()

                        }
                }
            }
            if (binding.txtusername.editText?.text.toString().equals("") or
                binding.txtemail.editText?.text.toString().equals("") or
                binding.txtpassword.editText?.text.toString().equals("")
                ){
                Toast.makeText(this@SignUpActivity, "Please fill the details", Toast.LENGTH_SHORT).show()
            }
            else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.txtemail.editText?.text.toString(),
                    binding.txtpassword.editText?.text.toString()
                ).addOnCompleteListener {
                    result->
                    if (result.isSuccessful){
                        user.name=binding.txtusername.editText?.text.toString()
                        user.password= binding.txtemail.editText?.text.toString()
                        user.email= binding.txtpassword.editText?.text.toString()

                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                               startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))

                                finish()

                            }

                    }
                    else{
                        Toast.makeText(this@SignUpActivity,result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.imgPlus.setOnClickListener {
            launcher.launch("image/*")//bas iamge ko leke aana hai
        }
        binding.txtlogin.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
            finish()
        }
    }
}