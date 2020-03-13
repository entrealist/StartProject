package com.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<out B : ViewBinding> : Fragment() {

    private var mViewBinding: B? = null
    val binding: B get() = mViewBinding!!

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun fetchViewModel(): BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchViewModel()?.navigateDestination?.observe(this, Observer { item ->
            findNavController().also { navController ->
                when {
                    item.action == 0 -> navController.navigateUp() //.popBackStack()
                    item.bundle != null -> navController.navigate(item.action, item.bundle)
                    else -> navController.navigate(item.action)
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getViewBinding(inflater, container).run {
            mViewBinding = this
            root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mViewBinding = null
    }
}