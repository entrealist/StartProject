package com.jeff.startproject.view.pager

import com.jeff.startproject.databinding.ActivityPagerBinding
import com.view.base.BaseActivity

/*
 * https://developer.android.com/guide/navigation/navigation-swipe-view-2
 * https://github.com/android/views-widgets-samples/tree/master/ViewPager2
 */

class PagerActivity : BaseActivity<ActivityPagerBinding>() {
    override fun getViewBinding(): ActivityPagerBinding = ActivityPagerBinding.inflate(layoutInflater)
}