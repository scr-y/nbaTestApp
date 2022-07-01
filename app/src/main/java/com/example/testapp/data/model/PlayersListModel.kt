package com.example.testapp.data.model

data class PlayersListModel(
    val data: List<PlayerInfo>
) {
    data class PlayerInfo(
        val id: Int,
        val first_name: String,
        val height_feet: String,
        val height_inches: String,
        val last_name: String,
        val position: String,
        val team: TeamInfo,
        val weight_pounds: Int,
    )
    data class TeamInfo(
        val id: Int,
        val abbreviation: String,
        val city: String,
        val conference: String,
        val division: String,
        val full_name: String,
        val name: String,
    )
}