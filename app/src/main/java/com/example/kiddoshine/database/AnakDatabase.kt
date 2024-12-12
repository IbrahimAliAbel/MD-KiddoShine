package com.example.kiddoshine.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Anak::class], version = 2, exportSchema = false)
abstract class AnakDatabase : RoomDatabase() {
    abstract fun anakDao(): AnakDao

    companion object {
        @Volatile
        private var INSTANCE: AnakDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE anak_table ADD COLUMN new_column TEXT DEFAULT 'unknown'")


                database.execSQL("ALTER TABLE anak_table ADD COLUMN usia TEXT DEFAULT 'undefined'")
                database.execSQL("ALTER TABLE anak_table ADD COLUMN lingkarKepala TEXT DEFAULT 'undefined'")

                database.execSQL("UPDATE anak_table SET usia = 'undefined' WHERE usia IS NULL")
                database.execSQL("UPDATE anak_table SET lingkarKepala = 'undefined' WHERE lingkarKepala IS NULL")
                database.execSQL("ALTER TABLE anak_table ADD COLUMN photoPath TEXT")
                database.execSQL("ALTER TABLE anak_table ADD COLUMN tanggalLahir TEXT DEFAULT 'unknown'")
            }
        }

        fun getDatabase(context: Context): AnakDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnakDatabase::class.java,
                    "anak_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}