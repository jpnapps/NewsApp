package com.jpndev.newsapiclient.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jpndev.newsapiclient.R
import com.jpndev.newsapiclient.data.model.Article
import com.jpndev.newsapiclient.databinding.RcvItemNewsBinding


class NewsAdapter():RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
/*
    private val tvList = ArrayList<Article>()

    fun setList(tvShows:List<Article>){
         tvList.clear()
         tvList.addAll(tvShows)
    }
*/

    private val callback =object:DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {

            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = RcvItemNewsBinding.inflate( LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
      //  return tvList.size

        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item= differ.currentList.get(position)
       holder.bind(item)
    }




inner class MyViewHolder(val binding: RcvItemNewsBinding):
RecyclerView.ViewHolder(binding.root){

   fun bind(item:Article){
        binding.text1Ctxv.text = item.title
        binding.text2Ctxv.text = item.description
        //val posterURL = "https://image.tmdb.org/t/p/w500"+tvShow.posterPath
        Glide.with(binding.imgDimv.context)
            .load(item.urlToImage)
            .into(binding.imgDimv)

       binding.root.setOnClickListener{OnItemClickListener?.let{
                      it(item)
       }}

   }

}

    private var OnItemClickListener:((Article)->Unit)?=null

    fun setOnItemClickListner(listner:(Article)->Unit){

        OnItemClickListener=listner
    }

}