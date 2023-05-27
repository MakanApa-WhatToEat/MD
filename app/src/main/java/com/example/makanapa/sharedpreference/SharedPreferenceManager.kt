package com.example.makanapa.sharedpreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context : Context) {

    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
    private val editor : SharedPreferences.Editor = sharedPreferences.edit()

    fun saveToken(token : String, name : String, email : String){
        editor.putString("token", token)
        editor.putString("username", name)
        editor.putString("email", email)
        editor.apply()
    }

    fun getToken() : String?{
        return sharedPreferences.getString("token", null)
    }

    fun getUsername() : String?{
        return sharedPreferences.getString("username", null)
    }

    fun getEmail() : String?{
        return sharedPreferences.getString("email", null)
    }


    fun clearToken(){
        editor.remove("token")
        editor.apply()
    }
}