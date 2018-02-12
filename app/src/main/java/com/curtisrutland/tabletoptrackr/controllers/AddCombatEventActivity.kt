package com.curtisrutland.tabletoptrackr.controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import com.curtisrutland.tabletoptrackr.R
import com.curtisrutland.tabletoptrackr.services.CombatEventDataService
import kotlinx.android.synthetic.main.activity_add_combat_event.*
import java.util.UUID

class AddCombatEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_combat_event)

        hpChangeText.filters = arrayOf(InputFilter { source, _, _, dest, _, _ ->
            try {
                val input = (dest.toString() + source.toString()).toInt()
                if (input in 0..1000) null else ""
            } catch (e: Exception) {
                ""
            }
        })

        val data = getCombatEventData()

        hpChangeText.setText(data.hpChangeStr)
        sourceText.setText(data.source)
        notesText.setText(data.notes)
        positiveToggle.isChecked = data.hpChange ?: 0 > 0

        if (data.id != null)
            submitButton.text = getString(R.string.update_button_text)

        submitButton.setOnClickListener {
            val multiplier = if (positiveToggle.isChecked) 1 else -1
            val hpChange = if(hpChangeText.text.toString() != "") hpChangeText.text.toString().toInt() * multiplier else null
            val source = sourceText.text.toString()
            val notes = notesText.text.toString()

            if (data.id == null)
                CombatEventDataService.addEvent(hpChange, source, notes)
            else
                CombatEventDataService.editEvent(data.id, hpChange, source, notes)
            finish()
        }
    }

    data class CombatEventData(val id: UUID?, val hpChange: Int?, val hpChangeStr: String?, val source: String?, val notes: String?)

    private fun getCombatEventData(): CombatEventData {
        val hpChange = try {
            intent.getStringExtra("hpChange")?.toInt()
        } catch (e: Exception) {
            null
        }
        val hpChangeStr = if (hpChange == null) null else Math.abs(hpChange).toString()
        val source = intent.getStringExtra("source")
        val notes = intent.getStringExtra("notes")
        val idStr = intent.getStringExtra("id")
        val id = try {
            UUID.fromString(idStr)
        } catch (e: Exception) {
            null
        }
        return CombatEventData(id, hpChange, hpChangeStr, source, notes)
    }
}
