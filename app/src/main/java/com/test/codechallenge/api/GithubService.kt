package com.test.codechallenge.api


import com.test.codechallenge.entity.RepoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Github API communication setup via Retrofit.
 */
interface GithubService {
    /**
     * Get repos ordered by stars.
     */
    @GET("search/repositories?q=created:>2017-10-22&sort=stars&order=desc")
    fun searchRepos(
        @Query("page") page: Int?,
        @Query("per_page") itemsPerPage: Int
    ): Call<RepoSearchResponse>
}