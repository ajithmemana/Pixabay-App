package com.ajithmemana.pixabay.di

import com.ajithmemana.pixabay.data.database.PixabayAppDatabase
import com.ajithmemana.pixabay.data.database.dao.PixabayImagesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by ajithmemana
 */
@Module
@InstallIn(ActivityComponent::class)
class DatabaseModule {
    @Provides
    fun providePixabayImagesDao(database: PixabayAppDatabase): PixabayImagesDao {
        return database.pixabayImagesDao()
    }
}

