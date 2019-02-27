package com.affjj.pileofshame.controller

import com.affjj.pileofshame.model.Group
import com.affjj.pileofshame.model.GroupRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.URISyntaxException
import javax.validation.Valid

@RestController
@RequestMapping("/api")
internal class GroupController(private val groupRepository: GroupRepository) {

    private val log: Logger = LoggerFactory.getLogger(GroupController::class.java)

    @GetMapping("/groups")
    fun groups(): List<Group> {
        return groupRepository.findAll()
    }

    @GetMapping("/group/{id}")
    fun getGroup(@PathVariable id: Long): ResponseEntity<*> {
        val group = groupRepository.findById(id)
        return group.map { response -> ResponseEntity.ok().body(response) }
                .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @PostMapping("/group")
    @Throws(URISyntaxException::class)
    fun createGroup(@Valid @RequestBody group: Group): ResponseEntity<Group> {
        log.info("Request to create group: {}", group)
        val result = groupRepository.save(group)
        return ResponseEntity.created(URI("/api/group/${result.id}"))
                .body<Group>(result)
    }

    @PutMapping("/group")
    fun updateGroup(@Valid @RequestBody group: Group): ResponseEntity<Group> {
        log.info("Request to update group: {}", group)
        val result = groupRepository.save(group)
        return ResponseEntity.ok().body<Group>(result)
    }

    @DeleteMapping("/group/{id}")
    fun deleteGroup(@PathVariable id: Long): ResponseEntity<*> {
        log.info("Request to delete group: {}", id)
        groupRepository.deleteById(id)
        return ResponseEntity.ok().build<Any>()
    }
}