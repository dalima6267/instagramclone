package com.dalima.instagramclone.post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dalima.instagramclone.HomeActivity
import com.dalima.instagramclone.models.Reel
import com.dalima.instagramclone.databinding.ActivityReelsBinding
import com.dalima.instagramclone.models.User
import com.dalima.instagramclone.utils.REEL
import com.dalima.instagramclone.utils.REEL_FOLDER
import com.dalima.instagramclone.utils.USER_NODE
import com.dalima.instagramclone.utils.uploadVideo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class ReelsActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl:String
   lateinit var progressDialog:ProgressDialog
    private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let {


            uploadVideo(uri, REEL_FOLDER,progressDialog){
                    url->
                if(url!=null){

                    videoUrl= url
                }

            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
progressDialog=ProgressDialog(this)
        binding.imgSelectreel.setOnClickListener{
            launcher.launch("video/*")
        }
        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user: User=it.toObject<User>()!!
                    val reel: Reel = Reel(videoUrl!!,binding.caption.editText?.text.toString(),user.image!!)

                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel).addOnSuccessListener{
                            startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
                            finish()
                        }

                    }
                }

        }
    }
}