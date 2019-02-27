package com.affjj.pileofshame

import com.affjj.pileofshame.model.Group
import com.affjj.pileofshame.model.GroupRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.stream.Stream

@Component
internal class Initializer(private val repository: GroupRepository) : CommandLineRunner {

    override fun run(vararg strings: String) {
//        val e = Event(title = "Full Stack Reactive",
//                description = "Reactive with Spring Boot + React",
//                date = Instant.parse("2018-12-12T18:00:00.000Z"))
        Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
                "Richmond JUG").forEach { name -> repository.save(Group(name = name)) }

        repository.findAll().forEach { println(it) }
    }
}