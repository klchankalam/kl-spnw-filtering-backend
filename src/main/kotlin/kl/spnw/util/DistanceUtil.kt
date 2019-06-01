
package kl.spnw.util

open class DistanceUtil {
    companion object {
        @JvmStatic
        fun AreTwoCoordinateWithinDistance(startLat: Double, startLon: Double,
                                           endLat: Double, endLon: Double,
                                           distanceInKm: Int): Boolean {
            val dLat = Math.toRadians(endLat - startLat)
            val dLong = Math.toRadians(endLon - startLon)

            val a = haversin(dLat) + Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) * haversin(dLong)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

            return EARTH_RADIUS * c < distanceInKm
        }

        val EARTH_RADIUS = 6371

        private fun haversin(`val`: Double): Double {
            return Math.pow(Math.sin(`val` / 2), 2.0)
        }
    }
}