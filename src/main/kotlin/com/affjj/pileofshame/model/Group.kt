package com.affjj.pileofshame.model

import org.jetbrains.annotations.NotNull
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "user_group")
class Group(
    @NotNull
    val name: String? = null,
    val address: String? = null,
    val city: String? = null,
    val stateOrProvince: String? = null,
    val country: String? = null,
    val postalCode: String? = null,

    @ManyToOne(cascade = arrayOf(CascadeType.PERSIST))
    val user: User? = null,

    @OneToMany(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    val events: Set<Event>? = null
) : AbstractJpaPersistable<Long>()