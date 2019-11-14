package cervices

import cervices.data.OrderResolution
import cervices.data.QueryCommand
import entities.PeopleGroup
import enums.QueryCommandType
import enums.Resolution
import utils.log

class PeopleGroupQueue {
    private val peopleGroups: MutableList<PeopleGroup> = mutableListOf()
    private val commands: MutableList<QueryCommand>  = mutableListOf()

    private val handledThread: Thread by lazy { Thread {
        while (true) {
            handleLastCommand()
            Thread.sleep(500)
        }
    }
    }

    fun start() {
        handledThread.start()
    }

    fun peopleInit(peopleGroup: PeopleGroup) {
        peopleGroups.add(peopleGroup)
        peopleGroup.link(this)
        log("people $peopleGroup inited")
    }

    fun getGroupForOrder(): PeopleGroup {
        while (true) {
            if (peopleGroups.isNotEmpty()) {
                return peopleGroups.removeAt(0)
            } else {
                Thread.sleep(300)
            }
        }
    }

    fun handleOrderResolution(orderResolution: OrderResolution) {
        // TODO: refactor extra adding when people leave queue
        if (orderResolution.resolution == Resolution.REJECT) {
            onAdd(orderResolution.peopleGroup)
            orderResolution.peopleGroup.onReject()
        }
    }

    private fun handleLastCommand() {
        if (commands.isNotEmpty()) {
            val command = commands.removeAt(0)
            when (command.type) {
                QueryCommandType.ADD -> peopleGroups.add(command.peopleGroup)
                QueryCommandType.DELETE -> peopleGroups.remove(command.peopleGroup)
            }
            log("Command $command handeled")
        }
    }

    private fun onAdd(peopleGroup: PeopleGroup) {
        commands.add(
                QueryCommand(
                        QueryCommandType.ADD, peopleGroup)
        )
    }

    fun onDelete(peopleGroup: PeopleGroup) {
        commands.add(
                QueryCommand(
                        QueryCommandType.DELETE, peopleGroup)
        )
    }
}