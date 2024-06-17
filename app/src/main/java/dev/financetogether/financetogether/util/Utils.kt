// Utils.kt
package dev.financetogether.financetogether.util

import java.text.NumberFormat
import java.util.*

object Utils {
    fun formatCurrency(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
        format.maximumFractionDigits = if (amount % 1 == 0.0) 0 else 2
        format.currency = Currency.getInstance("EUR")
        return format.format(amount)
    }
}
