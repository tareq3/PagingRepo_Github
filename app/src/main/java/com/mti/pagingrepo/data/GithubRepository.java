/*
 * Created by Tareq Islam on 5/22/19 12:01 PM
 *
 *  Last modified 5/22/19 12:01 PM
 */

package com.mti.pagingrepo.data;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.mti.pagingrepo.api.GithubService;
import com.mti.pagingrepo.db.GithubLocalCache;
import com.mti.pagingrepo.model.Repo;
import com.mti.pagingrepo.model.RepoSearchResult;

/***
 * Created by mtita on 22,May,2019.
 */
public class GithubRepository {
    //Constant used for logs
    private static final String LOG_TAG = GithubRepository.class.getSimpleName();
    //Constant for the number of items to be loaded at once from the DataSource by the PagedList
    private static final int DATABASE_PAGE_SIZE = 20;
    private GithubService githubService;
    private GithubLocalCache localCache;

    public GithubRepository(GithubService githubService, GithubLocalCache localCache) {
        this.githubService = githubService;
        this.localCache = localCache;
    }

    /**
     * Search repositories whose names match the query.
     */
    public RepoSearchResult search(String query) {
        Log.d("tareq_test", "search: NewQuery");

        //Get Data source factory from the local cache
        DataSource.Factory<Integer, Repo> reposByName = localCache.reposByName(query);

        //Construct the boundary callback
        RepoBoundaryCallback boundaryCallback = new RepoBoundaryCallback(query, githubService, localCache);

        LiveData<String> networkErrors = boundaryCallback.getNetworkError();

        // Set the Page size for the Paged list
        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(DATABASE_PAGE_SIZE)
                .build();

        // Get the Live Paged list
        LiveData<PagedList<Repo>> data = new LivePagedListBuilder<>(reposByName, pagedConfig)
                .setBoundaryCallback(boundaryCallback)
                .build();

        // Get the Search result with the network errors exposed by the boundary callback
        return new RepoSearchResult(data, networkErrors);
    }

}
