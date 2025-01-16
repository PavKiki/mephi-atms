package ru.dsis.atms.jdbc.util

fun nullIfZero(int: Int): Int? = if (int == 0) null else int
