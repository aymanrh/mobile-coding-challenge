package com.test.codechallenge.base

import androidx.lifecycle.ViewModel
import com.test.codechallenge.component.DaggerViewModelInjector
import com.test.codechallenge.component.ViewModelInjector
import com.test.codechallenge.di.module.NetworkModule
import com.test.codechallenge.ui.Repo.RepoViewModel


abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is RepoViewModel -> injector.inject(this)
        }
    }
}