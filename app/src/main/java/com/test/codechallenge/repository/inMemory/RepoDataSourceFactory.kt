package com.test.codechallenge.repository.inMemory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.codechallenge.api.GithubService
import com.test.codechallenge.entity.Item
import java.util.concurrent.Executor


class RepoDataSourceFactory(
    private val repoAPI: GithubService,
    private val retryExecutor: Executor
) : DataSource.Factory<Int, Item>() {
    val sourceLiveData = MutableLiveData<PageKeyedRepoDataSource>()
    override fun create(): DataSource<Int, Item> {
        val source = PageKeyedRepoDataSource(repoAPI, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}