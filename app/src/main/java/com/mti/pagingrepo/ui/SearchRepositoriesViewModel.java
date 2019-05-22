/*
 * Created by Tareq Islam on 5/22/19 12:32 PM
 *
 *  Last modified 5/22/19 12:32 PM
 */

package com.mti.pagingrepo.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;

import com.mti.pagingrepo.data.GithubRepository;
import com.mti.pagingrepo.data.RepoBoundaryCallback;
import com.mti.pagingrepo.model.Repo;
import com.mti.pagingrepo.model.RepoSearchResult;

/***
 * Created by mtita on 22,May,2019.
 *  * ViewModel for the {@link MainActivity} screen.
 *  * The ViewModel works with the {@link GithubRepository} to get the data.
 *  *
 */
public class SearchRepositoriesViewModel extends ViewModel {

    private GithubRepository githubRepository;

    //For quries
    private MutableLiveData<String> queryLiveData = new MutableLiveData<>();

    //Applying transformation to get RepoSearchResult for the given Search Query
    private LiveData<RepoSearchResult> repoReuslt = Transformations.map(queryLiveData,
            inputQuery -> githubRepository.search(inputQuery)
    );

    //Applying transformation to get Live PagedList<Repo> from the RepoSearchResult
    private LiveData<PagedList<Repo>> repos = Transformations.switchMap(repoReuslt,
            repoSearchResult -> repoSearchResult.getData()
    );

    //Applying transformation to get Live Network Errors from the RepoSearchResult
    private LiveData<String> networkErrors = Transformations.switchMap(repoReuslt,
            repoSearchResult -> repoSearchResult.getNetworkError()
    );

    //Applying transformation to get Live Network State from the RepoSearchResult
    private LiveData<RepoBoundaryCallback.NETWORK_STATE> networkStates = Transformations.switchMap(repoReuslt,
            repoSearchResult -> repoSearchResult.getNetworkState()
    );


    public SearchRepositoriesViewModel(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    public LiveData<PagedList<Repo>> getRepos() {
        return repos;
    }

    public LiveData<String> getNetworkErrors() {
        return networkErrors;
    }

    public LiveData<RepoBoundaryCallback.NETWORK_STATE> getNetworkStates() {
        return networkStates;
    }

    /**
     * Search a repository based on a query string.
     */
    void searchRepo(String queryString) {
        queryLiveData.postValue(queryString);

    }

    /**
     * Get the last query value.
     */
    @Nullable
    String lastQueryValue() {
        return queryLiveData.getValue();
    }
}
