package com.ajithmemana.pixabay.di

import android.content.Context
import com.ajithmemana.pixabay.data.database.PixabayAppDatabase
import com.ajithmemana.pixabay.data.database.dao.PixabayImagesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by ajithmemana
 */
@Module
@InstallIn(ActivityComponent::class, SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providePixabayImagesDao(database: PixabayAppDatabase): PixabayImagesDao {
        return database.pixabayImagesDao()
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PixabayAppDatabase {
        return PixabayAppDatabase.getDatabase(context)
    }
}

