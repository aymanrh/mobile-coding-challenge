package com.test.codechallenge.repository.inMemory

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.test.codechallenge.api.GithubService
import com.test.codechallenge.entity.Item
import com.test.codechallenge.repository.Listing
import com.test.codechallenge.repository.RepoRepository
import java.util.concurrent.Executor


class InMemoryRepoRepository(
    private val repoAPI: GithubService,
    private val networkExecutor: Executor
) : RepoRepository {
    @MainThread
    override fun getRepoList(pageSize: Int): Listing<Item> {
        val sourceFactory = RepoDataSourceFactory(repoAPI, networkExecutor)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()
        val livePagedList = LivePagedListBuilder(sourceFactory, config).setFetchExecutor(networkExecutor).build()

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            }
        )
    }
}