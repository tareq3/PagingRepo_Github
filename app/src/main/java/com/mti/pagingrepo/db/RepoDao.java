/*
 * Created by Tareq Islam on 5/22/19 11:44 AM
 *
 *  Last modified 5/22/19 11:44 AM
 */

package com.mti.pagingrepo.db;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mti.pagingrepo.model.Repo;

import java.util.List;

/***
 * Created by mtita on 22,May,2019.
 *
 /**
 * Room data access object for accessing the {@link Repo} table.
 *
 */
@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Repo> posts);


    // Do a similar query as the search API:
    // Look for repos that contain the query string in the name or in the description
    // and order those results descending, by the number of stars and then by name ascending
    @Query("SELECT * FROM repos where (name LIKE :queryString) OR (description LIKE " +
                        ":queryString) ORDER BY stars DESC, name ASC")
    DataSource.Factory<Integer, Repo> reposByName(String queryString);
}
