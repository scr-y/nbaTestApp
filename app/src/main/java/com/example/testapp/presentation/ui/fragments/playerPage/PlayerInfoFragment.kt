package com.example.testapp.presentation.ui.fragments.playerPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.testapp.App
import com.example.testapp.R
import com.example.testapp.data.database.FavoritesPlayers
import com.example.testapp.data.repository.PlayerInfoRepository
import com.example.testapp.databinding.FragmentPlayerInfoBinding
import com.example.testapp.databinding.FragmentPlayersListBinding
import com.example.testapp.presentation.ui.fragments.playersList.PlayersListViewModel
import com.example.testapp.utils.isOnline

class PlayerInfoFragment : Fragment() {

    private val viewModel by lazy {
        PlayerInfoViewModel(PlayerInfoRepository((requireActivity().application as App).favDao))
    }
    private val args: PlayerInfoFragmentArgs by navArgs()
    private lateinit var binding: FragmentPlayerInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)
        binding.handler = this
        binding.buttonStatus = true
        getPlayerInfo()
        return binding.root
    }

    private fun getPlayerInfo() {
        if (isOnline(requireContext())) {
            viewModel.getPlayerInfo(args.id).observe(viewLifecycleOwner) {
                if (it == null || !it.isSuccessful) {
                    Toast.makeText(activity, R.string.connection_error, Toast.LENGTH_SHORT).show()
                    return@observe
                }
                val response = it.body()
                binding.info = response
                response?.let {info->
                    checkIfFavorites(info.id)
                }
            }
        }
    }

    fun addFavoritesPlayer(id: Int, name: String) {
        val elem = FavoritesPlayers(id,name)
        viewModel.insert(elem)
        binding.buttonStatus = false
    }

    fun checkIfFavorites(id: Int){
        viewModel.checkIfFavorites(id).observe(viewLifecycleOwner) { player ->
            if (player != null)
                binding.buttonStatus = false
        }

    }

}