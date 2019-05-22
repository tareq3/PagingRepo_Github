/*
 * Created by Tareq Islam on 5/22/19 11:56 AM
 *
 *  Last modified 5/22/19 11:56 AM
 */

package com.mti.pagingrepo.db;

import android.arch.paging.DataSource;
import android.util.Log;

import com.mti.pagingrepo.model.Repo;

import java.util.List;
import java.util.concurrent.Executor;

/***
 * Created by mtita on 22,May,2019.
 */
public class GithubLocalCache {

    //Constant used for Logs
    private static final String LOG_TAG = GithubLocalCache.class.getSimpleName();

    //Dao for Repo Entity
    private RepoDao repoDao;
    //Single Thread Executor for database operations
    private Executor ioExecutor;

    public GithubLocalCache(RepoDao repoDao, Executor ioExecutor) {
        this.repoDao = repoDao;
        this.ioExecutor = ioExecutor;
    }

    /**
     * Insert a list of repos in the database, on a background thread.
     */
    public void insert(List<Repo> repos, InsertCallback insertCallback) {
        ioExecutor.execute(()->{
            Log.d("tareq_test" , "insert: inserting "+ repos.size() );

            repoDao.insert(repos);

            insertCallback.insertFinished();
        });
    }


    /**
     * Request a DataSource.Factory<Integer, Repo> from the Dao, based on a repo name. If the name contains
     * multiple words separated by spaces, then we're emulating the GitHub API behavior and allow
     * any characters between the words.
     *
     * @param name repository name
     */
    public DataSource.Factory<Integer, Repo> reposByName(String name) {
        // appending '%' so we can allow other characters to be before and after the query string
        return repoDao.reposByName("%" + name.replace(' ', '%') + "%");
    }


    public interface InsertCallback {
        /**
         * Callback method invoked when the insert operation
         * completes.
         */
        void insertFinished();
    }
}
