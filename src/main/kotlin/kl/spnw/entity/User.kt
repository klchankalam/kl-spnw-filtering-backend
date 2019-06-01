package kl.spnw.entity

import javax.persistence.*

@Entity
@SequenceGenerator(name="user_seq", allocationSize = 1)
data class User(
        @Id @GeneratedValue(generator = "user_seq")
        val id: Long = -1,
        val displayName: String,
        val age: Int,
        val heightInCm: Int,
        val jobTitle: String?,
        @ManyToOne(optional = false)
        @JoinColumn(name = "city")
        val city: City,
        val mainPhoto: String?,
        val compatibilityScore: Double,
        val contactsExchanged: Int,
        val favourite: Boolean,
        val religion: String?
    )