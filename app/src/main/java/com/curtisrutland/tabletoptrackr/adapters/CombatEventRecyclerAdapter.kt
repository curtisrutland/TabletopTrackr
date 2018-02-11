package com.curtisrutland.tabletoptrackr.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.curtisrutland.tabletoptrackr.R
import com.curtisrutland.tabletoptrackr.models.CombatEvent

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

        fun bindCombatEvent(event: CombatEvent) {
            source?.text = event.source
            hpChange?.text = event.hpChange?.toString() ?: "0"
            currentHp?.text = event.currentHp.toString()
            notes?.text = event.notes
        }
    }
}