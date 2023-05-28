package xyz.android.appbook_sqlite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import xyz.android.appbook_sqlite.adapter.LibroAdapter;
import xyz.android.appbook_sqlite.dao.LibroDao;
import xyz.android.appbook_sqlite.db.DatabaseHelper;
import xyz.android.appbook_sqlite.enums.MensajeEnum;
import xyz.android.appbook_sqlite.model.Libro;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton btnAgregar;
    LinearLayout no_data;

    DatabaseHelper databaseHelper;
    LibroDao libroDao;
    LibroAdapter libroAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = DatabaseHelper.getInstance(this);
        libroDao = new LibroDao(databaseHelper.getWritableDatabase());

        recyclerView = findViewById(R.id.recyclerView);
        btnAgregar = findViewById(R.id.btnAddLibro);
        no_data = findViewById(R.id.empty_lista);


        loadTareas();

        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarLibroActivity.class);
            startActivity(intent);
        });
    }

    // este código verifica si el resultado proviene de una actividad específica
    // (identificada por el código de solicitud) y,
    // en caso afirmativo, recrea la actividad principal para actualizar su estado.
    // **Reactiva cuando actuliza datos desde activity updateTarea
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    // Para el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadTareas() {
        List<Libro> libros = libroDao.getAll();
        if (libros.isEmpty()) {
            no_data.setVisibility(View.VISIBLE);
        }

        libroAdapter = new LibroAdapter(MainActivity.this, this, libros);
        recyclerView.setAdapter(libroAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void confirmDialog() {
      var builder =  new MaterialAlertDialogBuilder(this)
              .setTitle("¿Eliminar todo?")
              .setMessage(MensajeEnum.ELIMINAR_TODO.getValue())
              .setIcon(R.drawable.ic_delete_sweep_r)
              .setNeutralButton("Cancelar", (dialog, which) -> {})
              .setPositiveButton("Sí", (dialog, which) -> {
                  var estado = libroDao.deleteAll();
                  if (estado == -1) {
                      return;
                  }

                  // Actualizar la actividad
                  Intent intent = new Intent(MainActivity.this, MainActivity.class);
                  startActivity(intent);
                  finish();
              });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}