package xyz.android.appbook_sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import xyz.android.appbook_sqlite.enums.LibroEnum;

public class DatabaseHelper extends SQLiteOpenHelper {
    // El patrón de diseño Singleton
    private static final String DATABASE_NAME = "Biblioteca.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
        Al hacer que el método getInstance(Context context) sea sincronizado utilizando la palabra clave synchronized,
        se asegura que solo un hilo pueda acceder a este método a la vez.
        Esto es importante para evitar problemas de concurrencia si varios hilos intentan obtener una instancia al mismo tiempo.
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryLibro = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER);",
                LibroEnum.TABLE_NAME.getValue(),
                LibroEnum.COL_ID.getValue(),
                LibroEnum.COL_TITULO.getValue(),
                LibroEnum.COL_AUTOR.getValue(),
                LibroEnum.COL_PAGINA.getValue()
        );
        db.execSQL(queryLibro);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No se realiza ninguna operación en este método
    }
}
