package cervices


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
