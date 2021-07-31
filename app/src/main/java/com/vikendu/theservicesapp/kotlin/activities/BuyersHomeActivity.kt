package com.vikendu.theservicesapp.kotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.databinding.ActivityBuyersHomeKtBinding
import com.vikendu.theservicesapp.databinding.FragmentSearchBinding
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.chatcontacts.ReceiversChatContactFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.feed.FeedFragment
//import com.vikendu.theservicesapp.fragments.FeedFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.search.SearchFragment

class BuyersHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyers_home_kt)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val feedFragment = FeedFragment()
        val chatContactFragment = ReceiversChatContactFragment()
        val searchFragment = SearchFragment()

        setCurrentFragment(feedFragment)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            setCurrentFragment(searchFragment)
            bottomNavigationView.selectedItemId = R.id.idSearchFragment
            onSearchRequested()
        }

        bottomNavigationView.setOnItemSelectedListener  {
            when (it.itemId) {
                R.id.idFeedFragment -> setCurrentFragment(feedFragment)
                R.id.idMessagesFragment -> setCurrentFragment(chatContactFragment)
                R.id.idSearchFragment -> setCurrentFragment(searchFragment)
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