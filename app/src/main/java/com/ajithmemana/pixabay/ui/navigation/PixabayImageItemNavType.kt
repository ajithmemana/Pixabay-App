package com.ajithmemana.pixabay.ui.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.squareup.moshi.Moshi

/**
 * Custom NavType class created to pass a parcelable image item from list screen to detail screen.
 *
 * Created by ajithmemana
 */
class PixabayImageItemNavType : NavType<PixabayImageItem?>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): PixabayImageItem? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, PixabayImageItem::class.java) else bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PixabayImageItem? {
        return Moshi.Builder().build().adapter(PixabayImageItem::class.java).fromJson(value)
    }

    override fun put(bundle: Bundle, key: String, value: PixabayImageItem?) {
        bundle.putParcelable(key, value)
    }
}

