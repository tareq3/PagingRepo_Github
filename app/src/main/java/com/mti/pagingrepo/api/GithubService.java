/*
 * Created by Tareq Islam on 5/22/19 11:36 AM
 *
 *  Last modified 5/22/19 11:36 AM
 */

package com.mti.pagingrepo.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/***
 * Created by mtita on 22,May,2019.
 */
public interface GithubService {
    /**
     * Get repos ordered by stars.
     */
    @GET("search/repositories?sort=stars")
    Call<RepoSearchResponse> searchRepos(@Query("q") String query,
                                         @Query("page") int page,
                                         @Query("per_page") int itemsPerPage);
}
