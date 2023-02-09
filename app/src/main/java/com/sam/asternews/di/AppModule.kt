package com.sam.asternews.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sam.asternews.core.data.remote.AsterNewsApi
import com.sam.asternews.feature_home.data.CategoriesNewsRepositoryImpl
import com.sam.asternews.feature_home.domain.CategoriesNewsRepository
import com.sam.asternews.core.data.local.SavedNewsRepositoryImpl
import com.sam.asternews.core.data.local.model.SavedNewsDatabase
import com.sam.asternews.core.domain.SavedNewsRepository
import com.sam.asternews.core.data.local.UserThemePreferenceRepositoryImpl
import com.sam.asternews.core.domain.UserThemePreferenceRepository
import com.sam.asternews.feature_top_headline.data.TopHeadlinesRepositoryImpl
import com.sam.asternews.feature_top_headline.domain.TopHeadlineRepository
import com.sam.asternews.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAsterNewsApo(): AsterNewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AsterNewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTopHeadlinesRepository(api: AsterNewsApi): TopHeadlineRepository {
        return TopHeadlinesRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideCategoryNewsRepository(api: AsterNewsApi): CategoriesNewsRepository {
        return CategoriesNewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSavedNewsDatabase(app: Application): SavedNewsDatabase {
        return Room.databaseBuilder(app, SavedNewsDatabase::class.java, "saved_news")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSavedNewsRepository(db: SavedNewsDatabase): SavedNewsRepository {
        return SavedNewsRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideUserThemePreferenceRepository(@ApplicationContext context: Context):
            UserThemePreferenceRepository {
        return UserThemePreferenceRepositoryImpl(context = context)
    }
}