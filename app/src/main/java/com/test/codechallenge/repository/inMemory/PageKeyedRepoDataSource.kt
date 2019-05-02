package com.test.codechallenge.repository.inMemory


import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource

import com.test.codechallenge.api.GithubService
import com.test.codechallenge.entity.Item
import com.test.codechallenge.entity.RepoSearchResponse
import com.test.codechallenge.repository.NetworkState
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class PageKeyedRepoDataSource(
    private val repoAPI: GithubService,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Int, Item>() {


    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }

    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Item>) {

        val numberOfItems = params.requestedLoadSize
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        val request = repoAPI.searchRepos(
            page = 1,
            itemsPerPage = numberOfItems
        )
        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val data = response.body()
            val items = data?.items ?: emptyList()
            retry = null

            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(items, null, 1)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        networkState.postValue(NetworkState.LOADING)
        repoAPI.searchRepos(
            page = params.key,
            itemsPerPage = page * numberOfItems
        ).enqueue(
            object : retrofit2.Callback<RepoSearchResponse> {
                override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                }

                override fun onResponse(
                    call: Call<RepoSearchResponse>,
                    response: Response<RepoSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        val items = data?.items ?: emptyList()
                        retry = null
                        callback.onResult(items, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(
                            NetworkState.error("error code: ${response.code()}")
                        )
                    }
                }
            }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

    }
}