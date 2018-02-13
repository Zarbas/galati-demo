package com.demo.galatidemo.injection;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.demo.galatidemo.R;
import com.demo.galatidemo.api.ApiService;
import com.demo.galatidemo.db.AppDB;
import com.demo.galatidemo.db.CommentDao;
import com.demo.galatidemo.db.PostDao;
import com.demo.galatidemo.db.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AppModule build the dependencies for the other classes
 */
@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton @Provides
    ApiService provideApiService(Application app) {
        return new Retrofit.Builder()
                .baseUrl(app.getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Singleton @Provides
    AppDB provideDb(Application app) {
        return Room.databaseBuilder(app, AppDB.class, app.getString(R.string.db_name)).fallbackToDestructiveMigration()
                .build();
    }

    @Singleton @Provides
    PostDao provideRepoDao(AppDB db) {
        return db.postDao();
    }

    @Singleton @Provides
    UserDao provideUserDao(AppDB db) {
        return db.userDao();
    }

    @Singleton @Provides
    CommentDao provideCommentDao(AppDB db) {
        return db.commentDao();
    }

}
