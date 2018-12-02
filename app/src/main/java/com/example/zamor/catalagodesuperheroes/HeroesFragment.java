package com.example.zamor.catalagodesuperheroes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zamor.catalagodesuperheroes.ListaMarvel.ListaCreadaCero;
import com.example.zamor.catalagodesuperheroes.ListaMarvel.ListaDescripcion;
import com.example.zamor.catalagodesuperheroes.MarvelAPI.Constants;
import com.example.zamor.catalagodesuperheroes.MarvelAPI.RestApiAdapter;
import com.example.zamor.catalagodesuperheroes.MarvelAPI.Service;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeroesFragment extends Fragment {
    ImageView prueba;
    static ArrayList<Personaje> listaDePersonajesHeroesDescripcion;

    ArrayList<String> busquedaDescripcion;
    ArrayList<Personaje> personajeCero;


    private static  ArrayList<Personaje> listaFinal;

    ListaDescripcion ld;
    ListaCreadaCero lcc;
    String iron = "Iron Man";
    String hulk = "Hulk";
    //TextView tvJson;

    public RecyclerView recyclerView;

    public AdaptadorRecyclerView adaptadorRecyclerView;

    public HeroesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_heroes,container,false);

        ld = new ListaDescripcion();
        lcc = new ListaCreadaCero();

        listaDePersonajesHeroesDescripcion = new ArrayList<>();
        listaFinal = new ArrayList<>();

        personajeCero = new ArrayList<>();

        personajeCero = lcc.creaPersonajesHeroes();


        //tvJson = (TextView) view.findViewById(R.id.tvJson);

        busquedaDescripcion = ld.rellenaNombresHeroes();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adaptadorRecyclerView = new AdaptadorRecyclerView(getActivity());
        recyclerView.setAdapter(adaptadorRecyclerView);
        recyclerView.setHasFixedSize(true);








        RestApiAdapter restApiAdapter   = new RestApiAdapter();
        Service service           = restApiAdapter.getPersonajeService();
        Call<JsonObject> call;

        int i = 0;
        while(i< busquedaDescripcion.size()){
            String busqueda = busquedaDescripcion.get(i);
            int j= i;
            call = service.getDataPersonaje(busqueda, Constants.APIKEY, Constants.TS, Constants.HASH);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        if(response.isSuccessful()) {
                            String peliculas = "";
                            JSONObject jsonObject = new JSONObject(response.body().getAsJsonObject("data").toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            JSONObject personaje = jsonArray.getJSONObject(0);

                            String nombre = personaje.getString("name");
                            String descripcion = personaje.getString("description");

                            JSONObject image = personaje.getJSONObject("thumbnail");

                            String imagenPersonaje = image.getString("path") + "." + image.getString("extension");

                            adaptadorRecyclerView.adicionarLista(new Personaje(nombre,descripcion,peliculas,imagenPersonaje));


                            //listaDePersonajesHeroesDescripcion.add(new Personaje(nombre, descripcion, peliculas, imagenPersonaje));
                        }else{
                            //tvJson.setText("error masivo ");
                        }


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        //tvJson.setText("error " + listaDePersonajesHeroesDescripcion.get(7).getName());
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    //tvJson.setText("falla " + " " + t.getMessage());
                }
            });
            ++i;
        }

        //imagenPrueba = (ImageView) findViewById(R.id.imagenPrueba);

        //tvJson.setText(listaDePersonajesHeroesDescripcion.size());

        /*if(listaDePersonajesHeroesDescripcion.size() != 0){
            String url = listaDePersonajesHeroesDescripcion.get(0).getImagen();
            Glide.with(this)
                    .load(url)
                    .into(imagenPrueba);
        }*/

        //tvJson.setText(""+ contador);


        return view;
    }

}
