package es.trespies.movies.repositories.paging

class LoadMoreState {

    val isRunning: Boolean
    val errorMessage: String
    private var handledError = false
    private var hasMore = true

    val errorMessageIfNotHandled: String?
        get() {
            if (handledError) {
                return null
            }
            handledError = true
            return errorMessage
        }

    constructor(running: Boolean, errorMessage: String?) {
        this.isRunning = running
        this.errorMessage = errorMessage ?: ""
    }


    constructor(running: Boolean, hasMorePages: Boolean, errorMessage: String?) {
        this.isRunning = running
        this.errorMessage = errorMessage ?: ""
        this.hasMore = hasMorePages
    }

    fun hasMorePages(): Boolean {
        return hasMore
    }

}