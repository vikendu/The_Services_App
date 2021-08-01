package com.vikendu.theservicesapp.kotlin.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vikendu.theservicesapp.R
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.chatcontacts.ReceiversChatContactFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.feed.FeedFragment
import com.vikendu.theservicesapp.kotlin.activities.ui.buyers.search.SearchFragment

class BuyersHomeActivity : AppCompatActivity() {
    private var queryText: String? = null
    private var bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyers_home_kt)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val feedFragment = FeedFragment()
        val chatContactFragment = ReceiversChatContactFragment()
        val searchFragment = SearchFragment()

        setCurrentFragment(feedFragment)
        checkSearchIntent()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            onSearchRequested()
        }

        bottomNavigationView.setOnItemSelectedListener  {
            when (it.itemId) {
                R.id.idFeedFragment -> setCurrentFragment(feedFragment)
                R.id.idMessagesFragment -> setCurrentFragment(chatContactFragment)
                R.id.idSearchFragment -> addQueryData(searchFragment)
            }
            true
        }
    }

    private fun checkSearchIntent() {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                queryText = query
            }
        }
    }

    private fun addQueryData(fragment: Fragment) {
        checkSearchIntent()
        if(queryText != null) {
            bundle.putString("query", queryText)
            fragment.arguments = bundle
        } else {
            bundle.putString("query", null)
            fragment.arguments = bundle
        }
        queryText?.let { Log.d("QUERY!!!!", it) }
        setCurrentFragment(fragment)
    }

    private fun setCurrentFragment(fragment : Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()
            }
}