package com.bangik.packclese.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bangik.packclese.ui.profile.account.ProfileAccountFragment
import com.bangik.packclese.ui.profile.packclese.ProfilePackcleseFragment

class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Account"
            1 -> "Packclese"
            else -> ""
        }
    }

    override fun getItem(position: Int): Fragment {
        val fragment : Fragment
        return when(position){
            0 -> {
                fragment = ProfileAccountFragment()
                return fragment
            }
            1 -> {
                fragment = ProfilePackcleseFragment()
                return fragment
            }
            else -> {
                fragment = ProfileAccountFragment()
                return fragment
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }
}