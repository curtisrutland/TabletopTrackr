package com.curtisrutland.tabletoptrackr.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.curtisrutland.tabletoptrackr.R
import com.curtisrutland.tabletoptrackr.adapters.CombatEventRecyclerAdapter
import com.curtisrutland.tabletoptrackr.models.CombatEvent
import com.curtisrutland.tabletoptrackr.services.CombatEventDataService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CombatEventRecyclerAdapter(this, CombatEventDataService.events)
        combatEventRecylerView.adapter = adapter
        val layout = LinearLayoutManager(this)
        combatEventRecylerView.layoutManager = layout
        combatEventRecylerView.setHasFixedSize(true)

        addEventFab.setOnClickListener {
            val newEvent = CombatEvent(100, 100, "From FAB", "notes from fab")
            CombatEventDataService.addEvent(newEvent)
            adapter.notifyItemInserted(0)
            layout.scrollToPositionWithOffset(0,0)
        }
    }
}
