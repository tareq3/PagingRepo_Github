/*
 * Created by Tareq Islam on 5/22/19 11:26 AM
 *
 *  Last modified 5/19/19 11:39 PM
 */

package com.mti.pagingrepo.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

/**
 * Immutable model class for a Github repo that holds all the information about a repository.
 * Objects of this type are received from the Github API, therefore all the fields are annotated
 * with the serialized name.
 * <p>
 * This class also defines the Room repos table, where the repo {@link #id} is the primary key.
 *
 * @author Kaushik N Sanji
 */
@Entity(tableName = "repos")
public class Repo {
    @PrimaryKey
    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("full_name")
    public String fullName;

    @Nullable
    @SerializedName("description")
    public String description;

    @SerializedName("html_url")
    public String url;

    @SerializedName("stargazers_count")
    public int stars;

    @SerializedName("forks_count")
    public int forks;

    @Nullable
    @SerializedName("language")
    public String language;


}
