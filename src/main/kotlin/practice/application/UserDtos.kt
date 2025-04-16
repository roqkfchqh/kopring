package practice.application

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern
import practice.domain.user.Gender
import practice.domain.user.Password
import practice.domain.user.User
import java.time.LocalDate

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val birthday: LocalDate,
) {
    constructor(user: User): this(
        user.id,
        user.name,
        user.email,
        user.phoneNumber,
        user.gender,
        user.birthday,
    )
}

data class RegisterUserRequest(
    @field:Pattern(regexp = "[가-힣]{1,30}", message = "올바른 형식의 이름이어야 합니다")
    val name: String,

    @field:Email
    val email: String,

    @field:Pattern(regexp = "010-\\d{4}-\\d{4}", message = "올바른 형식의 전화번호여야 합니다")
    val phoneNumber: String,
    val gender: Gender,

    @field:Past
    val birthday: LocalDate,
    val password: Password,
    val confirmPassword: Password
){
    fun toEntity(): User {
        return User(name, email, phoneNumber, gender, birthday, password)
    }
}