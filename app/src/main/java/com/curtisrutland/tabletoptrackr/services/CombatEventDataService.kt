package com.curtisrutland.tabletoptrackr.services

import com.curtisrutland.tabletoptrackr.models.CombatEvent
import java.util.*

object CombatEventDataService {
    var currentHp = 20
        private set

    val events = mutableListOf(
            CombatEvent(20, 20, "Initial")
    )

    private val itemInsertedListeners = mutableListOf<() -> Unit>()
    private val currentHpChangedListeners = mutableListOf<(Int) -> Unit>()
    private val itemUpdatedListeners = mutableListOf<(Int) -> Unit>()
    private val itemRemovedListeners = mutableListOf<(Int) -> Unit>()

    fun addEvent(hpChange: Int?, source: String, notes: String) {
        currentHp += hpChange ?: 0
        events.add(0, CombatEvent(currentHp, hpChange, source, notes))
        notifyHpChanged()
        notifyItemInserted()
    }

    fun editEvent(id: UUID, hpChange: Int?, source: String, notes: String) {
        val targetIndex = events.indexOfFirst { it.id == id }
        if (targetIndex < 0) return
        var hp = 0
        val maxIdx = events.count() - 1
        for ((reversedIndex, evt) in events.reversed().withIndex()) {
            val index = maxIdx - reversedIndex
            if (index == targetIndex) {
                hp += hpChange ?: 0
                events[index] = CombatEvent(hp, hpChange, source, notes)
            } else {
                hp += evt.hpChange ?: 0
                evt.currentHp = hp
            }
            notifyItemUpdated(index)
        }
        currentHp = hp
        notifyHpChanged()
    }

    fun removeEvent(id: UUID) {
        val targetIndex = events.indexOfFirst { it.id == id }
        removeEvent(targetIndex)
    }

    fun removeEvent(targetIndex: Int) {
        if (targetIndex < 0) return
        events.removeAt(targetIndex)
        var hp = 0
        val maxIdx = events.count() - 1
        for ((reversedIndex, evt) in events.reversed().withIndex()) {
            hp += evt.hpChange ?: 0
            evt.currentHp = hp
            notifyItemUpdated(maxIdx - reversedIndex)
        }
        currentHp = hp
        notifyItemRemoved(targetIndex)
        notifyHpChanged()
    }

    fun addCurrentHpChangedListener(listener: (Int) -> Unit) {
        currentHpChangedListeners.add(listener)
    }

    fun addOnItemRemovedListener(listener: (Int) -> Unit) {
        itemRemovedListeners.add(listener)
    }

    fun addOnItemInsertedListener(listener: () -> Unit) {
        itemInsertedListeners.add(listener)
    }

    fun addOnItemUpdatedListener(listener: (Int) -> Unit) {
        itemUpdatedListeners.add(listener)
    }

    private fun notifyItemUpdated(updatedPosition: Int) {
        itemUpdatedListeners.forEach { it(updatedPosition) }
    }

    fun notifyItemRemoved(removedPosition: Int) {
        itemRemovedListeners.forEach { it(removedPosition) }
    }

    private fun notifyItemInserted() {
        itemInsertedListeners.forEach { it() }
    }

    private fun notifyHpChanged() {
        currentHpChangedListeners.forEach { it(currentHp) }
    }
}