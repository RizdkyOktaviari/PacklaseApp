package com.bangik.packclese.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.bangik.packclese.R
import com.bangik.packclese.ui.profile.packclese.PrivacyPolicyFragment
import com.bangik.packclese.ui.profile.packclese.TermsConditionFragment

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val pageRequest = intent.getIntExtra("pages", 0)
        if (pageRequest == 1){
            val privacyPolicyFragment = PrivacyPolicyFragment()
            val fm:FragmentManager = supportFragmentManager
            fm.beginTransaction().add(R.id.mainLayout, privacyPolicyFragment).commit()
        }else if (pageRequest == 2){
            val termsConditionFragment = TermsConditionFragment()
            val fm:FragmentManager = supportFragmentManager
            fm.beginTransaction().add(R.id.mainLayout, termsConditionFragment).commit()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}