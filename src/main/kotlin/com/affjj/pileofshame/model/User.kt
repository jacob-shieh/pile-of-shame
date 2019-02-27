package com.affjj.pileofshame.model

import javax.persistence.Entity

@Entity
class User(
    val name: String? = null,
    val email: String? = null
) : AbstractJpaPersistable<Long>()