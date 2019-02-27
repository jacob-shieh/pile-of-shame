package com.affjj.pileofshame.model

import javax.persistence.Entity
import javax.persistence.ManyToMany
import java.time.Instant

@Entity
class Event(
    val date: Instant? = null,
    val title: String? = null,
    val description: String? = null,
    @ManyToMany
    val attendees: Set<User>? = null
) : AbstractJpaPersistable<Long>()