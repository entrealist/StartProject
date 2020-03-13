package com.jeff.startproject.view.navigation

import com.jeff.startproject.databinding.ActivityNavigationBinding
import com.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
 * https://developer.android.com/guide/navigation
 * https://developer.android.com/guide/navigation/navigation-pass-data
 */

class NavigationActivity: BaseActivity<ActivityNavigationBinding>() {
    override fun getViewBinding():ActivityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)

    private val viewModel by viewModel<NavigationViewModel>()
}