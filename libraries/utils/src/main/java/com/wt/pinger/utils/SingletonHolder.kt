package com.wt.pinger.utils

/**
 * Created by kenumir on 10.02.2018.
 * source: https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 */
open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    /**
     * call in Application.onCreate
     */
    fun init(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }

    fun get(): T {
        val i = instance
        if (i != null) {
            return i
        }
        throw RuntimeException("No singleton instance of " + this.javaClass)
    }
}