package kl.spnw.controller

import kl.spnw.entity.User
import kl.spnw.service.UserFilterService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class FilterController (
        val userFilterService: UserFilterService
) {
    @GetMapping("/user")
    fun getUser(@RequestParam hasPhoto: Boolean?, @RequestParam inContact: Boolean?, @RequestParam isFavourite: Boolean?,
                @RequestParam compatibilityScoreLow: Int?, @RequestParam compatibilityScoreHigh: Int?,
                @RequestParam ageLow: Int?, @RequestParam ageHigh: Int?,
                @RequestParam heightLow: Int?, @RequestParam heightHigh: Int?,
                @RequestParam userLatitude: Double?, @RequestParam userLongitude: Double?,
                @RequestParam distanceWithinKM: Int?): Iterable<User> {

        // validate input
        InputValidator.checkLowerAndUpperIntBoundary(compatibilityScoreLow, compatibilityScoreHigh,
                "Compatibility Score", 1, 99)
        InputValidator.checkLowerAndUpperIntBoundary(ageLow, ageHigh,"Age", 18, 95)
        InputValidator.checkLowerAndUpperIntBoundary(heightLow, heightHigh, "Height", 135, 210)
        InputValidator.checkAllOrNone(listOf("Latitude", "longitude", "distance"),
                userLatitude, userLongitude, distanceWithinKM)
        InputValidator.checkDoubleBoundary(userLatitude, "Latitude", -90.0, 90.0)
        InputValidator.checkDoubleBoundary(userLongitude, "Longitude", -180.0, 180.0)
        InputValidator.checkIntBoundary(distanceWithinKM, "Distance in KM", 30, 300)


        return userFilterService.filterUser(hasPhoto, inContact, isFavourite,
                compatibilityScoreLow, compatibilityScoreHigh,
                ageLow, ageHigh, heightLow, heightHigh, distanceWithinKM, userLatitude, userLongitude)
    }
}