package com.agencytravelboat.buildvariant

import splitties.resources.appStr

object Config {

    private const val DEV = true
    private const val STAGE = false
    private const val PROD = false
    private var base: String = appStr(R.string.API_DEV)

    const val IS_PRODUCTION = false

    fun retornaUrlBase(): String {
        return if (DEV) {
            appStr(R.string.API_DEV)
        } else if (STAGE) {
            appStr(R.string.API_STAGE)
        } else if (PROD) {
            appStr(R.string.API_PROD)
        } else {
            base
        }
    }
}