/*
 * Arquivo: com.marin.catfeina.core.data.entities.PoesiaNotaEntity.kt
 * Descrição: Define a entidade Room para a tabela 'PoesiaNota', que armazena
 *            o estado de interação do usuário (favorito, lido) com cada poesia.
 */
package com.marin.catfeina.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "PoesiaNota",
    foreignKeys = [
        ForeignKey(
            entity = PoesiaEntity::class,
            parentColumns = ["id"],
            childColumns = ["poesiaId"],
            onDelete = ForeignKey.CASCADE // Crucial: Deleta a nota se a poesia for deletada
        )
    ]
)
data class PoesiaNotaEntity(
    @PrimaryKey
    @ColumnInfo(name = "poesiaId")
    val poesiaId: Long,

    @ColumnInfo(name = "ehFavorita", defaultValue = "0")
    val ehFavorita: Boolean,

    @ColumnInfo(name = "foiLida", defaultValue = "0")
    val foiLida: Boolean,

    @ColumnInfo(name = "dataFavoritado")
    val dataFavoritado: Long? = null,

    @ColumnInfo(name = "dataLeitura")
    val dataLeitura: Long? = null,

    @ColumnInfo(defaultValue = "")
    val notaUsuario: String? = null,
)
