package com.yildiz.artbook.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yildiz.artbook.R
import com.yildiz.artbook.api.RetrofitAPI
import com.yildiz.artbook.repository.ArtRepository
import com.yildiz.artbook.repository.ArtRepositoryInterface
import com.yildiz.artbook.roomdb.ArtDAO
import com.yildiz.artbook.roomdb.ArtDatabase
import com.yildiz.artbook.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/** Bu modüle sınıfı sayesinde:
 * Repolarımızı yazabiliriz
 * repoları alıp viewModel'a bağlayabiliriz
 * İstediğimiz gibi bu işlemleri yapabiliriz.
 **/

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** böylece istediğimiz yerde @Inject diyerek bunu oraya dahil edebileceğiz **/
    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, ArtDatabase::class.java, "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDAO(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: ArtDAO, api: RetrofitAPI) =
        ArtRepository(dao, api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}