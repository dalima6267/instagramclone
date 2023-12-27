package com.dalima.instagramclone.post


import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.result.contract.ActivityResultContracts


import androidx.appcompat.app.AppCompatActivity
import com.dalima.instagramclone.HomeActivity

import com.dalima.instagramclone.databinding.ActivityPost2Binding
import com.dalima.instagramclone.models.Post
import com.dalima.instagramclone.models.User
import com.dalima.instagramclone.utils.POST
import com.dalima.instagramclone.utils.POST_FOLDER
import com.dalima.instagramclone.utils.USER_NODE
import com.dalima.instagramclone.utils.USER_PROFILE_FOLDER
import com.dalima.instagramclone.utils.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class PostActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityPost2Binding.inflate(layoutInflater)
    }
    lateinit var user: User
    // for opening gallery use launcher register1
    var imageUrl:String?=null
    private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let {

            uploadImage(uri, POST_FOLDER){
                url->
                if(it!=null){

                    imageUrl=url
                }

            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
// for adding back button
        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }
        binding.imgSelect.setOnClickListener{
            launcher.launch("image/*")
        }
        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }
        binding.btnPost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user =it.toObject<User>()!!
                val post: Post= Post(
                    postUrl = imageUrl!!,
                    caption = binding.caption.editText?.text.toString(),
                    uid= Firebase.auth.currentUser!!.uid,
                    time=System.currentTimeMillis().toString()
                )


            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post)
                    .addOnSuccessListener {
                        startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                        finish()
                    }
            }
            }
        }
    }


}