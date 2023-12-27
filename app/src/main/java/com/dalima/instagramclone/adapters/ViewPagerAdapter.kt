package com.dalima.instagramclone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPagerAdapter (fm:FragmentManager): FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val fragmentList= mutableListOf<Fragment>()
   val titleLIst= mutableListOf<String>()
    override fun getCount(): Int {
    return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
       return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleLIst.get(position)
    }
    fun addFragments(fragment: Fragment,title:String){
        fragmentList.add(fragment)
        titleLIst.add(title)
    }
}