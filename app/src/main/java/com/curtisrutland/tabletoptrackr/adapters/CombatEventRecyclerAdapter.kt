package com.curtisrutland.tabletoptrackr.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.curtisrutland.tabletoptrackr.R
import com.curtisrutland.tabletoptrackr.controllers.AddCombatEventActivity
import com.curtisrutland.tabletoptrackr.models.CombatEvent
import com.curtisrutland.tabletoptrackr.services.CombatEventDataService
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class CombatEventRecyclerAdapter(private val context: Context, private val events: List<CombatEvent>)
    : RecyclerView.Adapter<CombatEventRecyclerAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindCombatEvent(events[position])
    }

    override fun getItemCount(): Int {
        return events.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.combat_event_item, parent, false)
        return Holder(view)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val source = itemView?.findViewById<TextView>(R.id.sourceText)
        private val hpChange = itemView?.findViewById<TextView>(R.id.hpChangeText)
        private val currentHp = itemView?.findViewById<TextView>(R.id.currentHpText)
        private val notes = itemView?.findViewById<TextView>(R.id.notesText)
        private val icon = itemView?.findViewById<ImageView>(R.id.menuIcon)
        private val arrow = itemView?.findViewById<ImageView>(R.id.arrowIcon)
        private val editMenuItemId = R.id.editMenuItem
        private val deleteMenuItemId = R.id.deleteMenuItem

        fun bindCombatEvent(event: CombatEvent) {
            source?.text = event.source
            hpChange?.text = event.hpChange?.toString() ?: "0"
            currentHp?.text = event.currentHp.toString()
            notes?.text = event.notes

            val hpVis = if (event.hpChange ?: 0 == 0) View.GONE else View.VISIBLE

            hpChange?.visibility = hpVis
            currentHp?.visibility = hpVis
            arrow?.visibility = hpVis

            icon?.setOnClickListener {
                val popup = PopupMenu(context, icon)
                popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        editMenuItemId -> {
                            context.startActivity<AddCombatEventActivity>(
                                    "source" to event.source,
                                    "notes" to event.notes,
                                    "hpChange" to event.hpChange.toString(),
                                    "id" to event.id.toString()
                            )
                        }
                        deleteMenuItemId -> {
                            context.alert("Are you sure you want to remove this event?",
                                    "Delete Event?") {
                                yesButton { CombatEventDataService.removeEvent(event.id) }
                                noButton { }
                            }.show()
                        }
                    }
                    true
                }
                popup.show()
            }
        }
    }
}