package com.example.mysharedprefapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editorSharedPreferences: SharedPreferences.Editor
    private var myValue = 0

    private var VALUE_KEY = "MY_KEY"

//   SharedPreferences sharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences =
            this.getSharedPreferences("com.example.mysharedprefapp", Context.MODE_PRIVATE)
        editorSharedPreferences = sharedPreferences.edit()

        editorSharedPreferences

        my_button.setOnClickListener { _ ->
            myValue += 10000
            my_text.text = myValue.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        myValue = sharedPreferences.getInt(VALUE_KEY, 0)
        my_text.text = myValue.toString()
    }

    override fun onStop() {
        super.onStop()
        editorSharedPreferences.putInt(VALUE_KEY, myValue)
        editorSharedPreferences.commit()
    }
}
