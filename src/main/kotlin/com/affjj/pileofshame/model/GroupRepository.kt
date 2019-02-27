package com.affjj.pileofshame.model

import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Long> {
    fun findByName(name: String): Group
}