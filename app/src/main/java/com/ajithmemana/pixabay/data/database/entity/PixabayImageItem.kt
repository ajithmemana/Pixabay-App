package com.ajithmemana.pixabay.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
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

