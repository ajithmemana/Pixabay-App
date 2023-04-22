package com.ajithmemana.pixabay.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import com.google.gson.Gson

/**
 * Custom NavType class created to pass a parcelable image item from list screen to detail screen.
 *
 * Created by ajithmemana
 */
class PixabayImageItemNavType : NavType<PixabayImageItem>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): PixabayImageItem? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PixabayImageItem {
        return Gson().fromJson(value, PixabayImageItem::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: PixabayImageItem) {
        bundle.putParcelable(key, value)
    }

}

