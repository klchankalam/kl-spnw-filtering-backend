package kl.spnw.service

import kl.spnw.entity.User

interface UserFilterService {
    fun filterUser(hasPhoto: Boolean?, inContact: Boolean?, favourite: Boolean?,
                   compatibilityScoreLow: Int?, compatibilityScoreHigh: Int?,
                   ageLow: Int?, ageHigh: Int?,
                   heightLow: Int?, heightHigh: Int?,
                   distanceWithinKM: Int?): MutableList<User>
}