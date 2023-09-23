package com.andreirookie.kinosearch.domain

import com.andreirookie.kinosearch.data.net.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFilmInfoUseCase @Inject constructor(
    private val networkRepo: NetworkRepository
) {
    private val maxDirectorsQuantity = 3
    private val maxActorsQuantity = 6

    suspend fun execute(filmId: Int): FilmInfo {
        return withContext(Dispatchers.Default) {
            loadFilmFullInfoById(filmId)
        }
    }

    private suspend fun loadFilmFullInfoById(filmId: Int): FilmInfo {

        val film = networkRepo.loadFilmById(filmId)
        val staffList = networkRepo.loadStaffByFilmId(filmId)

        val directorList = staffList.filterByProfessionKey(ProfessionKey.DIRECTOR)
        val actorList = staffList.filterByProfessionKey(ProfessionKey.ACTOR)

        val directorString = directorList.take(maxDirectorsQuantity).concatenateNames()
        val actorString = actorList.take(maxActorsQuantity).concatenateNames()
// todo clickable staff
        return FilmInfo(film, directorString, actorString)
    }

    private fun List<Staff>.filterByProfessionKey(key: ProfessionKey): List<Staff> {
        return this.filter { staff -> staff.professionKey == key.value }
    }

    private fun List<Staff>.concatenateNames(): String {
        val sb = StringBuilder()
        return if (this.size == 1) {
            this.first().name
        } else {
            this.forEach { staff ->
                if (staff.name.isNotBlank()) {
                    sb.append(staff.name + ", ")
                }
            }
            sb.trimEnd(',', ' ').toString()
        }
    }
}