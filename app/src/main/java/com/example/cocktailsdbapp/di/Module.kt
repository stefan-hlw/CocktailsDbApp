package com.example.cocktailsdbapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.cocktailsdbapp.database.AppDatabase
import com.example.cocktailsdbapp.database.CocktailDao
import com.example.cocktailsdbapp.network.CocktailApiService
import com.example.cocktailsdbapp.repository.CocktailsRepo
import com.example.cocktailsdbapp.repository.CocktailsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideCocktailApiService(): CocktailApiService {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CocktailApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCocktailsRepository(cocktailsRepoImpl: CocktailsRepoImpl): CocktailsRepo {
        return cocktailsRepoImpl
    }

    @Provides
    @Singleton
    fun provideCocktailsRepositoryImpl(cocktailApiService: CocktailApiService, cocktailDao: CocktailDao): CocktailsRepoImpl {
        return CocktailsRepoImpl(cocktailApiService, cocktailDao)
    }

}

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): CocktailDao {
        return appDatabase.cocktailDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RssReader"
        ).build()
    }
}