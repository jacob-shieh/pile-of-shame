package com.affjj.pileofshame.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    val name: String? = null,
    val email: String? = null
) : AbstractJpaPersistable<Long>()