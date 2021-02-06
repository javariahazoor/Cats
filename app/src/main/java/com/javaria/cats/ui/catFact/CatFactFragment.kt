package com.javaria.cats.ui.catFact

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.javaria.cats.R
import com.javaria.cats.data.models.CatFactsResponse
import com.javaria.cats.data.network.CatApi
import com.javaria.cats.data.network.ResultResource
import com.javaria.cats.data.repositories.CatRepository
import com.javaria.cats.databinding.CatFactFragmentBinding
import com.javaria.cats.ui.base.BaseFragment
import com.javaria.cats.util.*
import kotlinx.coroutines.launch


class CatFactFragment : BaseFragment<CatFactViewModel, CatFactFragmentBinding, CatRepository>() {
    private var imageUrl = ""
    private var catFacts:List<CatFactsResponse> = ArrayList()
    private var activeFact:String=""
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)



        //rendering cat image and facts
        lifecycleScope.launch {
            viewModel.getCatFacts()
            viewModel.getCatImage()
        }
        viewModel.catImageResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultResource.Success -> {
                    binding.catImage.loadImage(it.value[0].url)
                    imageUrl = it.value[0].url.toString()
                    binding.progressBar.visibility=View.GONE
                }
                is ResultResource.Loading -> {
                    binding.progressBar.visibility=View.VISIBLE
                }
                is ResultResource.Failure -> handleApiError(it)
            }
        })
        viewModel.catFactsResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultResource.Success -> {
                    catFacts=it.value
                    getRandomFact()
                }
                is ResultResource.Failure -> handleApiError(it)
            }
        })
        binding.refresh.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getCatImage()
                if(catFacts.isEmpty())
                    viewModel.getCatFacts()
                else
                    getRandomFact()
            }
        }
        binding.downloadBtn.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                askPermissions(requireActivity(), requireContext(), imageUrl)
            } else {
                downloadImage(imageUrl, requireContext(), requireActivity());
            }
        }
    }

    private fun getRandomFact() {
        activeFact=catFacts.random().text
        binding.fact.text = activeFact
    }

    override fun getViewModel(): Class<CatFactViewModel> = CatFactViewModel::class.java
    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): CatFactFragmentBinding = CatFactFragmentBinding.inflate(inflater, container, false)
    override fun getFragmentRepository(): CatRepository {
        val api = remoteDataSource.buildApi(CatApi::class.java)
        return CatRepository(api)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.share) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    activeFact
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
            true
        } else super.onOptionsItemSelected(item)
    }






}