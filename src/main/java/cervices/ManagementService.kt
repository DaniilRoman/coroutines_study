package cervices

import utils.log


class ManagementService(val restaurant: Restaurant, val queue: PeopleGroupQueue) {
    private val handledThread: Thread by lazy {
        Thread {
            while (true) {
                handlePeopleGroup()
                Thread.sleep(500L)
            }
        }
    }

    fun start() {
        handledThread.start()
    }

    private fun handlePeopleGroup() {
        val peopleGroup = queue.getGroupForOrder()
        val orderResolution = restaurant.handleOrder(peopleGroup)
        queue.handleOrderResolution(orderResolution)
        log("handled: $orderResolution")
    }
}
