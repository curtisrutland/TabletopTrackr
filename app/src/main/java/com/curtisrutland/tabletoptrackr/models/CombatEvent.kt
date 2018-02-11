package com.curtisrutland.tabletoptrackr.models

class CombatEvent(val currentHp: Int, val hpChange: Int? = null,  val source: String? = null, val notes: String? = null)