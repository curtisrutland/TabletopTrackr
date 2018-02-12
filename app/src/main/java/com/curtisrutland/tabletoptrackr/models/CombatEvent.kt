package com.curtisrutland.tabletoptrackr.models

import java.util.UUID

class CombatEvent(var currentHp: Int, val hpChange: Int? = null,  val source: String? = null, val notes: String? = null){
    val id: UUID = UUID.randomUUID()
}