package com.example.testapp.presentation.ui.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testapp.App
import com.example.testapp.data.database.FavoritesPlayers
import com.example.testapp.data.repository.FavoritesPlayersRepository
import com.example.testapp.databinding.FragmentFavoritesPlayersBinding

class FavoritesPlayersFragment : Fragment() {

    private val viewModel by lazy {
        FavoritesPlayersViewModel(FavoritesPlayersRepository((requireActivity().application as App).favDao))
    }
    private lateinit var binding: FragmentFavoritesPlayersBinding
    private var playersList = mutableListOf<FavoritesPlayers>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesPlayersBinding.inflate(inflater, container, false)
        binding.adapter = FavoritesPlayersListAdapter(playersList,
            object : FavoritesPlayersListAdapter.FavoritesPlayersSelectedListener {

                override fun onDeleteClick(index: Int, player: FavoritesPlayers) {
                    viewModel.delete(player)
                    playersList.removeAt(index)
                    binding.adapter?.notifyItemRemoved(index)
                    binding.adapter?.notifyItemRangeChanged(0,playersList.size)
                }

                override fun onItemClick(index: Int, playerId: Int) {
                    findNavController().navigate(
                        FavoritesPlayersFragmentDirections.actionFavoritesPlayersFragmentToPlayerInfoFragment(
                            playerId
                        )
                    )
                }
            })
        getPlayersList()
        return binding.root
    }

    private fun getPlayersList() {
        viewModel.allWords.observe(viewLifecycleOwner) { players ->
            // Update the cached copy of the words in the adapter.
            players.let {
                if (playersList != it) {
                    playersList.clear()
                    playersList.addAll(it)
                    binding.adapter?.notifyDataSetChanged()

                }
            }
        }

    }

}