package kl.spnw.controller

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.web.server.ResponseStatusException
import java.math.BigInteger

open class InputValidatorTests {

    @Test
    fun testLowerIsGreaterThanUpper() {
        assertThrows(ResponseStatusException::class.java) {
            InputValidator.checkLowerAndUpperIntBoundary(101, 100, "asdf", 1, 200)
        }
    }

    @Test
    fun testLowerAndUpperNormal() {
        InputValidator.checkLowerAndUpperIntBoundary(100, 100, "asdf", 1, 200)
    }

    @Test
    fun testIntExceedBoundary() {
        assertThrows(ResponseStatusException::class.java) {
            InputValidator.checkIntBoundary(-1, "asdf", 0, 9)
        }
    }

    @Test
    fun testIntNormalBoundary() {
        InputValidator.checkIntBoundary(0, "asdf", 0, 9)
    }

    @Test
    fun testDoubleExceedBoundary() {
        assertThrows(ResponseStatusException::class.java) {
            InputValidator.checkDoubleBoundary(9.01, "asdf", 0.0, 9.0)
        }
    }

    @Test
    fun testDoubleNormalBoundary() {
        InputValidator.checkDoubleBoundary(9.0, "asdf", 0.0, 9.0)
    }

    @Test
    fun testAllNull() {
        InputValidator.checkAllOrNone(listOf(), null, null ,null, null, null)
    }

    @Test
    fun testAllNotNull() {
        InputValidator.checkAllOrNone(listOf(), 1, 2.0 ,"asdf", "qwer", BigInteger("780912432098712420978"))
    }

    @Test
    fun testSomeNull() {
        assertThrows(ResponseStatusException::class.java) {
            InputValidator.checkAllOrNone(listOf(), 1.0, 2, "asdf", "qwer", null)
        }
    }
}