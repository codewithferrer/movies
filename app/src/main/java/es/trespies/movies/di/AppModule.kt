package es.trespies.movies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.trespies.movies.services.CoroutineAppExecutors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoroutineAppExecutor(@ApplicationContext appContext: Context): CoroutineAppExecutors {
        return CoroutineAppExecutors()
    }

}