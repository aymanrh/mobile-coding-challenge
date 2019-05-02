package com.test.codechallenge.entity

data class Item(
    val created_at: String,
    val default_branch: String,
    val description: String,
    val fork: Boolean,
    val forks_count: Int,
    val full_name: String,
    val homepage: String,
    val html_url: String,
    val id: Int,
    val language: String,
    val master_branch: String,
    val name: String,
    val node_id: String,
    val open_issues_count: Int,
    val owner: Owner,
    val `private`: Boolean,
    val pushed_at: String,
    val score: Double,
    val size: Int,
    val stargazers_count: Int,
    val updated_at: String,
    val url: String,
    val watchers_count: Int
)