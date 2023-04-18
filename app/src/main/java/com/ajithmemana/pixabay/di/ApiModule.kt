package com.ajithmemana.pixabay.di

import com.ajithmemana.pixabay.data.api.PixabayImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ajithmemana
 */
@Module
@InstallIn(ActivityComponent::class)
object ApiModule {

    private const val BASE_URL = "https://pixabay.com/"

    @Provides
    fun providesPixabayImageService(): PixabayImageService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PixabayImageService::class.java)
    }
}

