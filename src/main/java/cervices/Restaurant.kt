package cervices

import cervices.data.OrderResolution
import entities.PeopleGroup
import entities.Table
import enums.Resolution

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
        peopleGroup.onAccept(table)
        table.onAccept(peopleGroup)
    }
}