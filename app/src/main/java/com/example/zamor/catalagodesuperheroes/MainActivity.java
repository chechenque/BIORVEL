package com.example.zamor.catalagodesuperheroes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    ArrayList<Personaje> listaDePersonajesHeroesDescripcion;
    ArrayList<Personaje> listaDePErsonajesHeroesSinDescripcion;

    ArrayList<Personaje> listaDePersonajesVillanosDescripcion;
    ArrayList<Personaje> listaDePErsonajesVillanosSinDescripcion;

    ArrayList<Personaje> listaDePersonajesHeroesCero;
    ArrayList<Personaje> listaDePErsonajesVillanosCero;

    ArrayList<Personaje> listaTotalHeroes;
    ArrayList<Personaje> listaTotalVillanos;

    String iron = "iron man";
    String hulk = "Hulk";
    TextView tvJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaDePersonajesHeroesDescripcion = new ArrayList<>();
        tvJson = (TextView) findViewById(R.id.tvJson);


        RestApiAdapter restApiAdapter   = new RestApiAdapter();
        Service service           = restApiAdapter.getPersonajeService();
        Call<JsonObject> call           = service.getDataPersonaje("Hulk",Constants.APIKEY,Constants.TS,Constants.HASH);

        //tvJson.setText(call.toString());

       call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String[] peliculas = new String[10];
                    JSONObject jsonObject   = new JSONObject(response.body().getAsJsonObject("data").toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject personaje = jsonArray.getJSONObject(0);

                    String nombre        = personaje.getString("name");
                    String descripcion   = personaje.getString("description");

                    JSONObject image = personaje.getJSONObject("thumbnail");

                    String imagenPersonaje = image.getString("path") + "." +  image.getString("extension");

                    listaDePersonajesHeroesDescripcion.add(new Personaje(nombre,descripcion,peliculas,imagenPersonaje));

                    //tvJson.setText(descripcion);
                    //tvJson.setText("exito");


                    } catch (JSONException e1) {
                    e1.printStackTrace();
                    tvJson.setText("error");
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {        tvJson.setText("falla " +" " +  t.getMessage());}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_busqueda,menu);
        MenuItem item = menu.findItem(R.id.busqueda_heroes);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
