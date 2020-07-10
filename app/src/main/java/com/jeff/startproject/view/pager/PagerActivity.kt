package com.jeff.startproject.view.pager

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.jeff.startproject.databinding.ActivityPagerBinding
import com.view.base.BaseActivity
import kotlin.random.Random

/*
 * https://developer.android.com/guide/navigation/navigation-swipe-view-2
 * https://github.com/android/views-widgets-samples/tree/master/ViewPager2
 */

class PagerActivity : BaseActivity<ActivityPagerBinding>() {

    val viewModel: PagerViewModel by viewModels()

    override fun getViewBinding(): ActivityPagerBinding = ActivityPagerBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = MyPagerAdapter()

        binding.viewPager.adapter = adapter

        val list = mutableListOf<MyPagerItem>()
        repeat(100) {
            val item = MyPagerItem()
            item.title = "Index: $it"

            val r = Random.nextInt(255)
            val g = Random.nextInt(255)
            val b = Random.nextInt(255)
            item.color = Triple(r, g, b)

            list.add(item)
        }

        adapter.submitList(list)

        addMarginAndPadding(binding.viewPager)
    }

    /**
     * Margin: View彼此的距離
     * Padding: View與Parent的距離
     * Padding 大於 Margin 時才看到前或後的View
     */
    private fun addMarginAndPadding(viewPager: ViewPager2) {
        viewPager.setPageTransformer(MarginPageTransformer(30))

        val recyclerView = viewPager.getChildAt(0) as RecyclerView
        recyclerView.apply {
            // setting padding on inner RecyclerView puts overscroll effect in the right place
            // TODO: expose in later versions not to rely on getChildAt(0) which might break
            setPadding(50, 0, 50, 0)
            clipToPadding = false
        }
    }
}