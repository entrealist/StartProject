package com.jeff.startproject.view.encrypt

import android.util.Base64
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

object RSAUtil {
    fun encrypt(content: ByteArray, publicKey: String): ByteArray? {
        val keyFactory = KeyFactory.getInstance("RSA")
        val spec = X509EncodedKeySpec(android.util.Base64.decode(publicKey, android.util.Base64.DEFAULT))
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(spec))
        return cipher.doFinal(content)
    }

    /**
     * 私鑰解密
     * @param content 解密內容
     * @param privateKey 私鑰
     * @return string
     * @throws Exception
     */
    fun decrypt(content: ByteArray, privateKey: String): String? {
        return try {
            val keyFactory = KeyFactory.getInstance("RSA")
            val privateKeySpec = PKCS8EncodedKeySpec(Base64.decode(privateKey, Base64.DEFAULT))
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.DECRYPT_MODE, keyFactory.generatePrivate(privateKeySpec))
            String(cipher.doFinal(content))
        } catch (e: Exception) {
            null
        }
    }
}