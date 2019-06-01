package kl.spnw

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.restdocs.snippet.Attributes.key
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ExtendWith(value = [RestDocumentationExtension::class, SpringExtension::class])
@SpringBootTest
class SpringRestDocTests {
    private var mockMvc: MockMvc? = null

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext,
              restDocumentation: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation))
                .build()
    }

    @Test
    fun test() {
        this.mockMvc!!.perform(get(
                "/api/user?hasPhoto=true&inContact=false&isFavourite=false&compatibilityScoreLow=10&compatibilityScoreHigh=99&ageLow=18&ageHigh=95&heightLow=135&heightHigh=210&userLatitude=55.458565&userLongitude=-4.629179&distanceWithinKM=300"
        ).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andDo(document("GetUser", preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("hasPhoto").optional().description("boolean to set should result user has photo").attributes(key("optional").value("Y")),
                                parameterWithName("inContact").optional().description("boolean to get user in/out your contact").attributes(key("optional").value("Y")),
                                parameterWithName("isFavourite").optional().description("boolean to search in your favourite or not").attributes(key("optional").value("Y")),
                                parameterWithName("compatibilityScoreLow").optional().description("Int score (1-99) lower bound, inclusive. No bound if null").attributes(key("optional").value("Y")),
                                parameterWithName("compatibilityScoreHigh").optional().description("Int score (1-99) uppper bound, inclusive. No bound if null").attributes(key("optional").value("Y")),
                                parameterWithName("ageLow").optional().description("Int age (18-95) lower bound, inclusive. No bound if null").attributes(key("optional").value("Y")),
                                parameterWithName("ageHigh").optional().description("Int age (18-95) uppper bound, inclusive. No bound if null").attributes(key("optional").value("Y")),
                                parameterWithName("heightLow").optional().description("Int height (135-210) lower bound, inclusive. No bound if null").attributes(key("optional").value("Y")),
                                parameterWithName("heightHigh").optional().description("Int height (135-210) upper bound, inclusive. No bound if null").attributes(key("optional").value("Y")),
                                parameterWithName("userLatitude").optional().description("Current user's latitude. Latitude, longitude and distance must be all null or all non-empty").attributes(key("optional").value("Y")),
                                parameterWithName("userLongitude").optional().description("Current user's longitude. Latitude, longitude and distance must be all null or all non-empty").attributes(key("optional").value("Y")),
                                parameterWithName("distanceWithinKM").optional().description("Search distance in KM. Latitude, longitude and distance must be all null or all non-empty").attributes(key("optional").value("Y"))),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("User's id in database").attributes(key("optional").value("N")),
                                fieldWithPath("[].display_name").type(JsonFieldType.STRING).description("User's display name").attributes(key("optional").value("N")),
                                fieldWithPath("[].age").type(JsonFieldType.NUMBER).description("User's age").attributes(key("optional").value("N")),
                                fieldWithPath("[].height_in_cm").type(JsonFieldType.NUMBER).description("User's height in cm").attributes(key("optional").value("N")),
                                fieldWithPath("[].job_title").type(JsonFieldType.STRING).optional().description("User's job title").attributes(key("optional").value("Y")),
                                fieldWithPath("[].city").type(JsonFieldType.OBJECT).description("User's city info").attributes(key("optional").value("N")),
                                fieldWithPath("[].city.name").type(JsonFieldType.STRING).description("User's city name").attributes(key("optional").value("N")),
                                fieldWithPath("[].city.latitude").type(JsonFieldType.NUMBER).description("User's city latitude").attributes(key("optional").value("N")),
                                fieldWithPath("[].city.longitude").type(JsonFieldType.NUMBER).description("User's city longitude").attributes(key("optional").value("N")),
                                fieldWithPath("[].main_photo").type(JsonFieldType.STRING).optional().description("User's photo URL").attributes(key("optional").value("Y")),
                                fieldWithPath("[].compatibility_score").type(JsonFieldType.NUMBER).description("Compatibility score with current user").attributes(key("optional").value("N")),
                                fieldWithPath("[].contacts_exchanged").type(JsonFieldType.NUMBER).description("How many contacts do this user have").attributes(key("optional").value("N")),
                                fieldWithPath("[].favourite").type(JsonFieldType.BOOLEAN).description("Is this user the current user's favourite").attributes(key("optional").value("N")),
                                fieldWithPath("[].religion").type(JsonFieldType.STRING).optional().description("User's religion").attributes(key("optional").value("Y"))
                        )
                ))
    }
}