package xyz.android.appbook_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import xyz.android.appbook_sqlite.dao.LibroDao;
import xyz.android.appbook_sqlite.db.DatabaseHelper;
import xyz.android.appbook_sqlite.enums.LibroEnum;
import xyz.android.appbook_sqlite.enums.MensajeEnum;
import xyz.android.appbook_sqlite.model.Libro;

public class DetalleLibroActivity extends AppCompatActivity {

    TextInputLayout detalleTituloInput, detalleAutorInput, detalleNumPaginaInput;
    MaterialButton actualizarBtn, eliminarBtn;
    Libro libro;
    DatabaseHelper databaseHelper;
    LibroDao libroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        databaseHelper = DatabaseHelper.getInstance(this);
        libroDao = new LibroDao(databaseHelper.getWritableDatabase());

        detalleTituloInput = findViewById(R.id.detalleInputTitulo);
        detalleAutorInput = findViewById(R.id.detalleInputAutor);
        detalleNumPaginaInput = findViewById(R.id.detalleInputPaginas);
        actualizarBtn = findViewById(R.id.detalleBtnUpdate);
        eliminarBtn = findViewById(R.id.detalleBtnEliminarLibro);

        Intent intent = getIntent();
        libro = (Libro) intent.getSerializableExtra(LibroEnum.KEY_NAME.getValue());
        if (libro.getTitulo() != null && libro.getAutor() != null && libro.getPaginas() != null) {
            detalleTituloInput.getEditText().setText(libro.getTitulo());
            detalleAutorInput.getEditText().setText(libro.getAutor());
            detalleNumPaginaInput.getEditText().setText(libro.getPaginas());
        }

        actualizarBtn.setOnClickListener(v -> {
            String nuevoTitulo = detalleTituloInput.getEditText().getText().toString();
            String nuevoAuto = detalleAutorInput.getEditText().getText().toString();
            String nuevoPaginas = detalleNumPaginaInput.getEditText().getText().toString();

            Libro libroUpdate = new Libro(libro.getId(), nuevoTitulo, nuevoAuto, nuevoPaginas);
            var estado = libroDao.update(libroUpdate);
            if (estado == -1) {
                Toast.makeText(DetalleLibroActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(DetalleLibroActivity.this, "Libro Actualizado", Toast.LENGTH_SHORT).show();

        });

        eliminarBtn.setOnClickListener(v -> {
            confirmDialog();
        });
    }

    void confirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle(String.format("¿Eliminar %s?", libro.getTitulo()))
                .setMessage(MensajeEnum.ELIMINAR_ITEM.getValue())
                .setNegativeButton("No", ((dialog, which) -> {}))
                .setPositiveButton("Sí", (dialog, which) -> {
                    var estado = libroDao.deleteById(String.valueOf(libro.getId()));
                    if (estado == -1) {
                        return;
                    }

                    finish();
                })
                .show();
    }
}