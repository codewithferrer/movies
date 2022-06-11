package es.trespies.movies.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.trespies.movies.api.MovieService
import es.trespies.movies.db.MovieDao
import es.trespies.movies.db.MovieDb
import es.trespies.movies.services.ApiKeyInterceptor
import es.trespies.movies.services.Configuration
import es.trespies.movies.services.CoroutineAppExecutors
import es.trespies.movies.util.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideConfiguration(@ApplicationContext appContext: Context): Configuration {
        return Configuration(context = appContext)
    }

    @Singleton
    @Provides
    fun provideCoroutineAppExecutor(@ApplicationContext appContext: Context): CoroutineAppExecutors {
        return CoroutineAppExecutors()
    }

    @Singleton @Provides
    fun provideMovieService(@ApplicationContext appContext: Context): MovieService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        //Add interceptor for add logging
        httpClient.addInterceptor(logging)
        //Add interceptor for add api_key in each call
        httpClient.addInterceptor(ApiKeyInterceptor(appContext))

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .client(httpClient.build())
            .build()
            .create(MovieService::class.java)
    }

    @Singleton @Provides
    fun provideDb(@ApplicationContext appContext: Context): MovieDb {
        return Room
            .databaseBuilder(appContext, MovieDb::class.java, "movie.db")
            .enableMultiInstanceInvalidation()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton @Provides
    fun provideMovieDao(movieDb: MovieDb): MovieDao {
        return movieDb.movieDao()
    }

}