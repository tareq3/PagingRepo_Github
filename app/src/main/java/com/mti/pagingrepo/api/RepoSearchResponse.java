/*
 * Created by Tareq Islam on 5/22/19 11:38 AM
 *
 *  Last modified 5/19/19 11:39 PM
 */

package com.mti.pagingrepo.api;

import com.google.gson.annotations.SerializedName;
import com.mti.pagingrepo.model.Repo;

import java.util.List;

/**
 * Class to hold repo responses from searchRepo API calls.
 *
 * @author Kaushik N Sanji
 */
public class RepoSearchResponse {
    @SerializedName("total_count")
    public int total;

    @SerializedName("items")
    public List<Repo> items;

    public int nextPage;
}
