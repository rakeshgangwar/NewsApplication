package com.rakeshgangwar.newsapplication.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rakeshgangwar.newsapplication.R
import com.rakeshgangwar.newsapplication.di.Injectable
import com.rakeshgangwar.newsapplication.models.Article
import com.rakeshgangwar.newsapplication.network.common.Status
import com.rakeshgangwar.newsapplication.ui.adapter.NewsListPagerAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.news_list_fragment.*
import javax.inject.Inject

class NewsListFragment : DaggerFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter: NewsListPagerAdapter

    private lateinit var viewModel: NewsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel::class.java)

        val localFragmentManager = activity?.supportFragmentManager
        val customTabsHelper = if (activity != null && localFragmentManager != null)
            CustomTabsHelper(activity as Context, localFragmentManager)
        else
            null

        adapter = NewsListPagerAdapter(customTabsHelper)
        news_pager.adapter = adapter

        viewModel.getNews().observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS)
                setData(it.data)
            else if (it.status == Status.ERROR && it.data != null)
                setData(it.data)
        })
    }

    private fun setData(data: List<Article>?) {
        if (data != null) {
            adapter.addNewsItems(data)
        }
    }
}
