package com.chan.common

interface BindListAdapter<T> {
    fun replaceData(data: T?)
    fun addAllData(data: T?)
}