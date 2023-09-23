package com.andreirookie.kinosearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andreirookie.kinosearch.di.ActivityComponentHolder
import com.andreirookie.kinosearch.di.appComponent
import com.andreirookie.kinosearch.fragments.feed.FeedFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityComponentHolder.getComponent(appComponent).inject(this)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, FeedFragment())
            .addToBackStack(FeedFragment.TAG)
            .commit()
    }

    // todo tests
}