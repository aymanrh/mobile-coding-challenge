package com.test.codechallenge.ui.Repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.codechallenge.MainActivity
import com.test.codechallenge.R
import com.test.codechallenge.di.ViewModelFactory
import com.test.codechallenge.entity.Item
import kotlinx.android.synthetic.main.fragment_repo.*

class RepoFragment : Fragment() {

    companion object {
        const val KEY_LIST = "key_list"
    }

    private lateinit var viewModel: RepoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Repo"
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = activity as MainActivity

        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity = act))
            .get(RepoViewModel::class.java)

        initAdapter()
//        if (savedInstanceState == null || !savedInstanceState.getBoolean(KEY_LIST, false))
//            viewModel.showList()


    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(KEY_LIST, true)
        super.onSaveInstanceState(outState)
    }

    private fun initAdapter() {

        rv_repo.layoutManager = LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)

        val adapter = RepoAdapter {
            viewModel.retry()
        }
        rv_repo.adapter = adapter
        viewModel.repos.observe(this, Observer<PagedList<Item>> {
            adapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
    }

}