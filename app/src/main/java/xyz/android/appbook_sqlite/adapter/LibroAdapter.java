package xyz.android.appbook_sqlite.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xyz.android.appbook_sqlite.DetalleLibroActivity;
import xyz.android.appbook_sqlite.R;
import xyz.android.appbook_sqlite.enums.LibroEnum;
import xyz.android.appbook_sqlite.model.Libro;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.MyViewHolder>{
    private final Context context;
    private final List<Libro> libros;
    private final Activity activity;
    private Libro libro;

    public LibroAdapter(Activity activity, Context context, List<Libro> libros) {
        this.context = context;
        this.libros = libros;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_libro, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (libros.size() > 0) {
            libro = libros.get(position);
            holder.idTextView.setText(String.valueOf(libro.getId()));
            holder.tituloTextView.setText(libro.getTitulo());
            holder.autorTextView.setText(libro.getAutor());
            holder.paginasTextView.setText(libro.getPaginas());

            holder.mainLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetalleLibroActivity.class);
                intent.putExtra(LibroEnum.KEY_NAME.getValue(), libro);
                activity.startActivityForResult(intent, 1);
            });
        }
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView, tituloTextView, autorTextView, paginasTextView;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idLibroTxt);
            tituloTextView = itemView.findViewById(R.id.tituloLibroTxt);
            autorTextView = itemView.findViewById(R.id.autorLibroTxt);
            paginasTextView = itemView.findViewById(R.id.paginasLibrioTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            Animation teanslate_ani = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(teanslate_ani);
        }
    }
}
