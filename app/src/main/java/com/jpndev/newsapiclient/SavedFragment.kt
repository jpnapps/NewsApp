package com.jpndev.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jpndev.newsapiclient.data.util.Resource
import com.jpndev.newsapiclient.databinding.FragmentNewsBinding
import com.jpndev.newsapiclient.databinding.FragmentSavedBinding
import com.jpndev.newsapiclient.presentation.NewsAdapter
import com.jpndev.newsapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedFragment : Fragment() {
    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentSavedBinding

    lateinit var newsadapter: NewsAdapter
    var country:String="US"
    var page :Int=1
    var pages:Int=0
    var isLoading =false
    var isScrolling=false
    var isLastPage=false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSavedBinding.bind(view)
        viewModel=(activity as MainActivity).viewModel
        newsadapter=(activity as MainActivity).newsadapter
        newsadapter.setOnItemClickListner {
            val bundle=Bundle().apply {
                putSerializable("selected_item",it)
            }
            findNavController().navigate(R.id.action_savedFragment_to_infoFragment,bundle)
        }
        initRcv()
        viewNewsList()

        //setSearchView()

    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getSearchedNews(country,p0.toString(),page)
                viewSearchNewsList()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.getSearchedNews(country,p0.toString(),1)
                    viewSearchNewsList()
                }

                return false
            }

        })
        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                initRcv()
                viewNewsList()
                return false
            }

        })
    }

    private fun viewSearchNewsList() {

        viewModel.searchedNews.observe(viewLifecycleOwner,{response->
            when(response)
            {
                is Resource.Loading->{
                    showProgressBar()
                }
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {
                        newsadapter.differ.submitList(it.articles.toList())
                        if(it.totalResults%20==0)
                        {
                            pages=it.totalResults/20
                        }
                        else
                        {
                            pages=it.totalResults/20+1
                        }
                        isLastPage=pages==page
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let{
                        Toast.makeText(activity,"Error Search: "+it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    private fun viewNewsList() {
       // viewModel.getNewsHeadLines(country,page)
        viewModel.getSavedNews().observe(viewLifecycleOwner,{
            newsadapter.differ.submitList(it)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsadapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                view?.let {
                    Snackbar.make(it,"Deleted Successfully", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction("Undo"){
                                viewModel.saveArticle(article)
                            }
                            show()
                        }
                }

            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rcv)
        }
    }


    private fun initRcv() {
        //  newsadapter= NewsAdapter()
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter=newsadapter
            addOnScrollListener(this@SavedFragment.onScrollListner)
        }

    }

    private fun hideProgressBar() {
        isLoading=false
        binding.progressBar.visibility=View.GONE
    }

    private fun showProgressBar() {
        isLoading=true
        binding.progressBar.visibility=View.VISIBLE
    }

    private val onScrollListner = object : RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            {
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lm=binding.rcv.layoutManager as LinearLayoutManager
            val size_current_list=   lm.itemCount
            val visible_items=lm.childCount
            val toppos= lm.findFirstVisibleItemPosition()
            val hasReachedtoEnd=toppos+visible_items>=size_current_list

            if(!isLastPage && hasReachedtoEnd && isScrolling && !isLoading)
            {
                page++
                viewModel.getNewsHeadLines(country,page)
                isScrolling=false
            }
        }

    }
}