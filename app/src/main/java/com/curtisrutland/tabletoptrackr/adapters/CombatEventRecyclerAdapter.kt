package com.curtisrutland.tabletoptrackr.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.curtisrutland.tabletoptrackr.R
import com.curtisrutland.tabletoptrackr.controllers.AddCombatEventActivity
import com.curtisrutland.tabletoptrackr.models.CombatEvent
import org.jetbrains.anko.startActivity

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

        fun bindCombatEvent(event: CombatEvent) {

            source?.text = event.source
            hpChange?.text = event.hpChange?.toString() ?: "0"
            //hpChange?.setTextColor(if (event.hpChange ?: 0 > 0) Color.GREEN else Color.RED)
            currentHp?.text = event.currentHp.toString()
            notes?.text = event.notes

            val hpVis = if (event.hpChange ?: 0 == 0) View.GONE else View.VISIBLE

            hpChange?.visibility = hpVis
            currentHp?.visibility = hpVis
            arrow?.visibility = hpVis

            icon?.setOnClickListener {
                context.startActivity<AddCombatEventActivity>(
                        "source" to event.source,
                        "notes" to event.notes,
                        "hpChange" to event.hpChange.toString(),
                        "id" to event.id.toString()
                )
            }
        }
    }
}