package kl.spnw.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class City (
        @Id
        val name: String,
        val latitude: Double,
        val longitude: Double
)
