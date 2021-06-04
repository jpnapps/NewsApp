package com.jpndev.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.jpndev.newsapiclient.databinding.FragmentInfoBinding
import com.jpndev.newsapiclient.databinding.FragmentNewsBinding
import com.jpndev.newsapiclient.presentation.viewmodel.NewsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {
    lateinit var binding: FragmentInfoBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentInfoBinding.bind(view)
        viewModel=(activity as MainActivity).viewModel

        val args:InfoFragmentArgs by navArgs()
        val item= args.selectedItem
        binding.webView.apply {
            webViewClient= WebViewClient()
            if(item.url!=null)
              loadUrl(item.url!!)
        }
        binding.fabSave.setOnClickListener{
            viewModel.saveArticle(item)
            Snackbar.make(view,"News saved successfully ",Snackbar.LENGTH_LONG).show()
        }
/*var webviewclient = WebViewClient()
            loadurl()*/
    }

}