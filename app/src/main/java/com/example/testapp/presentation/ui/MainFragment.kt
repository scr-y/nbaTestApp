package com.example.testapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentMainBinding
import com.example.testapp.utils.setupWithNavControllers
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }


    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.navView

        val navGraphIds = listOf(
            R.navigation.home_graph,
            R.navigation.search_graph,
            R.navigation.favorites_graph
        )

        val controller = bottomNavigationView.setupWithNavControllers(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.fragment_main_nav_host_container,
            intent = requireActivity().intent
        )

        controller.observe(viewLifecycleOwner) { navController ->
            navigationOnApp(navController, bottomNavigationView)
        }
        currentNavController = controller
    }

    private fun navigationOnApp(navController: NavController, navView: BottomNavigationView) {
        binding.toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.setNavigationIcon(com.google.android.material.R.drawable.material_ic_keyboard_arrow_left_black_24dp)
            when (navController.currentDestination?.id) {
                R.id.searchPlayerFragment -> navView.visibility = View.VISIBLE
                R.id.playersListFragment -> navView.visibility = View.VISIBLE
                R.id.favoritesPlayersFragment -> navView.visibility = View.VISIBLE
                else -> navView.visibility = View.GONE
            }

            // Тут можно настроить toolbar на каждой странице по вкусу
            binding.toolbarText.text = when (destination.id) {
                R.id.playersListFragment -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.toolbar.navigationIcon = null
                    "Список игроков"
                }
                R.id.playerInfoFragment -> {
                    binding.toolbar.visibility = View.VISIBLE
                    "Подробная информация"
                }
                R.id.favoritesPlayersFragment -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.toolbar.navigationIcon = null
                    "Избранные игроки"
                }
                R.id.searchPlayerFragment -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.toolbar.navigationIcon = null
                    "Поиск игроков"
                }
                else -> {
                    binding.toolbar.visibility = View.GONE
                    ""
                }
            }

        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigationBar()
    }

}