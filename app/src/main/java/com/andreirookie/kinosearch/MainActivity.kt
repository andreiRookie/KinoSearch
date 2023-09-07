package com.andreirookie.kinosearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreirookie.kinosearch.fragments.feed.FeedFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, FeedFragment())
            .addToBackStack(FeedFragment.TAG)
            .commit()
    }
}