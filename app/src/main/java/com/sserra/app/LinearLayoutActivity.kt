package com.sserra.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.sserra.coffee.context

class RelativeLayoutActivity : AppCompatActivity() {

    companion object {
        fun start(ctx: Context) = ctx.startActivity(Intent(ctx, RelativeLayoutActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.relative_layout_activity)
    }
}

class LinearLayoutActivity : AppCompatActivity() {

    companion object {
        fun start(ctx: Context) = ctx.startActivity(Intent(ctx, LinearLayoutActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout_activity)

        val title = findViewById<TextView>(R.id.title)
        title.setOnClickListener {
            val popmenu = PopupMenu(this, title, Gravity.START)
            popmenu.inflate(R.menu.menu_popup)
            popmenu.setOnMenuItemClickListener {
                ScrollActivity.start(this@LinearLayoutActivity)
                true
            }
            popmenu.show()
        }
    }
}