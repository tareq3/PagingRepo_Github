/*
 * Created by Tareq Islam on 5/22/19 11:53 AM
 *
 *  Last modified 5/22/19 11:53 AM
 */

package com.mti.pagingrepo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mti.pagingrepo.model.Repo;

/***
 * Created by mtita on 22,May,2019.
 */
@Database(entities = {Repo.class}, version = 1, exportSchema = false)
public abstract  class RepoDatabase extends RoomDatabase {
    private static volatile RoomDatabase INSTANCE;

    public static RoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RepoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }


    private static RepoDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                RepoDatabase.class,
                "Github.db"
        ).build();
    }

    //RepoDao is a DAO class annotated with @Dao
    public abstract RepoDao reposDao();
}
