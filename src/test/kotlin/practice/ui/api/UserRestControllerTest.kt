package practice.ui.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.post
import practice.application.UserAuthenticationService
import practice.domain.user.Gender
import supports.createLocalDate
import java.time.LocalDate

private const val PASSWORD = "password"

private fun createRegisterUserRequest(
    name: String = "회원",
    email: String = "test@email.com",
    phoneNumber: String = "010-0000-0000",
    gender: Gender = Gender.FEMALE,
    birthday: LocalDate = createLocalDate(1995, 2, 2),
    password: String = PASSWORD,
    confirmPassword: String = PASSWORD
): Map<String, Any> {
    return mapOf(
        "name" to name,
        "email" to email,
        "phoneNumber" to phoneNumber,
        "gender" to gender,
        "birthday" to birthday,
        "password" to password,
        "confirmPassword" to confirmPassword
    )
}

@WebMvcTest(UserRestController::class)
class UserRestControllerTest: RestControllerTest() {
    @MockkBean
    private lateinit var userAuthenticationService: UserAuthenticationService

    @Test
    fun `유효한 회원 생성 및 검증 요청에 대하여 응답으로 토큰이 반환된다`() {
        val response = "valid_token"
        every { userAuthenticationService.generateTokenByRegister(any()) } returns response

        mockMvc.post("/api/users/register") {
            jsonContent(createRegisterUserRequest())
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            handle(document("user-register-post"))
        }
    }
}