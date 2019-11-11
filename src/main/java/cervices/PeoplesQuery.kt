package cervices

import cervices.data.OrderResolution
import cervices.data.QueryCommand
import entities.PeopleGroup
import enums.QueryCommandType
import enums.Resolution

class PeopleGroupQueue {
    val peopleGroups: MutableList<PeopleGroup> = mutableListOf()
    private val commands: MutableList<QueryCommand>  = mutableListOf()

    fun add(peopleGroup: PeopleGroup) {
        peopleGroups.add(peopleGroup)
    }

    fun getGroupForOrder(): PeopleGroup {
        return peopleGroups.removeAt(0)
    }

    fun handleOrderResolution(orderResolution: OrderResolution) {
        if (orderResolution.resolution == Resolution.REJECT) {
            orderResolution.peopleGroup.onReject()
            onAdd(orderResolution.peopleGroup)
        }
    }

    private fun handleLastCommand() {
        val command = commands.removeAt(0)
        when (command.type) {
            QueryCommandType.ADD -> peopleGroups.add(command.peopleGroup)
            QueryCommandType.DELETE -> peopleGroups.remove(command.peopleGroup)
        }
    }

    private fun onAdd(peopleGroup: PeopleGroup) {
        commands.add(
                QueryCommand(
                        QueryCommandType.ADD, peopleGroup)
        )
    }

    private fun onDelete(peopleGroup: PeopleGroup) {
        commands.add(
                QueryCommand(
                        QueryCommandType.DELETE, peopleGroup)
        )
    }
}