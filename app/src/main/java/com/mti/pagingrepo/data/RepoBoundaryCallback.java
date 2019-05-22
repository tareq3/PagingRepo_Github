/*
 * Created by Tareq Islam on 5/22/19 12:02 PM
 *
 *  Last modified 5/22/19 12:02 PM
 */

package com.mti.pagingrepo.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mti.pagingrepo.api.GithubService;
import com.mti.pagingrepo.api.GithubServiceClient;
import com.mti.pagingrepo.db.GithubLocalCache;
import com.mti.pagingrepo.model.Repo;

import java.util.List;

/***
 * Created by mtita on 22,May,2019.
 */
public class RepoBoundaryCallback extends PagedList.BoundaryCallback<Repo> implements GithubServiceClient.ApiCallback {

    //Constant used for logs
    private static final String LOG_TAG = RepoBoundaryCallback.class.getSimpleName();
    // Constant for the Number of items in a page to be requested from the Github API
    private static final int NETWORK_PAGE_SIZE = 50;
    private String query;
    private GithubService githubService;
    private GithubLocalCache localCache;
    // Keep the last requested page. When the request is successful, increment the page number.
    private int lastRequestedPage = 1;
    // Avoid triggering multiple requests in the same time
    private boolean isRequestInProgress = false;

    //LiveData of network error
    private MutableLiveData<String> networkError = new MutableLiveData<>();

    public RepoBoundaryCallback(String query, GithubService githubService, GithubLocalCache localCache) {
        this.query = query;
        this.githubService = githubService;
        this.localCache = localCache;
    }

    public MutableLiveData<String> getNetworkError() {
        return networkError;
    }



    /**
     * Method to request data from Github API for the given search query
     * and save the results.
     *
     * @param query The query to use for retrieving the repositories from API
     */
    private void requestAndSaveData(String query) {
        //Exiting if the request is in progress
        if(isRequestInProgress) return;

        //set to true as we are starting the network request
        isRequestInProgress = true;

        //CAlling the client API to retrieve the repos for the given query
        GithubServiceClient.searchRepos(githubService, query, lastRequestedPage, NETWORK_PAGE_SIZE, this);
    }

    /**
     * Called when zero items are returned from an initial load of the PagedList's data source.
     */
    @Override
    public void onZeroItemsLoaded() {
        Log.d("tareq_test", "onZeorItem loaded: Started");
        requestAndSaveData(query);

    }


    /**
     * Called when the item at the end of the PagedList has been loaded, and access has
     * occurred within {@link PagedList.Config#prefetchDistance} of it.
     * <p>
     * No more data will be appended to the PagedList after this item.
     *
     * @param itemAtEnd The first item of PagedList
     */
    @Override
    public void onItemAtEndLoaded(@NonNull Repo itemAtEnd) {
        Log.d("tareq_test", "onItemEndLoaded: Started");
        requestAndSaveData(query);

    }



    /**
     * Callback invoked when the Search Repo API Call
     * completed successfully
     *
     * @param items The List of Repos retrieved for the Search done
     */
    @Override
    public void onSuccess(List<Repo> items) {
        //Inserting records in the database thread
        localCache.insert(items, () -> {
            //Updating the last requested page number when the request was successful
            //and the results were inserted successfully
            lastRequestedPage++;
            //Marking the request progress as completed
            isRequestInProgress = false;
        });
    }

    /**
     * Callback invoked when the Search Repo API Call failed
     *
     * @param errorMessage The Error message captured for the API Call failed
     */
    @Override
    public void onError(String errorMessage) {
        //Update the Network error to be shown
        networkError.postValue(errorMessage);
        //Mark the request progress as completed
        isRequestInProgress = false;
    }
}
