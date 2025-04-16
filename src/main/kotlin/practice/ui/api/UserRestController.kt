package practice.ui.api

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import practice.application.RegisterUserRequest
import practice.application.UserAuthenticationService

@RequestMapping("/api/users")
@RestController
class UserRestController(
    private val userAuthenticationService: UserAuthenticationService
) {
    @PostMapping("/register")
    fun generateToken(@RequestBody @Valid request: RegisterUserRequest): ResponseEntity<ApiResponse<String>> {
        val token = userAuthenticationService.generateTokenByRegister(request)
        return ResponseEntity.ok(ApiResponse.success(token))
    }
}