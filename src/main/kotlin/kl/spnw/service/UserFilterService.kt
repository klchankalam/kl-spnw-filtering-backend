package kl.spnw.service

import kl.spnw.entity.User

interface UserFilterService {
    fun filterUser(hasPhoto: Boolean?, inContact: Boolean?, isFavourite: Boolean?,
                   compatibilityScoreLow: Int?, compatibilityScoreHigh: Int?,
                   ageLow: Int?, ageHigh: Int?,
                   heightLow: Int?, heightHigh: Int?,
                   distanceWithinKM: Int?): Iterable<User>
}