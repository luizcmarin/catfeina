// =============================================================================
// Arquivo: com.marin.catfeina.core.data.AppDatabase.kt
// Descrição: Classe principal do Room que define o banco de dados do aplicativo.
// =============================================================================
package com.marin.catfeina.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marin.catfeina.core.data.daos.AtelierDao
import com.marin.catfeina.core.data.daos.PersonagemDao
import com.marin.catfeina.core.data.daos.PoesiaDao
import com.marin.catfeina.core.data.daos.InformativoDao
import com.marin.catfeina.core.data.entities.AtelierEntity
import com.marin.catfeina.core.data.entities.PersonagemEntity
import com.marin.catfeina.core.data.entities.PoesiaEntity
import com.marin.catfeina.core.data.entities.PoesiaNotaEntity
import com.marin.catfeina.core.data.entities.InformativoEntity

/**
 * A classe de banco de dados principal para o aplicativo, usando Room.
 *
 * Lista todas as entidades que fazem parte do banco de dados e fornece
 * acesso aos DAOs correspondentes.
 *
 * @property version A versão do esquema do banco de dados. Deve ser incrementada
 *                   sempre que o esquema for alterado.
 */
@Database(
    entities = [
        PoesiaEntity::class,
        PoesiaNotaEntity::class,
        PersonagemEntity::class,
        InformativoEntity::class,
        AtelierEntity::class
    ],
    version = 1,
    exportSchema = true
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Fornece o DAO para operações relacionadas a poesias.
     */
    abstract fun poesiaDao(): PoesiaDao

    /**
     * Fornece o DAO para operações relacionadas a personagens.
     */
    abstract fun personagemDao(): PersonagemDao

    /**
     * Fornece o DAO para operações relacionadas a textos gerais.
     */
    abstract fun informativoDao(): InformativoDao

    /**
     * Fornece o DAO para operações relacionadas ao atelier.
     */
    abstract fun atelierDao(): AtelierDao

}