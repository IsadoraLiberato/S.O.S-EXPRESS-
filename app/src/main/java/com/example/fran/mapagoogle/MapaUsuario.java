package com.example.fran.mapagoogle;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.fran.mapagoogle.GeneratoRetrofit.RetrofitServiceGenerator;
import com.example.fran.mapagoogle.RestClient.RetrofitService;
import com.example.fran.mapagoogle.entidade.Oficina;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaUsuario extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ProgressDialog progress;
    private List<Oficina> listaOficinas;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaOficinas = new ArrayList<>();

        getMapAsync(this);
        retornaOficinas();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(12);

        for(Oficina oficina : retornaOficinas()){

            // Add a marker in Sydney and move the camera
            LatLng posicao = getLocationFromAddress(getContext(),
                    oficina.getRua()+", "+oficina.getNumero()+", "+oficina.getBairro()+", "+"Natal - RN,"+oficina.getCep()+", "+"Brasil");

            mMap.addMarker(new MarkerOptions().position(posicao).title(oficina.getNome()));
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(12);
        // Add a marker in Sydney and move the camera
        LatLng posicao = getLocationFromAddress(getContext(), "Av. Alm. Alexandrino de Alencar, 708 - Alecrim, Natal - RN, 59030-350, Brasil");
        mMap.addMarker(new MarkerOptions().position(posicao).title("Estacio"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicao));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }


    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }

    public List<Oficina> retornaOficinas() {

        RetrofitService service = RetrofitServiceGenerator.createService(RetrofitService.class);

        Call<List<Oficina>> response = service.getOficinas();

        response.enqueue(new Callback<List<Oficina>>() {
            @Override
            public void onResponse(Call<List<Oficina>> call, Response<List<Oficina>> response) {

                if (response.isSuccessful()){
                    listaOficinas = response.body();
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Dados");
                    alert.setMessage("Nome oficina: "+listaOficinas.get(8).getNome()
                       +"\n Rua: "+listaOficinas.get(8).getRua());
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<List<Oficina>> call, Throwable t) {

            }
        });

        return  listaOficinas;

    }
}
