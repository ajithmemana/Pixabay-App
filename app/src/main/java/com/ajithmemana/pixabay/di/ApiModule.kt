package com.ajithmemana.pixabay.di

import com.ajithmemana.pixabay.data.api.PixabayImageService
import com.ajithmemana.pixabay.util.PIXABAY_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by ajithmemana
 */
@Module
@InstallIn(ActivityComponent::class, SingletonComponent::class)
object ApiModule {

    @Provides
    fun providesPixabayImageService(): PixabayImageService {
        return Retrofit.Builder()
            .baseUrl(PIXABAY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(PixabayImageService::class.java)
    }
}

