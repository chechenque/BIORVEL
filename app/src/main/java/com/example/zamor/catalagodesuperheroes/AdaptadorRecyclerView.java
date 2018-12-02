package com.example.zamor.catalagodesuperheroes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorRecyclerView extends RecyclerView.Adapter<AdaptadorRecyclerView.ViewHolderDatos>{
    ArrayList<Personaje> listaPersonajes;
    Context context;

    public AdaptadorRecyclerView(Context context){
        this.context = context;
        listaPersonajes = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list,null,false);
        return new ViewHolderDatos(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int position) {
        Personaje p = listaPersonajes.get(position);
        Glide.with(context)
                .load(p.getImagen())
                .into(viewHolderDatos.imagenPersonaje);
        viewHolderDatos.textoPersonaje.setText(listaPersonajes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listaPersonajes.size();
    }

    public void adicionarLista(Personaje personaje){
        listaPersonajes.add(personaje);
        notifyDataSetChanged();
    }



    public static class ViewHolderDatos extends RecyclerView.ViewHolder {


        ImageView imagenPersonaje;
        TextView textoPersonaje;
        CardView cartaPersonaje;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            imagenPersonaje = (ImageView) itemView.findViewById(R.id.foto_personaje);
            textoPersonaje = (TextView) itemView.findViewById(R.id.nombre_personaje);
            cartaPersonaje = itemView.findViewById(R.id.carta_personaje);
        }
    }
}


