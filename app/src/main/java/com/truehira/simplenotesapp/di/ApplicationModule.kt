package com.truehira.simplenotesapp.di

import android.content.Context
import androidx.room.Room
import com.truehira.simplenotesapp.data.SimpleNotesDatabase
import com.truehira.simplenotesapp.data.SimpleNotesRepositoryImpl
import com.truehira.simplenotesapp.domain.repository.SimpleNotesRepository
import com.truehira.simplenotesapp.utils.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): SimpleNotesDatabase {
        return Room.databaseBuilder(
            context,
            SimpleNotesDatabase::class.java,
            SimpleNotesDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: SimpleNotesDatabase): SimpleNotesRepository {
        return SimpleNotesRepositoryImpl(db.simpleNotesDao)
    }

    @Provides
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}