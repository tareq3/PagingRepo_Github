/*
 * Created by Tareq Islam on 5/22/19 12:44 PM
 *
 *  Last modified 5/22/19 12:44 PM
 */

package com.mti.pagingrepo.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mti.pagingrepo.data.GithubRepository;

import java.lang.reflect.InvocationTargetException;

/***
 * Created by mtita on 22,May,2019.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private GithubRepository githubRepository;

    public ViewModelFactory(GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (SearchRepositoriesViewModel.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(GithubRepository.class).newInstance(githubRepository);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class " + modelClass);
    }
}
