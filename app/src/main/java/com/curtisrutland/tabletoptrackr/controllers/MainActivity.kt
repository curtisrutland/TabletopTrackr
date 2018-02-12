package com.curtisrutland.tabletoptrackr.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.curtisrutland.tabletoptrackr.R
import com.curtisrutland.tabletoptrackr.adapters.CombatEventRecyclerAdapter
import com.curtisrutland.tabletoptrackr.adapters.ItemTouchHelperCallback
import com.curtisrutland.tabletoptrackr.services.CombatEventDataService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CombatEventRecyclerAdapter(this, CombatEventDataService.events)
        combatEventRecylerView.adapter = adapter
        val layout = LinearLayoutManager(this)
        combatEventRecylerView.layoutManager = layout
        combatEventRecylerView.setHasFixedSize(true)

        currentHpText.text = CombatEventDataService.currentHp.toString()

        CombatEventDataService.addCurrentHpChangedListener { currentHpText.text = it.toString() }
        CombatEventDataService.addOnItemUpdatedListener { adapter.notifyDataSetChanged() } //{ adapter.notifyItemChanged(it) }
        CombatEventDataService.addOnItemRemovedListener { adapter.notifyDataSetChanged() } //{ adapter.notifyItemRemoved(it) }
        CombatEventDataService.addOnItemInsertedListener {
            //adapter.notifyItemInserted(0)
            adapter.notifyDataSetChanged()
            layout.scrollToPositionWithOffset(0, 0)
        }

        addEventFab.setOnClickListener {
            startActivity<AddCombatEventActivity>()
        }

        val helper = ItemTouchHelper(ItemTouchHelperCallback { pos ->
            alert("Are you sure you want to remove this event?",
                    "Delete Event?") {
                yesButton {
                    CombatEventDataService.removeEvent(pos)
                    adapter.notifyDataSetChanged()
                }
                noButton {
                    adapter.notifyDataSetChanged()
                }
            }.show()
        })
        helper.attachToRecyclerView(combatEventRecylerView)
    }
}
