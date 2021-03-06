package com.yzy.baselibrary.http

import android.annotation.SuppressLint
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Description:
 * @author: yzy
 * @date: 2019/9/22 18:47
 */
object SSLManager {
    /**
     * 创建SSLSocket的工厂类
     *
     * @return 返回一个SSLSocket的工厂类
     */
    fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null
        try {
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), null)
            ssfFactory = sc.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ssfFactory
    }

    /**
     * description: X509证书信任管理器类
     *
     * @author: yzy
     * @date: 2019/9/22 18:47
     */
    class TrustAllCerts : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }

    val hostnameVerifier: HostnameVerifier get() = HostnameVerifier { _, _ -> true }
}
