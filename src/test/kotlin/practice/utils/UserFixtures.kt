package practice.utils

import practice.application.RegisterUserRequest
import practice.domain.user.Gender
import practice.domain.user.Password
import supports.createLocalDate
import java.time.LocalDate

const val NAME: String = "지원자"
const val EMAIL: String = "test@email.com"
const val PHONE_NUMBER: String = "010-0000-0000"
val GENDER: Gender = Gender.FEMALE
val BIRTHDAY: LocalDate = createLocalDate(1995, 2, 2)
val PASSWORD: Password = Password("password")
val CONFIRM_PASSWORD: Password = Password("password")

fun createRegisterUserRequest(
    name: String = NAME,
    email: String = EMAIL,
    phoneNumber: String = PHONE_NUMBER,
    gender: Gender = GENDER,
    birthday: LocalDate = BIRTHDAY,
    password: Password = PASSWORD,
    confirmPassword: Password = CONFIRM_PASSWORD,
): RegisterUserRequest {
    return RegisterUserRequest(
        name,
        email,
        phoneNumber,
        gender,
        birthday,
        password,
        confirmPassword
    )
}