package entities


class PeopleGroup(val count: Int, var timeWaiting: Int, private val threshold: Int) {
    private var table: Table? = null

    fun onAccept(table: Table) {
        this.table = table
    }

    fun onReject() {
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
    }
}