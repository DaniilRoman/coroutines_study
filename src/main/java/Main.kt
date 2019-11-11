import cervices.ManagementService
import cervices.PeopleGroupQueue
import cervices.Restaurant

fun main(args: Array<String>) {
    val restaurant = Restaurant()
    val queue = PeopleGroupQueue()

    ManagementService(restaurant, queue).start()
}











