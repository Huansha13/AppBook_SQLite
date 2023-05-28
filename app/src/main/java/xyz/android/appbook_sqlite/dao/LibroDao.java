package xyz.android.appbook_sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xyz.android.appbook_sqlite.enums.LibroEnum;
import xyz.android.appbook_sqlite.model.Libro;

public class LibroDao {
    private final SQLiteDatabase database;

    public LibroDao(SQLiteDatabase database) {
        this.database = database;
    }


    public long insert(Libro libro) {
        ContentValues values = new ContentValues();
        values.put(LibroEnum.COL_TITULO.getValue(), libro.getTitulo());
        values.put(LibroEnum.COL_AUTOR.getValue(), libro.getAutor());
        values.put(LibroEnum.COL_PAGINA.getValue(), libro.getPaginas());

        /**
         * SQL requiere al menos un nombre de columna para insertar una fila
         * y el parámetro nullColumnHack se utiliza para insertar un valor NULL en caso de valores vacíos.
         */
        return database.insert(LibroEnum.TABLE_NAME.getValue(), null, values);
    }

    public long update(Libro libro) {
        ContentValues values = new ContentValues();
        values.put(LibroEnum.COL_TITULO.getValue(), libro.getTitulo());
        values.put(LibroEnum.COL_AUTOR.getValue(), libro.getAutor());
        values.put(LibroEnum.COL_PAGINA.getValue(), libro.getPaginas());

        String whereClause = String.format("%s=?", LibroEnum.COL_ID.getValue());
        String[] whereArgs = {String.valueOf(libro.getId())};
        return database.update(LibroEnum.TABLE_NAME.getValue(), values, whereClause, whereArgs);
    }

    public long deleteById(String id) {
        String whereClause = String.format("%s=?", LibroEnum.COL_ID.getValue());
        String[] whereArgs = {id};
        return database.delete(LibroEnum.TABLE_NAME.getValue(), whereClause, whereArgs);
    }

    public List<Libro> getAll() {
        List<Libro> libros = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", LibroEnum.TABLE_NAME.getValue());

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(LibroEnum.COL_ID.getValue());
                int tituloIndex = cursor.getColumnIndex(LibroEnum.COL_TITULO.getValue());
                int autorIndex = cursor.getColumnIndex(LibroEnum.COL_AUTOR.getValue());
                int paginasIndex = cursor.getColumnIndex(LibroEnum.COL_PAGINA.getValue());

                int id = cursor.getInt(idIndex);
                String titulo = cursor.getString(tituloIndex);
                String autor = cursor.getString(autorIndex);
                String paginas = cursor.getString(paginasIndex);

                Libro libro = new Libro(id, titulo, autor, paginas);
                libros.add(libro);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return libros;

    }

    public long deleteAll() {
        //String query = String.format("DELETE FROM %s", LibroEnum.TABLE_NAME.getValue());
       return database.delete(LibroEnum.TABLE_NAME.getValue(), null, null);
    }


}
