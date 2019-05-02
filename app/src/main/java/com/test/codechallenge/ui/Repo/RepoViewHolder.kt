package com.test.codechallenge.ui.Repo

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.codechallenge.R
import com.test.codechallenge.entity.Item
import com.test.codechallenge.utils.extension.load
import com.test.codechallenge.utils.extension.toK


class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val repoName: TextView = view.findViewById(R.id.tv_repoName)
    private val repoDescription: TextView = view.findViewById(R.id.tv_repoDesc)
    private val ownerName: TextView = view.findViewById(R.id.tv_ownerName)
    private val stars: TextView = view.findViewById(R.id.tv_stars)
    private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    private var item: Item? = null

    init {
        view.setOnClickListener {
            item?.html_url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(item: Item?) {
        this.item = item
        repoName.text = item?.name ?: "loading"
        repoDescription.text = item?.description
        ownerName.text = item?.owner?.login

        stars.toK(item?.stargazers_count)

        val avatarUrl = item?.owner?.avatar_url
        if (avatarUrl != null) {
            thumbnail.load(avatarUrl)
        }
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_item, parent, false)
            return RepoViewHolder(view)
        }


    }


}
