package cervices

import cervices.data.OrderResolution
import entities.PeopleGroup
import entities.Table
import enums.Resolution

class Restaurant(private val tables: List<Table>) {

    @Synchronized
    fun handleOrder(peopleGroup: PeopleGroup): OrderResolution {
        var resolution = Resolution.REJECT

        tables.map { table ->
            if (table.isContainStrict(peopleGroup.count)) {
                acceptOrder(peopleGroup, table)
                resolution = Resolution.ACCEPT
            }
            return@map table
        }.forEach { table ->
            if (table.isContain(peopleGroup.count)) {
                acceptOrder(peopleGroup, table)
                resolution = Resolution.ACCEPT
            }
        }

        return OrderResolution(peopleGroup, resolution)
    }

    override fun toString(): String {
        return "Restaurant: { ${tables.joinToString { table -> table.toString() }} }"
    }

    private fun acceptOrder(peopleGroup: PeopleGroup, table: Table) {
        peopleGroup.onAccept(table)
        table.onAccept(peopleGroup)
    }
}