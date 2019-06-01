package kl.spnw

import kl.spnw.util.DistanceUtil
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

open class DistanceUtilTests {
    @ParameterizedTest
    @CsvSource(value = [
        "true,55.458565,-4.629179,53.801277,-1.548567,300", // Ary to Leeds
        "false,55.458565,-4.629179,51.509865,-0.118092,300", // Ary to London
        "false,55.458565,-4.629179,50.376289,-4.143841,300", // Ary to Plymouth
        "true,54.607868,-5.926437,55.006763,-7.318268,100", // Belfast to Londonderry
        "false,50.614429,-2.457621,50.720806,-1.904755,30", // Weymouth to Bournemouth
        "true,50.614429,-2.457621,50.720806,-1.904755,50" // Weymouth to Bournemouth
    ])
    fun test(expected: Boolean, startLat: Double, startLon: Double, endLat: Double, endLon: Double, distance: Int) {
        assert(expected == DistanceUtil.AreTwoCoordinateWithinDistance(startLat, startLon, endLat, endLon, distance))
    }
}