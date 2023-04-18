package com.ajithmemana.pixabay.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ajithmemana.pixabay.data.database.PixabayAppDatabase
import com.ajithmemana.pixabay.data.database.entity.PixabayImageItem
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Created by ajithmemana
 */
class PixabayImagesDaoTest {
    private lateinit var database: PixabayAppDatabase
    private lateinit var pixabayImagesDao: PixabayImagesDao

    private val pixabayImageItem1 =
        PixabayImageItem(1, "www.google.com", "oreo, cupcake, marshmallow", "Ajit", 2, 4, 100)
    private val pixabayImageItem2 =
        PixabayImageItem(1, "www.google.com", "oreo, cupcake, marshmallow", "Ajit", 2, 4, 100)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PixabayAppDatabase::class.java).build()
        pixabayImagesDao = database.pixabayImagesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadImagesTest() = runBlocking {
        pixabayImagesDao.insert(pixabayImageItem1)
        val imagesRead = pixabayImagesDao.getAllImages().take(1).toList()
        Assert.assertEquals(imagesRead.size, 1)
        Assert.assertEquals(imagesRead[0].id, pixabayImageItem1.id)
        Assert.assertEquals(imagesRead[0].user, pixabayImageItem1.user)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllTest() = runBlocking {

        // Insert
        pixabayImagesDao.insertAll(pixabayImageItem1, pixabayImageItem2)
        // Delete
        pixabayImagesDao.deleteAll()
        val imagesRead = pixabayImagesDao.getAllImages().take(1).toList()
        Assert.assertEquals(imagesRead.size, 0)

    }
}

