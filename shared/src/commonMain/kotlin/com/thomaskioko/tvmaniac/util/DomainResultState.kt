package com.thomaskioko.tvmaniac.util

sealed class DomainResultState<ResultType> {

    data class Success<ResultType>(val data: ResultType) : DomainResultState<ResultType>()

    class Loading<ResultType> : DomainResultState<ResultType>() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false
            return true
        }

        override fun hashCode(): Int = this::class.hashCode()
    }

    data class Error<ResultType>(val message: String = "") : DomainResultState<ResultType>()

    companion object {
        /**
         * Creates [DomainResultState] object with [Success] state and [data].
         */
        fun <ResultType> success(data: ResultType): DomainResultState<ResultType> = Success(data)

        /**
         * Creates [DomainResultState] object with [Loading] state to notify
         * the UI to showing loading.
         */
        fun <ResultType> loading(): DomainResultState<ResultType> = Loading()

        /**
         * Creates [DomainResultState] object with [Error] state and [message].
         */
        fun <ResultType> error(throwable: Throwable): DomainResultState<ResultType> =
            Error(throwable.resolveError().message)
    }
}
