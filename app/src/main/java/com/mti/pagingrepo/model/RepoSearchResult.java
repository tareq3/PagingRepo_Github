/*
 * Created by Tareq Islam on 5/22/19 12:18 PM
 *
 *  Last modified 5/22/19 12:18 PM
 */

package com.mti.pagingrepo.model;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.mti.pagingrepo.data.RepoBoundaryCallback;

/***
 * Created by mtita on 22,May,2019.
 */
public class RepoSearchResult {
    //LiveData for search Result
    private final LiveData<PagedList<Repo>> data;

    //LiveData for Network Errors
    private final LiveData<String> networkError;

    //LiveData for Network Errors
    private final LiveData<RepoBoundaryCallback.NETWORK_STATE> networkState;

    public RepoSearchResult(LiveData<PagedList<Repo>> data, LiveData<String> networkError, LiveData<RepoBoundaryCallback.NETWORK_STATE> networkState) {
        this.data = data;
        this.networkError = networkError;
        this.networkState = networkState;
    }

    public LiveData<PagedList<Repo>> getData() {
        return data;
    }

    public LiveData<String> getNetworkError() {
        return networkError;
    }

    public LiveData<RepoBoundaryCallback.NETWORK_STATE> getNetworkState() {
        return networkState;
    }
}
