package com.dalima.instagramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dalima.instagramclone.R
import com.dalima.instagramclone.adapters.FollowAdapter
import com.dalima.instagramclone.adapters.PostAdapter
import com.dalima.instagramclone.databinding.FragmentHomeBinding
import com.dalima.instagramclone.models.Post
import com.dalima.instagramclone.models.User
import com.dalima.instagramclone.utils.FOLLOW
import com.dalima.instagramclone.utils.POST
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
  private lateinit var binding:FragmentHomeBinding
private var postList=ArrayList<Post>()
    private lateinit var adapter:PostAdapter
    private var folloList=ArrayList<User>()
    private lateinit var followAdapter: FollowAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentHomeBinding.inflate(inflater,container,false)
        adapter= PostAdapter(requireContext(),postList)
        binding.postRv.layoutManager=LinearLayoutManager(requireContext())
        binding.postRv.adapter=adapter
        binding.postRv.adapter=adapter
        binding.followRv.adapter=adapter
        followAdapter= FollowAdapter(requireContext(),folloList)
        binding.postRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        binding.followRv.adapter=followAdapter
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).get().addOnSuccessListener {

           var tempList=ArrayList<User>()
            folloList.clear()
            for (i in it.documents){
                var user:User=i.toObject<User>()!!
                tempList.add(user)
            }
            folloList.addAll(tempList)
            followAdapter.notifyDataSetChanged()
        }
         Firebase.firestore.collection(POST).get().addOnSuccessListener {
             var tempList=ArrayList<Post>()
             postList.clear()
             for (i in it.documents){
                 var post:Post=i.toObject<Post>()!!
                 tempList.add(post)
             }
             postList.addAll(tempList)
             adapter.notifyDataSetChanged()
         }
        return binding.root
    }

    companion object {

            }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}