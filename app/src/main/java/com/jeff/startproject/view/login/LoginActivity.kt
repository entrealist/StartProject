package com.jeff.startproject.view.login

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.jeff.startproject.R
import com.jeff.startproject.databinding.ActivityLoginBinding
import com.jeff.startproject.utils.repeatAnimation
import com.jeff.startproject.view.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel by viewModel<LoginViewModel>()

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.TRANSPARENT

        setupToolBar()
        setupObserve()
        setupClickListener()
    }

    private fun setupToolBar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> {
                    Toast.makeText(this, R.string.text_login, Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                else -> false
            }
        }

        binding.toolbar.navigationIcon?.repeatAnimation(binding.toolbar)
    }

    private fun setupObserve() {
        viewModel.mapLiveData.observe(this, Observer {
            binding.textWatchUser.text = "Mail: $it"
        })
        viewModel.switchMapLiveData.observe(this, Observer {
            binding.textWatchPassword.text = "Password: $it"
        })

        viewModel.editTextMailLiveData.bindingEditText = binding.editEmail
        viewModel.editTextPasswordLiveData.bindingEditText = binding.editPassword

        viewModel.loginUser.observe(this, Observer { loginUser ->
            when {
                loginUser.mail.isNullOrBlank() -> {
                    "請輸入信箱"
                }
                !loginUser.isMailValid -> {
                    "信箱格式不正確"
                }
                else -> {
                    ""
                }
            }.also {
                binding.layoutEmail.error = it
            }

            when {
                loginUser.password.isNullOrBlank() -> {
                    "請輸入密碼"
                }
                !loginUser.isPasswordValid -> {
                    "密碼長度不足"
                }
                else -> {
                    ""
                }
            }.also {
                binding.layoutPassword.error = it
            }
        })
    }

    private fun setupClickListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.updatedLoginUser()
        }

        binding.btnReset.setOnClickListener {
            viewModel.resetInput()
        }
    }
}