package com.example.testapp.presentation.ui.fragments.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.database.FavoritesPlayers
import com.example.testapp.databinding.ItemFavoritesPlayerBinding

class FavoritesPlayersListAdapter(private val item: List<FavoritesPlayers>,
                                  private val itemSelectedListener: FavoritesPlayersSelectedListener):
    RecyclerView.Adapter<FavoritesPlayersListAdapter.ViewHolder>() {
    class ViewHolder(val view: ItemFavoritesPlayerBinding) : RecyclerView.ViewHolder(view.root)

    interface FavoritesPlayersSelectedListener {
        fun onDeleteClick(index: Int, playerId: FavoritesPlayers)
        fun onItemClick(index: Int, playerId: Int)

    }
    override fun getItemCount() = item.size
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemFavoritesPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = item[position]

        holder.view.info = current
        holder.view.deleteFavPlayer.setOnClickListener {
            itemSelectedListener.onDeleteClick(position,current)
        }
        holder.view.playerName.setOnClickListener {
            itemSelectedListener.onItemClick(position,current.id)
        }
    }

}