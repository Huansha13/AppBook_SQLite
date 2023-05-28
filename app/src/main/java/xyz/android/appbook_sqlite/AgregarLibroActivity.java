package xyz.android.appbook_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import xyz.android.appbook_sqlite.dao.LibroDao;
import xyz.android.appbook_sqlite.db.DatabaseHelper;
import xyz.android.appbook_sqlite.enums.MensajeEnum;
import xyz.android.appbook_sqlite.model.Libro;

public class AgregarLibroActivity extends AppCompatActivity {
    Libro libro;
    DatabaseHelper databaseHelper;
    LibroDao libroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_libro);

        databaseHelper = DatabaseHelper.getInstance(this);
        libroDao = new LibroDao(databaseHelper.getWritableDatabase());

        TextInputLayout tituloTextInput = findViewById(R.id.tituloEditText);
        TextInputLayout autorTexttInput = findViewById(R.id.autorEditText);
        TextInputLayout numPaginasTextInput = findViewById(R.id.numPaginasEditText);
        MaterialButton materialButton = findViewById(R.id.addLibroBtn);

        materialButton.setOnClickListener(v -> {
            try {

                EditText tituloEditText = tituloTextInput.getEditText();
                EditText autorEditText = autorTexttInput.getEditText();
                EditText paginasEditText = numPaginasTextInput.getEditText();

                assert tituloEditText != null && autorEditText != null && paginasEditText != null;

                String titulo = tituloEditText.getText().toString();
                String autor = autorEditText.getText().toString();
                String paginas = paginasEditText.getText().toString();

                if (!titulo.isEmpty() && !autor.isEmpty() && !paginas.isEmpty()) {
                    libro = new Libro(titulo, autor, paginas);
                    var estado = libroDao.insert(libro);
                    if (estado == -1) {
                        Toast.makeText(AgregarLibroActivity.this, MensajeEnum.ERROR_REGISTRO.getValue(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(AgregarLibroActivity.this, MensajeEnum.OK_REGISTRO.getValue(), Toast.LENGTH_SHORT).show();

                } else {
                    throw new IllegalArgumentException(MensajeEnum.CAMPO_IMPOCOMPLETO.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AgregarLibroActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }
}