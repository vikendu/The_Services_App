package com.vikendu.theservicesapp.kotlin.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.chatcontacts.ReceiversChatContactFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.feed.FeedFragment
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

    override fun onNewIntent(intent: Intent?) {
        val newSearchFragment = SearchFragment()
        if (intent != null) {
            if (Intent.ACTION_SEARCH == intent.action) {
                intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                    val bundle = Bundle()
                    bundle.putString("query", query)
                    newSearchFragment.arguments = bundle
                }
            }
            setCurrentFragment(newSearchFragment)
        }
        super.onNewIntent(intent)
    }

    //TODO: move this function to a utility class
    private fun getCurrentFragment(): Fragment? {
        return this
            .supportFragmentManager
            .findFragmentById(R.id.flFragment)
    }

    private fun setCurrentFragment(fragment : Fragment) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()
            }

    }
}