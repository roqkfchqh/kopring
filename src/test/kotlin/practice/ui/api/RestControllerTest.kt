package practice.ui.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import supports.TestEnvironment

@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
@TestEnvironment
abstract class RestControllerTest {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(
                    restDocumentationContextProvider
                ).operationPreprocessors()
                    .withRequestDefaults(Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.prettyPrint())
            )
            .build()
    }

    fun MockHttpServletRequestDsl.jsonContent(value: Any) {
        content = objectMapper.writeValueAsString(value)
        contentType = MediaType.APPLICATION_JSON
    }

    fun success(value: Any): ResultMatcher {
        val expected = objectMapper.writeValueAsString(ApiResponse.success(value))
        return content().json(expected)
    }

    fun error(message: String): ResultMatcher {
        val expected = objectMapper.writeValueAsString(ApiResponse.error(message))
        return content().json(expected)
    }

}