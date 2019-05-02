package com.test.codechallenge.entity


import java.io.Serializable

/**
 * Class to hold repo responses from searchRepo API calls.
 */
data class RepoSearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
) : Serializable