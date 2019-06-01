package kl.spnw.service.impl

import com.ninjasquad.springmockk.MockkBean
import com.querydsl.core.types.Predicate
import io.mockk.every
import kl.spnw.entity.City
import kl.spnw.entity.User
import kl.spnw.repository.UserRepository
import kl.spnw.service.UserFilterService
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class UserFilterServiceTest {
    @MockkBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userFilterService: UserFilterService

    private final val leeds = City("Leeds", 53.801277, -1.548567)
    private final val london = City("London", 51.509865, -0.118092)
    private final val belfast = City("Belfast", 54.607868, -5.926437)

    private final val defaultReturnList = listOf(
            User(-1, "Caroline", 41, 153, "", leeds, "dummy", 0.76, 2, true, ""),
            User(-1, "Sharon", 39, 170, "", london, "dummy", 0.4, 0, false, ""),
            User(-1, "Carol", 25, 144, "", belfast, null, 0.9, 0, true, ""),
            User(-1, "Amy", 55, 160, "", leeds, "dummy", 0.8, 0, false, ""),
            User(-1, "Susan", 22, 166, "", london, null, 0.5, 10, false, ""),
            User(-1, "Mary", 66, 159, "", belfast, "dummy", 0.3, 0, true, "")
    )

    @ParameterizedTest
    @CsvSource(value = [
        "4,55.458565,-4.629179,300", // Ary
        "2,51.509865,-0.118092,30", // London
        "4,51.509865,-0.118092,300" // London
    ])
    fun `test service will filter invalid distance`(expectedMatch: Int, lat: Double, lon: Double, distance: Int) {
        every { userRepository.findAll(any<Predicate>()) } returns defaultReturnList

        // action - most of the param doesn't matter as we mock the return value
        val result = userFilterService.filterUser(true, true, false, 1, 99,
                18, 95, 120, 210,
                distance, lat, lon)

        assert(result.count() == expectedMatch)
    }
}