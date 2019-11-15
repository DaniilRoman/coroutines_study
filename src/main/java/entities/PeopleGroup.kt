package entities

import cervices.PeopleGroupQueue
import utils.log
import java.util.concurrent.atomic.AtomicInteger

object IdGen {
    val sharedCounter: AtomicInteger = AtomicInteger(0)

    fun getId(): Int {
        return sharedCounter.incrementAndGet()
    }
}

class PeopleGroup(val count: Int, var timeWaiting: Int, private val threshold: Int = 0) {
    private val clock: Thread by lazy {
        Thread {
            while(true) {
                onReject()
                try {
                    Thread.sleep(500)
                } catch (ex: InterruptedException) {
                    break
                }
            }
        }
    }

    val id: Int by lazy {
        IdGen.getId()
    }

    private var table: Table? = null
    private var queue: PeopleGroupQueue? = null

    fun link(queue: PeopleGroupQueue) {
        this.queue = queue
    }

    fun onAccept(table: Table) {
        this.table = table
        if (!clock.isAlive) {
            clock.start()
        }
    }

    fun onReject() {
        log("WAIT: $id ::: $timeWaiting")
        timeWaiting--
        checkOnLeave()
    }

    private fun checkOnLeave() {
        if (timeWaiting < threshold) {
            leave()
        }
    }

    private fun leave() {
        table?.onLeave(this)
        queue?.onDelete(this)
        clock.interrupt()
    }


    override fun toString(): String {
        return "Group: {id: $id; count: $count; timeWaiting: $timeWaiting; threshold: $threshold ::::: Table: $table}"
    }

    override fun equals(other: Any?): Boolean {
        return id == (other as PeopleGroup).id
    }
}