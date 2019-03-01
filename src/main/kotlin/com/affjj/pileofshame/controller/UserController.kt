package com.affjj.pileofshame.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
class UserController(registrations: ClientRegistrationRepository) {
    private val registration: ClientRegistration

    init {
        this.registration = registrations.findByRegistrationId("okta")
    }

    @GetMapping("/api/user")
    fun getUser(@AuthenticationPrincipal user: OAuth2User?): ResponseEntity<*> {
        return if (user == null) {
            ResponseEntity("", HttpStatus.OK)
        } else {
            ResponseEntity.ok().body(user.attributes)
        }
    }

    @PostMapping("/api/logout")
    fun logout(request: HttpServletRequest,
               @AuthenticationPrincipal(expression = "idToken") idToken: OidcIdToken): ResponseEntity<*> {
        // send logout URL to client so they can initiate logout
        val logoutUrl = this.registration.providerDetails
                .configurationMetadata["end_session_endpoint"].toString()

        val logoutDetails = HashMap<String, String>()
        logoutDetails["logoutUrl"] = logoutUrl
        logoutDetails["idToken"] = idToken.tokenValue
        request.getSession(false).invalidate()
        return ResponseEntity.ok().body<Map<String, String>>(logoutDetails)
    }
}