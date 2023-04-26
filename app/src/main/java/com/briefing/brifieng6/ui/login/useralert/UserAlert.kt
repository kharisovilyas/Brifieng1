package com.briefing.brifieng6.ui.login.useralert

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp


class UserAlert {
    fun alertOfUserAlreadyExist(context: Context){
        Toast.makeText(context, "Пользователь существует! Попробуйте войти", Toast.LENGTH_SHORT).show()
    }
}