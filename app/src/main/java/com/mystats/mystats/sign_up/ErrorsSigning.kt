package com.mystats.mystats.sign_up

interface ErrorsSigning{
    companion object{
        val ERROR_NOT_EQUAL_PASS : Int = 1
        val ERROR_REGISTRATION : Int = 2
        val ERROR_EMPTY_INPUT : Int = 3
        val ERROR_EMAIL_NOT_VERIFIED : Int = 4
        val ERROR_LOGIN : Int = 5
    }
}
