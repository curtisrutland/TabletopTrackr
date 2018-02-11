package com.curtisrutland.tabletoptrackr.services

import com.curtisrutland.tabletoptrackr.models.CombatEvent

object CombatEventDataService {
    val events = mutableListOf(
            CombatEvent(20, source = "Initial"),
            CombatEvent(30, source = "Something", notes = "notes go here"),
            CombatEvent(20, source = "Initial"),
            CombatEvent(20, source = "Initial"),
            CombatEvent(20, source = "Initial"),
            CombatEvent(20, source = "Initial")
    )

    fun addEvent(event: CombatEvent) {
        events.add(0, event)
    }

    fun getCombatEvents(): MutableList<CombatEvent> {
        return events
    }
}