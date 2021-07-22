package com.vikendu.theservicesapp.kotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.chatcontacts.ChatContactFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.feed.FeedFragment
//import com.vikendu.theservicesapp.fragments.FeedFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.settings.SettingsFragment

class BuyersHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyers_home_kt)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val feedFragment = FeedFragment()
        val chatContactFragment = ChatContactFragment()
        val settingsFragment = SettingsFragment()

        setCurrentFragment(feedFragment)

        bottomNavigationView.setOnItemSelectedListener  {
            when (it.itemId) {
                R.id.idFeedFragment -> setCurrentFragment(feedFragment)
                R.id.idMessagesFragment -> setCurrentFragment(chatContactFragment)
                R.id.idSettingsFragment -> setCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment : Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()
            }
}