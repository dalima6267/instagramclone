package com.dalima.instagramclone.utils

import android.app.ProgressDialog
import android.net.Uri
import com.dalima.instagramclone.post.ReelsActivity
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri:Uri,folderName:String,callback:(String?)->Unit) {
    //firebase k storage me image ka url add kar dega
    //kis folder me data upload karna h iske lie parameter me foldername pass karege
    var imageUrl: String? = null// image ka url h
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            // agar image successful upload ho jaega to url nikal jaega
            it.storage.downloadUrl.addOnSuccessListener {

                imageUrl = it.toString()
                callback(imageUrl)
            }

        }
}


// ek image aaegi with foldername  ab photo ko upload karge uska url lega uska url variable imageUrl me dal dega
fun uploadVideo(
    uri:Uri,
    folderName:String,

    progressDialog: ProgressDialog,
    callback:(String?)->Unit){
    //firebase k storage me image ka url add kar dega
    //kis folder me data upload karna h iske lie parameter me foldername pass karege
    var imageUrl:String?=null// image ka url h
   progressDialog.setTitle("Uploading....")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri)
        .addOnSuccessListener {
            // agar image successful upload ho jaega to url nikal jaega
            it.storage.downloadUrl.addOnSuccessListener {

                imageUrl=it.toString()
                progressDialog.dismiss()
                callback(imageUrl)
            }

        }.addOnProgressListener {
            val uploadedvalue: Long=(it.bytesTransferred/it.totalByteCount)*100
            progressDialog.setMessage("Uploaded $uploadedvalue %")
        }
}