package com.example.zamor.catalagodesuperheroes;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import android.os.Bundle;
import android.view.MenuInflater;
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
    private static final String TAG = "Main Activity";
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

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private FragmentAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewPager = (ViewPager) findViewById(R.id.container);
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(mViewPager);

        listaDePersonajesHeroesDescripcion = new ArrayList<>();
        //tvJson = (TextView) findViewById(R.id.tvJson);


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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        if(toolbar != null ){
            setSupportActionBar(toolbar);
        }

        setupViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager){

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), agregaFragments());
        viewPager.setAdapter(adapter);

        tabLayout.getTabAt(0).setText("Heroes");
        tabLayout.getTabAt(1).setText("Villanos");


    }

    private ArrayList<Fragment> agregaFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new HeroesFragment());
        fragments.add(new VillanosFragment());
        return  fragments;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}