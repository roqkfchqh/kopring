package practice.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import practice.domain.user.UserRepository
import practice.domain.user.existsByEmail
import practice.security.JwtTokenProvider

class UserAuthenticationServiceTest: BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val jwtTokenProvider = mockk<JwtTokenProvider>()

    val userAuthenticationService = UserAuthenticationService(userRepository, jwtTokenProvider)

    Given("특정 이메일의 회원이 이미 있는 경우") {
        every { userRepository.existsByEmail(any()) } returns true

        When("해당 이메일로 회원 가입을 하고 토큰을 생성하면") {
            Then("예외가 발생한다") {
                shouldThrow<IllegalStateException> {
                    userAuthenticationService.generateTokenByRegister(practice.utils.createRegisterUserRequest())
                }
            }
        }
    }
})