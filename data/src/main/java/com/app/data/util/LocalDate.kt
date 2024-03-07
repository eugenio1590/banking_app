package com.app.data.util

import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date

fun LocalDate.toDate(): Date = Date.from(atStartOfDay().toInstant(ZoneOffset.UTC))
