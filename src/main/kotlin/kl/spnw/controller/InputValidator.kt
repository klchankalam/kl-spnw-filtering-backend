package kl.spnw.controller

import org.springframework.http.HttpStatus
import org.springframework.util.StringUtils
import org.springframework.web.server.ResponseStatusException

open class InputValidator {
    companion object {
        @JvmStatic
        fun checkIntBoundary(inputLow: Int?, inputHigh: Int?, name: String, lowerBound: Int, upperBound: Int) {
            inputLow?.let {
                checkIntBoundary(it, name, lowerBound, upperBound)
            }
            inputHigh?.let {
                checkIntBoundary(it, name, lowerBound, upperBound)
            }
            if (inputLow != null && inputHigh != null &&
                    inputLow > inputHigh) {
                throw ResponseStatusException(badRequest, "Low value ($inputLow) must be less than or equal to high value ($inputHigh).")
            }
        }

        @JvmStatic
        fun checkIntBoundary(input: Int?, name: String, lower: Int, upper: Int) {
            input?.let {
                if (it < lower || it > upper)
                    throw ResponseStatusException(badRequest, "$name must be between $lower and $upper.")
            }
        }

        @JvmStatic
        fun checkDoubleBoundary(input: Double?, name: String, lower: Double, upper: Double) {
            input?.let {
                if (it < lower || it > upper)
                    throw ResponseStatusException(badRequest, "$name must be between $lower and $upper.")
            }
        }

        @JvmStatic
        fun checkAllOrNone(names: List<String>, vararg input: Any?) {
            var isEmpty: Boolean? = null
            for (i in input) {
                if (isEmpty == null) isEmpty = StringUtils.isEmpty(i)
                else if (isEmpty != StringUtils.isEmpty(i))  {
                    throw ResponseStatusException(badRequest, "${names.joinToString()} must be either all empty or all non-empty.")
                }
            }
        }


        private val badRequest = HttpStatus.BAD_REQUEST
    }
}