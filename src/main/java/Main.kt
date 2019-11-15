import cervices.ManagementService
import cervices.PeopleGroupQueue
import cervices.Restaurant
import entities.PeopleGroup
import entities.Table
import utils.generatePeoples
import utils.generateTables

fun main(args: Array<String>) {
    val restaurant = Restaurant(listOf(Table(4), Table(3)))
    println(restaurant)

    val queue = PeopleGroupQueue()

    queue.start()

    addPeoplesTo(queue)

    ManagementService(restaurant, queue).start()
}

private fun addPeoplesTo(queue: PeopleGroupQueue) {
    listOf(PeopleGroup(4, 3),
            PeopleGroup(2, 2),
            PeopleGroup(3, 2))
            .forEach( queue::peopleInit)
}










