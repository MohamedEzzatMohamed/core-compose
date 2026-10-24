package com.shared.core.validation_method



fun isNumber(value: String): Boolean {
    return value.isEmpty() || Regex("^\\d+\$").matches(value)
}

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex(pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    return email.trim().matches(emailRegex)
}

fun isPasswordValid(password: String, witLater : Boolean = false): Boolean {
    val passwordRegex = Regex(
        pattern = if (witLater){
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$"
        }else{
            ".*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"
        }
    )
    return passwordRegex.matches(password)
}