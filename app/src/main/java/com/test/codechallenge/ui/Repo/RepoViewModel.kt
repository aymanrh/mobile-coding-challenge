package com.test.codechallenge.ui.Repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import com.test.codechallenge.api.GithubService
import com.test.codechallenge.base.BaseViewModel
import com.test.codechallenge.repository.inMemory.InMemoryRepoRepository
import java.util.concurrent.Executors
import javax.inject.Inject

class RepoViewModel : BaseViewModel() {
    @Inject
    lateinit var repoAPI: GithubService
    private val repository = InMemoryRepoRepository(
        repoAPI = repoAPI,
        networkExecutor = Executors.newFixedThreadPool(5)
    )
    private val isShowList = MutableLiveData<Boolean>()
    private val repoResult = map(isShowList) {
        repository.getRepoList(10)
    }
    val repos = switchMap(repoResult) { it.pagedList }!!
    val networkState = switchMap(repoResult) { it.networkState }!!

    init {
        showList()
    }

    fun showList(): Boolean {
        if (isShowList.value == null)
            isShowList.value = true
        return true
    }

    fun retry() {
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }

}