package com.vikendu.theservicesapp.kotlin.activities

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.kotlin.activities.ui.providers.adcreation.AdCreationFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.providers.approved.ApprovedFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.providers.localfeed.LocalFeedFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.providers.providerschat.ProvidersChatContactFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.providers.unapproved.UnapprovedFragment

class ProvidersHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providers_home_kt)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)

        val approvedFragment = ApprovedFragment()
        val unapprovedFragment = UnapprovedFragment()
        val adCreationFragment = AdCreationFragment()
        val providersChatFragment = ProvidersChatContactFragment()
        val localFeedFragment = LocalFeedFragment()

        setCurrentFragment(approvedFragment)

        bottomNavigationView.setOnItemSelectedListener  {
            when (it.itemId) {
                R.id.idApprovedFragment -> setCurrentFragment(approvedFragment)
                R.id.idUnapprovedFragment -> setCurrentFragment(unapprovedFragment)
                R.id.idAdCreationTool -> setCurrentFragment(adCreationFragment)
                R.id.idMessagesFragment -> setCurrentFragment(providersChatFragment)
                R.id.idLocalFeedFragment -> setCurrentFragment(localFeedFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment : Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.providerFragment, fragment)
            commit()
        }
}