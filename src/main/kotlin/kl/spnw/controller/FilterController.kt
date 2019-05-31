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
                @RequestParam distanceWithinKM: Int?): List<User> {
        return userFilterService.filterUser(hasPhoto, inContact, isFavourite,
                compatibilityScoreLow, compatibilityScoreHigh,
                ageLow, ageHigh, heightLow, heightHigh, distanceWithinKM)
    }
}