package com.utils.extension

/**
 * Return contain '.'
 * Ex: .xml
 */
val String.fileExtension: String
    get() {
        return when (val dotIndex = this.lastIndexOf(".")) {
            -1 -> ""
            else -> this.substring(dotIndex)
        }
    }