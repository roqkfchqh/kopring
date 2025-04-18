package practice.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate

@Embeddable
class UserInformation(
    @Column(nullable = false, length = 30)
    val name: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false, length = 13)
    val phoneNumber: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false)
    val birthday: LocalDate
) {
}