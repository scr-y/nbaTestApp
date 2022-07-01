package com.example.testapp.presentation.ui.fragments.searchPlayer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentPlayersListBinding
import com.example.testapp.databinding.FragmentSearchPlayerBinding
import com.example.testapp.presentation.ui.fragments.playersList.PlayersListFragmentDirections
import com.example.testapp.presentation.ui.fragments.playersList.PlayersListViewModel
import com.example.testapp.utils.isOnline

class SearchPlayerFragment : Fragment() {

    private val viewModel = SearchPlayerViewModel()
    private lateinit var binding: FragmentSearchPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchPlayerBinding.inflate(inflater, container, false)
        binding.playerSearchEt.afterTextChangedDelayed {
            if (binding.playerSearchEt.hasFocus()) {
                if (it.isEmpty())
                    binding.playerSearchLv.adapter = null
                else
                    searchPlayer(it)

            }
        }
        return binding.root
    }

    private fun searchPlayer(name: String) {
        if (isOnline(requireContext())) {
            viewModel.getSearchedPlayersList(name).observe(viewLifecycleOwner) {
                if (it == null || !it.isSuccessful) {
                    Toast.makeText(activity, R.string.connection_error, Toast.LENGTH_SHORT).show()
                    return@observe
                }
                val response = it.body()
                if (!response?.data.isNullOrEmpty()) {
                    val playersArray = arrayListOf<String>()
                    response?.data?.forEach { it1 ->
                        playersArray.add(it1.first_name + " " + it1.last_name)
                    }
                    binding.playerSearchLv.adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        playersArray
                    )
                    binding.playerSearchLv.setOnItemClickListener{ _, _, position, _ ->
                        response?.data?.get(position)?.let { it1 ->
                            findNavController().navigate(SearchPlayerFragmentDirections.actionSearchPlayerFragmentToPlayerInfoFragment(it1.id))

                        }
                    }
                }
            }
        }
    }

}

private fun EditText.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        var timer: CountDownTimer? = null

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            timer?.cancel()
            timer = object : CountDownTimer(300, 1500) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    try {
                        afterTextChanged.invoke(editable.toString())
                    } catch (e: Throwable) {
                        Log.e("tag", e.localizedMessage)
                    }
                }
            }.start()
        }
    })
}