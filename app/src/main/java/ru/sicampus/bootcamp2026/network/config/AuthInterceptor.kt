package ru.sicampus.bootcamp2026.network.config

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import ru.sicampus.bootcamp2026.network.config.ApiConstants

class AuthInterceptor(context: Context) : Interceptor {
    private val sharedPreferences = context.getSharedPreferences("travo_prefs", Context.MODE_PRIVATE)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = sharedPreferences.getString(ApiConstants.TOKEN_PREF_KEY, null)

        val requestBuilder = originalRequest.newBuilder()

        token?.let {
            requestBuilder.addHeader("Authorization", ApiConstants.AUTH_PREFIX + it)
        }

        return chain.proceed(requestBuilder.build())
    }
}