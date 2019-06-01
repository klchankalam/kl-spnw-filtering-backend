package kl.spnw

import kl.spnw.entity.City
import kl.spnw.entity.User
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KlSpnwApplicationTests {

	@Autowired
	lateinit var webClient: WebTestClient

	val userCity = City("Ayr", 55.458565, -4.629179)

	private fun assertBasicInfoAndSize(size: Int, urlParam: String?): List<User> =
		this.webClient.get().uri("/api/user?$urlParam").exchange()
				.expectStatus().isOk
				.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
				.expectBodyList(User::class.java)
				.hasSize(size)
				.returnResult().responseBody!!

	private fun assetListContainAllNames(actualUserList: List<User>, expectedNameList: List<String>) =
			assert(actualUserList.map { it.displayName }.containsAll(expectedNameList))

	private fun genericTest(expectedNameList: List<String>, urlParam: String) {
		val userList = assertBasicInfoAndSize(expectedNameList.size, urlParam)
		assetListContainAllNames(userList, expectedNameList)
	}

	@Test
	fun testGetAll() {
		assertBasicInfoAndSize(25, null)
	}

	@Test
	fun testFilterOnFavourite() {
		genericTest(listOf("Caroline","Katlin","Angie","Elizabeth","Kysha","Katherine"),"isFavourite=true")
	}

	@Test
	fun testFilterOnPhoto() {
		genericTest(listOf("Katie","Kysha","Stephanie"),"hasPhoto=false")
	}

	@Test
	fun testFilterInContact() {
		assertBasicInfoAndSize(12, "inContact=true")
	}

	@Test
	fun testFilterOnScoreLow() {
		val unexpectedNameList = listOf("Natalia")
		val userList = assertBasicInfoAndSize(24, "compatibilityScoreLow=49")
		assert(!userList.map { it.displayName }.containsAll(unexpectedNameList))
	}

	@Test
	fun testFilterOnScoreHigh() {
		genericTest(listOf("Natalia","Diana"),"compatibilityScoreHigh=51")
	}

	@Test
	fun testFilterOnAgeHigh() {
		genericTest(listOf("Susan"),"ageHigh=30")
	}

	@Test
	fun testFilterOnAgeLow() {
		genericTest(listOf("Marta", "Angie"),"ageLow=50")
	}

	@Test
	fun testFilterOnHeightHigh() {
		genericTest(listOf("Emma", "Natalia","Marta","Katie","Clare","Elizabeth","Kysha"),"heightHigh=150")
	}

	@Test
	fun testFilterOnHeightLow() {
		genericTest(listOf("Maria", "Colette","Anne","Daniela","Katherine"),"heightLow=170")
	}

	@Test
	fun testFilterNotInContactAndScore() {
		genericTest(listOf("Emma", "Diana","Daniela"),"inContact=false&compatibilityScoreLow=1&compatibilityScoreHigh=80")
	}

	@Test
	fun testFilterOnPhotoAndFavourite() {
		genericTest(listOf("Kysha"),"isFavourite=true&hasPhoto=false")
	}

	@Test
	fun testFilterOnHeightAndAge() {
		genericTest(listOf("Colette", "Anne","Daniela","Katherine"),"ageHigh=40&heightLow=170")
	}

	@Test
	fun testFilterBy300KM() {
		genericTest(listOf("Caroline", "Emma", "Katlin", "Tracy", "Angie", "Samantha", "Elizabeth"),
				"userLatitude=${userCity.latitude}&userLongitude=${userCity.longitude}&distanceWithinKM=300")
	}

	@Test
	fun testFilterBy30KM() {
		genericTest(listOf("Angie"),
				"userLatitude=${userCity.latitude}&userLongitude=${userCity.longitude}&distanceWithinKM=30")
	}

	@Test
	fun testFilterBy100KM() {
		genericTest(listOf("Angie", "Samantha"),
				"userLatitude=${userCity.latitude}&userLongitude=${userCity.longitude}&distanceWithinKM=150")
	}
}
