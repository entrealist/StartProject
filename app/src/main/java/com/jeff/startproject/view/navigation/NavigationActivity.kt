package com.jeff.startproject.view.navigation

import android.os.Bundle
import androidx.activity.viewModels
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ActivityNavigationBinding
import com.view.base.BaseActivity

/*
 * https://developer.android.com/guide/navigation
 * https://developer.android.com/guide/navigation/navigation-pass-data
 */

class NavigationActivity : BaseActivity<ActivityNavigationBinding>() {
    override fun getViewBinding(): ActivityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)

    private val viewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.nav_file)

        // Setup the bottom navigation view with a list of navigation graphs
        binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
    }
}