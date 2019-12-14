package it.dikbudsit.stb.myappkey.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDbase : RoomDatabase() {
    abstract fun noteDao(): NoteDao?
    companion object{
        @Volatile
        private var noteRoomInstance: NoteRoomDbase? = null

        fun getDatabse(context: Context): NoteRoomDbase? {
            if (noteRoomInstance == null) {
                synchronized(NoteRoomDbase::class.java) {
                    if (noteRoomInstance == null) {
                        noteRoomInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                NoteRoomDbase::class.java, "note_database"
                        ).build()
                    }
                }
            }
            return noteRoomInstance
        }
  }
}