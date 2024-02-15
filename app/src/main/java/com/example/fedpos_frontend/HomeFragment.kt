package com.example.fedpos_frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fedpos_frontend.adapters.ProductAdapter
import com.example.fedpos_frontend.adapters.MenuAdapter
import com.example.fedpos_frontend.adapters.MenuItems
import com.example.fedpos_frontend.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var experienceAdapter: ProductAdapter
//    private lateinit var experienceRecyclerView: RecyclerView
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


        menuRecyclerView = binding?.menuRecyclerView!!
        menuAdapter = MenuAdapter {
            when (it) {
                MenuItems.AddProduct -> {
                    NavHostFragment.findNavController(this).navigate(R.id.action_FirstFragment_to_addProductFragment)
                }
            }
        }
        menuRecyclerView.adapter = menuAdapter
        menuRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

//        experienceRecyclerView = binding.fundraiserRecyclerView
        experienceAdapter = ProductAdapter {
            findNavController().navigate(R.id.action_FirstFragment_to_addProductFragment)
        }
//        experienceRecyclerView.adapter = experienceAdapter
//        experienceRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}
