package entities

class Table(private val total: Int,private var available: Int = total) {
    fun isContainStrict(n: Int): Boolean {
        return available == total && available >= n
    }

    fun isContain(n: Int): Boolean {
        return available >= n
    }

    fun onAccept(peopleGroup: PeopleGroup) {
        available = available - peopleGroup.count
        print(available)
    }

    fun onLeave(peopleGroup: PeopleGroup) {
        available = available + peopleGroup.count
    }

    override fun toString(): String {
        return "Table: {total: $total, available: $available}"
    }
}