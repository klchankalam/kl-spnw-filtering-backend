package kl.spnw.service.impl

import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.NumberPath
import kl.spnw.entity.QUser
import kl.spnw.entity.User
import kl.spnw.repository.UserRepository
import kl.spnw.service.UserFilterService
import kl.spnw.util.DistanceUtil
import org.springframework.stereotype.Service

@Service
class UserFilterServiceImpl(
        val userRepository: UserRepository
) : UserFilterService {
    override fun filterUser(hasPhoto: Boolean?, inContact: Boolean?, isFavourite: Boolean?,
                            compatibilityScoreLow: Int?, compatibilityScoreHigh: Int?,
                            ageLow: Int?, ageHigh: Int?,
                            heightLow: Int?, heightHigh: Int?,
                            distanceWithinKM: Int?,
                            userLatitude: Double?,
                            userLongitude: Double?): Iterable<User> {
        // TODO get upper/lower bound of latitude/longitude with distance criteria, limit result to a square for now


        // generate predicate
        val predicate = ExpressionUtils.allOf(
                getPhotoPredicate(hasPhoto),
                getContactPredicate(inContact),
                getFavouritePredicate(isFavourite),
                getCompatibilityPredicate(compatibilityScoreLow, compatibilityScoreHigh),
                getAgePredicate(ageLow, ageHigh),
                getHeightPredicate(heightLow, heightHigh)//,
                //getDistancePredicate()
        )

        // get result
        val result = if (predicate != null) userRepository.findAll(predicate) else userRepository.findAll()

        // filter distance again, upper/lower bound of lon/lat is a square, but distance bound is a circle
        return if (distanceWithinKM == null) result else
            result.filter {
                DistanceUtil.AreTwoCoordinateWithinDistance(
                        userLatitude!!, userLongitude!!, it.city.latitude, it.city.longitude, distanceWithinKM) }
    }

    private fun getCompatibilityPredicate(compatibilityScoreLow: Int?, compatibilityScoreHigh: Int?): Predicate? =
            ExpressionUtils.allOf(
                    if (compatibilityScoreLow != null) {
                        QUser.user.compatibilityScore.goe(compatibilityScoreLow.toDouble()/100.0)
                    } else null,
                    if (compatibilityScoreHigh != null) {
                        QUser.user.compatibilityScore.loe(compatibilityScoreHigh.toDouble()/100.0)
                    } else null
            )

    private fun getHeightPredicate(heightLow: Int?, heightHigh: Int?): Predicate? =
            getRangePredicate(QUser.user.heightInCm, heightLow, heightHigh)

    private fun getAgePredicate(ageLow: Int?, ageHigh: Int?): Predicate? =
            getRangePredicate(QUser.user.age, ageLow, ageHigh)

    private fun getRangePredicate(user: NumberPath<Int>, inputLow: Int?, inputHigh: Int?) =
            ExpressionUtils.allOf(
                    if (inputLow != null) {
                        user.goe(inputLow)
                    } else null,
                    if (inputHigh != null) {
                        user.loe(inputHigh)
                    } else null
            )

    private fun getFavouritePredicate(favourite: Boolean?): Predicate? =
            if (favourite != null) {
                QUser.user.favourite.eq(favourite)
            } else null

    private fun getContactPredicate(inContact: Boolean?): Predicate? =
            if (inContact != null) {
                with(QUser.user.contactsExchanged) {
                    if (inContact) gt(0) else eq(0)
                }
            } else null

    private fun getPhotoPredicate(hasPhoto: Boolean?): Predicate? =
            if (hasPhoto != null) {
                with(QUser.user.mainPhoto) {
                    if (hasPhoto) isNotNull else isNull
                }
            } else null
}