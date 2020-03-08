package com.jeff.startproject.view.navigation

import com.jeff.startproject.databinding.ActivityNavigationBinding
import com.jeff.startproject.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class NavigationActivity: BaseActivity<ActivityNavigationBinding>() {
    override fun getViewBinding():ActivityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)

    private val viewModel by viewModel<NavigationViewModel>()
}