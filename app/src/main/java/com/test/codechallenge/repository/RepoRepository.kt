package com.test.codechallenge.repository

import com.test.codechallenge.entity.Item

interface RepoRepository {
    fun getRepoList(pageSize: Int): Listing<Item>
}