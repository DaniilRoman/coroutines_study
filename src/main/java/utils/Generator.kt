package utils

import entities.PeopleGroup
import kotlin.random.Random

class PeopleGenerator {
    fun generate(): PeopleGroup {
        val count = Random.nextInt(1, 5)
        val threshold = Random.nextInt(5, 15)
        return PeopleGroup(count, threshold, threshold)
    }
}