package es.trespies.movies.util

data class ObjectId<V>(val obj: V?, var version: Int = 0) {

    val isEmpty: Boolean
        get() = obj == null

    fun upgrade() {
        this.version++
    }

    fun reset() {
        this.version = 0
    }
}