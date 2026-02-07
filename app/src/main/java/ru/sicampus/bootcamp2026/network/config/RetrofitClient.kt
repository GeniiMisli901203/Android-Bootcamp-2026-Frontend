package ru.sicampus.bootcamp2026.network.config

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun getClient(context: Context): Retrofit {
        if (retrofit == null) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val gson = GsonBuilder()
                .registerTypeAdapter(UUID::class.java, UUIDTypeAdapter())
                .registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
                .registerTypeAdapter(LocalDate::class.java, LocalDateTypeAdapter())
                .registerTypeAdapter(BigDecimal::class.java, BigDecimalTypeAdapter())
                .create()

            retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }
}

class UUIDTypeAdapter : com.google.gson.JsonSerializer<UUID>, com.google.gson.JsonDeserializer<UUID> {
    override fun serialize(src: UUID, typeOfSrc: java.lang.reflect.Type, context: com.google.gson.JsonSerializationContext): com.google.gson.JsonElement {
        return com.google.gson.JsonPrimitive(src.toString())
    }

    override fun deserialize(json: com.google.gson.JsonElement, typeOfT: java.lang.reflect.Type, context: com.google.gson.JsonDeserializationContext): UUID {
        return UUID.fromString(json.asString)
    }
}

class InstantTypeAdapter : com.google.gson.JsonSerializer<Instant>, com.google.gson.JsonDeserializer<Instant> {
    override fun serialize(src: Instant, typeOfSrc: java.lang.reflect.Type, context: com.google.gson.JsonSerializationContext): com.google.gson.JsonElement {
        return com.google.gson.JsonPrimitive(src.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(json: com.google.gson.JsonElement, typeOfT: java.lang.reflect.Type, context: com.google.gson.JsonDeserializationContext): Instant {
        return Instant.parse(json.asString)
    }
}

class LocalDateTypeAdapter : com.google.gson.JsonSerializer<LocalDate>, com.google.gson.JsonDeserializer<LocalDate> {
    override fun serialize(src: LocalDate, typeOfSrc: java.lang.reflect.Type, context: com.google.gson.JsonSerializationContext): com.google.gson.JsonElement {
        return com.google.gson.JsonPrimitive(src.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(json: com.google.gson.JsonElement, typeOfT: java.lang.reflect.Type, context: com.google.gson.JsonDeserializationContext): LocalDate {
        return LocalDate.parse(json.asString)
    }
}

class BigDecimalTypeAdapter : com.google.gson.JsonSerializer<BigDecimal>, com.google.gson.JsonDeserializer<BigDecimal> {
    override fun serialize(src: BigDecimal, typeOfSrc: java.lang.reflect.Type, context: com.google.gson.JsonSerializationContext): com.google.gson.JsonElement {
        return com.google.gson.JsonPrimitive(src.toPlainString())
    }

    override fun deserialize(json: com.google.gson.JsonElement, typeOfT: java.lang.reflect.Type, context: com.google.gson.JsonDeserializationContext): BigDecimal {
        return BigDecimal(json.asString)
    }
}