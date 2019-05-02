package com.test.codechallenge.component


import com.test.codechallenge.di.module.NetworkModule
import com.test.codechallenge.ui.Repo.RepoViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified RepoViewModel.
     * @param repoViewModel RepoViewModel in which to inject the dependencies
     */
    fun inject(repoViewModel: RepoViewModel)


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}