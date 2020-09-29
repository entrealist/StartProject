package com.jeff.startproject.view.preferences

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.jeff.startproject.databinding.ActivityPreferencesBinding
import com.view.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PreferencesActivity : BaseActivity<ActivityPreferencesBinding>() {

    private var dateStore = MyDateStore(this)

    override fun getViewBinding(): ActivityPreferencesBinding {
        return ActivityPreferencesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            dateStore.profileFlow
                .flowOn(Dispatchers.Main)
                .collect {
                    binding.edit.setText(it)
                }
        }

        binding.btnSend.setOnClickListener {
            lifecycleScope.launch {
                dateStore.updateContent(binding.edit.text.toString())
            }
        }
    }
}