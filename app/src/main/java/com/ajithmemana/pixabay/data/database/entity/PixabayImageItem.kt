package com.ajithmemana.pixabay.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Database entity to store Image data. This class contains a subset of elements present in
 * original response from cloud
 * Created by ajithmemana
 */
@Entity(tableName = "image_data")
@Parcelize
data class PixabayImageItem(
    @PrimaryKey val id: Long,
    val previewURL: String? = null,
    val largeImageURL: String? = null,
    val tags: String? = null,
    val user: String,
    val likes: Int? = null,
    val comments: Int? = null,
    val downloads: Int? = null,
) : Parcelable

