package kl.spnw.service.impl

import kl.spnw.entity.User
import kl.spnw.repository.UserRepository
import kl.spnw.service.UserFilterService
import org.springframework.stereotype.Service

@Service
class UserFilterServiceImpl (
        val userRepository: UserRepository
): UserFilterService {
    override fun filterUser(hasPhoto: Boolean?, inContact: Boolean?, favourite: Boolean?,
                            compatibilityScoreLow: Int?, compatibilityScoreHigh: Int?,
                            ageLow: Int?, ageHigh: Int?,
                            heightLow: Int?, heightHigh: Int?,
                            distanceWithinKM: Int?): MutableList<User> {
        return userRepository.findAll()
    }
}