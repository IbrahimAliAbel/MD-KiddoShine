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

        // Definisikan objek MIGRATION_1_2 di dalam companion object atau di dalam kelas ini
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Menambah kolom baru dengan nilai default untuk new_column
                database.execSQL("ALTER TABLE anak_table ADD COLUMN new_column TEXT DEFAULT 'unknown'")

                // Menambah kolom usia dan lingkarKepala dengan nilai default
                database.execSQL("ALTER TABLE anak_table ADD COLUMN usia TEXT DEFAULT 'undefined'")
                database.execSQL("ALTER TABLE anak_table ADD COLUMN lingkarKepala TEXT DEFAULT 'undefined'")

                // Memastikan kolom yang ada tetap sesuai
                database.execSQL("UPDATE anak_table SET usia = 'undefined' WHERE usia IS NULL")
                database.execSQL("UPDATE anak_table SET lingkarKepala = 'undefined' WHERE lingkarKepala IS NULL")
                database.execSQL("ALTER TABLE anak_table ADD COLUMN photoPath TEXT")
                database.execSQL("ALTER TABLE anak_table ADD COLUMN tanggalLahir TEXT DEFAULT 'unknown'")
            }
        }

        fun getDatabase(context: Context): AnakDatabase {
            //context.deleteDatabase("anak_database") // Hapus database lama

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