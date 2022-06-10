package es.trespies.movies.util

data class Resource<out T>(val status: Status, val data: T?, val message: String? = null) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> none(data: T?): Resource<T> {
            return Resource(Status.NONE, data, null)
        }
    }

    fun isFinalStatus(): Boolean {
        return status == Status.SUCCESS || status == Status.ERROR
    }

    fun isLoading(): Boolean {
        return status == Status.LOADING
    }

}