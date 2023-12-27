package com.dalima.instagramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dalima.instagramclone.adapters.ReelAdapter
import com.dalima.instagramclone.databinding.FragmentReelBinding
import com.dalima.instagramclone.models.Reel
import com.dalima.instagramclone.utils.REEL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class ReelFragment : Fragment() {

private lateinit var binding: FragmentReelBinding
lateinit var adapter:ReelAdapter
var reelList=ArrayList<Reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        binding=FragmentReelBinding.inflate(inflater,container,false)
        adapter= ReelAdapter(requireContext(),reelList)
        binding.viewPager.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList=ArrayList<Reel>()
            reelList.clear()
            for (i in it.documents){
                var reel =i.toObject<Reel>()!!
                tempList.add(reel)
                reelList.reverse()
                adapter.notifyDataSetChanged()
            }
            reelList.addAll(tempList)
        }

        return binding.root
    }

    companion object {

}}