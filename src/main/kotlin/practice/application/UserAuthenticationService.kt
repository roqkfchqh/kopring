package practice.application

import org.springframework.stereotype.Service
import practice.domain.user.UserRepository
import practice.domain.user.existsByEmail
import practice.security.JwtTokenProvider

@Service
class UserAuthenticationService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun generateTokenByRegister(request: RegisterUserRequest): String {
        require(request.password == request.confirmPassword) { "비밀번호가 일치하지 않습니다." }
        check(!userRepository.existsByEmail(request.email)) { "이미 가입된 이메일입니다." }
        val user = userRepository.save(request.toEntity())
        return jwtTokenProvider.createToken(user.email)
    }
}