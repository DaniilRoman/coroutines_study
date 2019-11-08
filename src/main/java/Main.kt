import kotlin.random.Random

fun main(args: Array<String>) {
    val restaurant = Restaurant()
    val queue = PeopleGroupQueue()

    ManagementService(restaurant, queue).start()
}

class ManagementService(val restaurant: Restaurant, val queue: PeopleGroupQueue) {

    fun start() {
        while(true) {
            handlePeopleGroup()
            Thread.sleep(500L)
        }
    }

    private fun handlePeopleGroup() {
        val peopleGroup = queue.getGroupForOrder()
        val orderResolution = restaurant.handleOrder(peopleGroup)
        queue.handleOrderResolution(orderResolution)
    }
}

class Restaurant {
    val tables = listOf(Table(5), Table(2), Table(6))

    fun handleOrder(peopleGroup: PeopleGroup): OrderResolution {
        var resolution = Resolution.REJECT

        tables.forEach { table ->
            if (table.isContainStrict(peopleGroup.count)) {
                acceptOrder(peopleGroup, table)
                resolution = Resolution.ACCEPT
            } else if (table.isContain(peopleGroup.count)) {
                acceptOrder(peopleGroup, table)
                resolution = Resolution.ACCEPT
            }
        }

        return OrderResolution(peopleGroup, resolution)
    }

    private fun acceptOrder(peopleGroup: PeopleGroup, table: Table) {

    }
}

class PeopleGroupQueue {
    val peopleGroups: MutableList<PeopleGroup> = mutableListOf()

    fun add(peopleGroup: PeopleGroup) {
        peopleGroups.add(peopleGroup)
    }

    fun getGroupForOrder(): PeopleGroup {
        return peopleGroups.removeAt(0)
    }

    fun handleOrderResolution(orderResolution: OrderResolution) {
        if (orderResolution.resolution == Resolution.REJECT) {
            orderResolution.peopleGroup.timeWaiting--
            peopleGroups.add(orderResolution.peopleGroup)
        }
    }
}



class PeopleGroup(private val count: Int, private var timeWaiting: Int, private val threshold: Int) {
    fun onAccept() {

    }

    fun onReject() {

    }
}

class Table(private val total: Int,private var available: Int = total) {
    fun isContainStrict(n: Int): Boolean {
        return available == total && available >= n
    }

    fun isContain(n: Int): Boolean {
        return available >= n
    }
}

data class OrderResolution(val peopleGroup: PeopleGroup, val resolution: Resolution)

enum class Resolution {
    ACCEPT, REJECT
}



class PeopleGenerator {
    fun generate(): PeopleGroup {
        val count = Random.nextInt(1, 5)
        val threshold = Random.nextInt(5, 15)
        return PeopleGroup(count, threshold, threshold)
    }
}