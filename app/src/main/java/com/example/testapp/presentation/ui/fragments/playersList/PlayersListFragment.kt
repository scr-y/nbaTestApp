package com.example.testapp.presentation.ui.fragments.playersList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentMainBinding
import com.example.testapp.databinding.FragmentPlayersListBinding
import com.example.testapp.utils.isOnline

class PlayersListFragment : Fragment() {
    private val viewModel= PlayersListViewModel()
    private lateinit var binding: FragmentPlayersListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayersListBinding.inflate(inflater,container,false)
        getPlayersList()
        return binding.root
    }

    private fun getPlayersList(){
        if (isOnline(requireContext())){
            viewModel.getPlayersList().observe(viewLifecycleOwner){
                if (it == null || !it.isSuccessful){
                    Toast.makeText(activity, R.string.connection_error,Toast.LENGTH_SHORT).show()
                    return@observe
                }
                val response = it.body()
                if (!response?.data.isNullOrEmpty()){
                    val playersArray = arrayListOf<String>()
                    response?.data?.forEach { it1 ->
                        playersArray.add(it1.first_name+" "+it1.last_name)
                    }
                    binding.adapter=ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,playersArray)
                    binding.playerListRv.setOnItemClickListener{ _, _, position, _ ->
                        response?.data?.get(position)?.let { it1 ->
                            findNavController().navigate(PlayersListFragmentDirections.actionPlayersListFragmentToPlayerInfoFragment(it1.id))

                        }
                    }
                }
            }
        }
    }

}