/*
 * Created by Tareq Islam on 5/22/19 12:54 PM
 *
 *  Last modified 5/22/19 12:54 PM
 */

package com.mti.pagingrepo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mti.pagingrepo.api.GithubService;
import com.mti.pagingrepo.api.GithubServiceClient;
import com.mti.pagingrepo.data.GithubRepository;
import com.mti.pagingrepo.db.GithubLocalCache;
import com.mti.pagingrepo.db.RepoDatabase;
import com.mti.pagingrepo.ui.SearchRepositoriesViewModel;
import com.mti.pagingrepo.ui.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/***
 * Created by mtita on 22,May,2019.
 */
public class Injection {

    /**
     * Creates an instance of {@link GithubLocalCache} based on the database DAO.
     */
    @NonNull
    private static GithubLocalCache provideCache(Context context) {
        RepoDatabase repoDatabase = (RepoDatabase) RepoDatabase.getInstance(context);
        return new GithubLocalCache(repoDatabase.reposDao(), Executors.newSingleThreadExecutor());
    }

    /**
     * Creates an instance of {@link GithubRepository} based on the
     * {@link GithubService} and a
     * {@link GithubLocalCache}
     */
    @NonNull
    private static GithubRepository provideGithubRepository(Context context) {
        return new GithubRepository(GithubServiceClient.create(), provideCache(context));
    }


    /**
     * Provides the {@link ViewModelFactory} that is then used to get a reference to
     * {@link  SearchRepositoriesViewModel} objects.
     */
    @NonNull
    public static ViewModelFactory provideViewModelFactory(Context context) {
        return new ViewModelFactory(provideGithubRepository(context));
    }
}
