package com.affjj.pileofshame.controller

import com.affjj.pileofshame.model.Group
import com.affjj.pileofshame.model.GroupRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
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
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/api")
internal class GroupController(private val groupRepository: GroupRepository
//                               , private val userRepository: UserRepository
) {

    private val log = LoggerFactory.getLogger(GroupController::class.java)

    @GetMapping("/groups")
    fun groups(principal: Principal): Collection<Group> {
        return groupRepository.findAllByUserId(principal.name)
    }

    @GetMapping("/group/{id}")
    fun getGroup(@PathVariable id: Long): ResponseEntity<*> {
        val group = groupRepository.findById(id)
        return group.map { response -> ResponseEntity.ok().body<Any>(response) }
                .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @PostMapping("/group")
    @Throws(URISyntaxException::class)
    fun createGroup(@Valid @RequestBody group: Group,
                    @AuthenticationPrincipal principal: OAuth2User): ResponseEntity<Group> {
        log.info("Request to create group: {}", group)
//        val details = principal.attributes
//        val userId = details["sub"].toString()

        // check to see if user already exists
//        val user = userRepository.findById(userId)
//        group.setUser(user.orElse(User(userId,
//                details["name"].toString(), details["email"].toString())))

        val result = groupRepository.save(group)
        return ResponseEntity.created(URI("/api/group/" + result.getId()))
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