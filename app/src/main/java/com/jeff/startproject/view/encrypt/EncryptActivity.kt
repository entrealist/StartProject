package com.jeff.startproject.view.encrypt

import android.os.Bundle
import android.util.Base64
import com.jeff.startproject.databinding.ActivityEncryptBinding
import com.view.base.BaseActivity

class EncryptActivity : BaseActivity<ActivityEncryptBinding>() {
    companion object {
        const val PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHQc7lyMLTALLmgYBmK8V39wzBCgdY77GR5jKQmR5JdYjK79ZLVmfeABwNEMavxkNBhzn+oWCYKgki8m4ADX0XQY8QuqY/Q21m1jVH6KXb8VISds9RNQQzXWWTofSwFRMhfdrB9Bk4VksUMpFT/kn1AQhFWqFEawth2EhGZOektwIDAQAB"
        const val PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIdBzuXIwtMAsuaBgGYrxXf3DMEKB1jvsZHmMpCZHkl1iMrv1ktWZ94AHA0Qxq/GQ0GHOf6hYJgqCSLybgANfRdBjxC6pj9DbWbWNUfopdvxUhJ2z1E1BDNdZZOh9LAVEyF92sH0GThWSxQykVP+SfUBCEVaoURrC2HYSEZk56S3AgMBAAECgYBzO1OjXKjuzxebXhUf9oajr+xDweGEmaD0pePKYUj2WJYUHsS5JoITFpDPaM19DzJZb3WvQ5lhyd5C0bt5fARnNOL65U5GHjMwZCYrgY7fnDE5d//r6Eb6QNPvpiK8RJ+R8bcD8OI2lN/BbM55bWGmwCJv1/YWYz4vkyYOZ0+lsQJBAPRWnP5/X3TG3i+Q5m8xYA0Oxn0xslXB64F5W1aATRzXirdzhTgeEFbBWhT49XpMA1xncvrXs2PwE8cjwHaNYiMCQQCNtmmqOm1rALychmV0mjHpfGjKd1YK8OA2BHtMxfrRvNjk72LrNxpuQ8Ib+1Jwdsk9qVvzULaEmJUQgKEMvepdAkActhTKnwMDgN7Y7gj15fJodmUCjxVqmFfpJe6Csp7dFcLaHbv4xSecWioQrtSBo279q7ZKHZCZ3LsmOmBCTgjLAkBhBGf0nYl5PwjhU/UzTbkr8vs+2VIzrVKiSJEtL0EWw+XtXaHoDFJw+Lx0MavvyLLfHwoPWsuJnXg30wfu1DoVAkBlDUUVnjMnHoi8smEiL6yqp+bDJJ4tHnlInNhiDPT19uBpmLa1CGSNr4joxjmccmdeprtSa0tXRBa8s7WVZxVw"
    }

    override fun getViewBinding(): ActivityEncryptBinding {
        return ActivityEncryptBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSend.setOnClickListener {
            var inputString = binding.edit.text.toString()
            val bytes = RSAUtil.encrypt(inputString.toByteArray(), PUBLIC_KEY)!!
            val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
            val decrypt = RSAUtil.decrypt(bytes, PRIVATE_KEY)

            binding.tvBase64.text = base64
            binding.tvDecrypt.text = decrypt
        }
    }
}