package com.example.fedpos_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fedpos_frontend.adapters.MenuAdapter
import com.example.fedpos_frontend.adapters.MenuItems
import com.example.fedpos_frontend.adapters.ProductAdapter
import com.example.fedpos_frontend.databinding.FragmentHomeBinding
import com.example.fedpos_frontend.model.AddProductResponse
import com.example.fedpos_frontend.network.ApiService
import com.example.fedpos_frontend.network.NetworkClient
import retrofit2.Call


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var experienceAdapter: ProductAdapter
    private lateinit var experienceRecyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize menu RecyclerView
        menuRecyclerView = binding?.menuRecyclerView!!
        menuAdapter = MenuAdapter {
            when (it) {
                MenuItems.AddProduct -> {
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_FirstFragment_to_addProductFragment)
                }
            }
        }
        menuRecyclerView.adapter = menuAdapter
        menuRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        // Initialize product RecyclerView and adapter without click listener
        experienceAdapter = ProductAdapter()
//          NavHostFragment.findNavController(this)
//              .navigate(R.id.action_FirstFragment_to_addProductFragment)

        experienceRecyclerView = binding?.fundraiserRecyclerView!!
        experienceRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        experienceRecyclerView.adapter = experienceAdapter

        // Call the function to fetch products
        getProducts()
    }




//call get products api


    private fun getProducts() {
        val apiService = NetworkClient.retrofitInstance?.create(ApiService::class.java)
        val call: Call<List<AddProductResponse>>? = apiService?.getProducts()
        call?.enqueue(object : retrofit2.Callback<List<AddProductResponse>> {
            override fun onResponse(
                call: Call<List<AddProductResponse>>,
                response: retrofit2.Response<List<AddProductResponse>>
            ) {
                val products = response.body()
                if (products != null) {
                    experienceAdapter.updateItems(products)
                }
            }

            override fun onFailure(call: Call<List<AddProductResponse>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


// Make sure to initialize your adapter somewhere, for example in your fragment or activity
//


    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}
