package hu.ait.tododemo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.ait.tododemo.data.AppDatabase
import hu.ait.tododemo.data.TodoDAO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideTodoDao(appDatabase: AppDatabase): TodoDAO {
        return appDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }
}