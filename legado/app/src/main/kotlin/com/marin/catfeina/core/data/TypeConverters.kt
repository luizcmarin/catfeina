// =============================================================================
// Arquivo: core/data/TypeConverters.kt
// Descrição: Conversores de tipo para o banco de dados Room.
// =============================================================================
package com.marin.catfeina.core.data

import androidx.room.TypeConverter
import com.marin.catfeina.core.CategoriaPoesiaEnum

class Converters {
    @TypeConverter
    fun fromCategoriaPoesiaEnum(value: CategoriaPoesiaEnum): String {
        return value.name
    }

    @TypeConverter
    fun toCategoriaPoesiaEnum(value: String): CategoriaPoesiaEnum {
        return try {
            CategoriaPoesiaEnum.valueOf(value)
        } catch (e: IllegalArgumentException) {
            // Caso um valor desconhecido seja encontrado no banco,
            // retorna um valor padrão para evitar crashes.
            // Isso pode acontecer se o JSON mudar e o banco não for limpo.
            CategoriaPoesiaEnum.POESIA
        }
    }
}
