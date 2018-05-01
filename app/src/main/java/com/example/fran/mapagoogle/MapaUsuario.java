package com.example.fran.mapagoogle;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;


import com.example.fran.mapagoogle.GeneratoRetrofit.RetrofitServiceGenerator;
import com.example.fran.mapagoogle.RestClient.RetrofitService;
import com.example.fran.mapagoogle.entidade.Oficina;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaUsuario extends  SupportMapFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;

    private ProgressDialog progress;
    private GoogleApiClient googleApiClient;
    private List<Oficina> listaOficinas;
    private Marker currentLocationMaker;
    private Marker locationOficinasMaker;
    private LatLng currentLocationLatLong;
    private List<Oficina> listOficinasMap;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        listaOficinas = new ArrayList<>();
        listOficinasMap = retornaOficinas();



        this.callConnection();
        this.startGettingLocations();
        //retornaOficinas();
      
        getMapAsync(this);

        //Chamando o progress
        progress = new ProgressDialog(getContext());
        progress.setTitle("Aguarde..... ");
        progress.show();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


   /*     mMap.getUiSettings().setZoomControlsEnabled(true);
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
*/


    }


    private synchronized void callConnection() {
        Log.i("LOG", "Entrou no callConnection");
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

       /* if (googleApiClient.isConnected()) {
            Log.i("LOG", "googleClient connected");

        }else{
            Log.i("LOG", "googleClient not1 connected");
            googleApiClient.connect();
        }*/
    }



    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);

        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;

    }

    @Override
    public void onPause() {
        super.onPause();
      //getActivity().getFragmentManager().popBackStack();

    }

    public List<Oficina> retornaOficinas() {

        RetrofitService service = RetrofitServiceGenerator.createService(RetrofitService.class);

        Call<List<Oficina>> response = service.getOficinas();

        response.enqueue(new Callback<List<Oficina>>() {
            @Override
            public void onResponse(Call<List<Oficina>> call, Response<List<Oficina>> response) {

                if (response.isSuccessful()){
                    listaOficinas = response.body();
                    Toast.makeText(getContext(),"Chamada das oficinas OK",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Oficina>> call, Throwable t) {

            }
        });

        return  listaOficinas;

    }

    @Override
    public void onLocationChanged(Location location) {
        //pegar todas as oficinas
        listOficinasMap = retornaOficinas();

        //Verifica se a lista estar vazia(So para testar)
        if(listOficinasMap.isEmpty()){
            Toast.makeText(getContext(),"Não foi possivel buscar as oficinas",Toast.LENGTH_LONG).show();
            progress.dismiss();

        }else {
            Toast.makeText(getContext(), "Oficinas encontradas", Toast.LENGTH_LONG).show();

            for(Oficina oficina : listOficinasMap){

                //So teste
                // Toast.makeText(getContext(), "Oficina = "+oficina.getRua(), Toast.LENGTH_SHORT).show();

                LatLng posicao = getLocationFromAddress(getContext(),
                        oficina.getRua()+", "+oficina.getNumero()+", "+oficina.getBairro()+", "+"Natal - RN,"+oficina.getCep()+", "+"Brasil");


                //add market
                mMap.addMarker(new MarkerOptions().position(posicao).title(oficina.getNome()));





            }
        }


        if (currentLocationMaker != null) {
            currentLocationMaker.remove();
        }
        //Add marker
        currentLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        Log.i("LOG","Lat "+location.getLatitude());
        Log.i("LOG","Lang "+location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        if( currentLocationLatLong != null && mMap!=null && location!=null && !location.equals("")){
            markerOptions.position(currentLocationLatLong);
            markerOptions.title("Localização atual");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            currentLocationMaker = mMap.addMarker(markerOptions);
            progress.dismiss();
            
            //Move to new location
            CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(currentLocationLatLong).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Toast.makeText(getContext(), "Localização atualizada", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getContext(), "Não foi possivel obter sua posição", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

      /*  LocationData locationData = new LocationData(location.getLatitude(), location.getLongitude());
        mDatabase.child("location").child(String.valueOf(new Date().getTime())).setValue(locationData);
*/

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }


    //Code para permissão

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("GPS desativado!");
        alertDialog.setMessage("Ativar GPS?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    private void startGettingLocations() {

        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean canGetLocation = true;
        int ALL_PERMISSIONS_RESULT = 101;
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;// Distance in meters
        long MIN_TIME_BW_UPDATES = 1000 * 10;// Time in milliseconds

        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;

        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);

        //Check if GPS and Network are on, if not asks the user to turn on
        if (!isGPS && !isNetwork) {
            showSettingsAlert();
        } else {
            // check permissions

            // check permissions for later versions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    canGetLocation = false;
                }
            }
        }


        //Checks if FINE LOCATION and COARSE Location were granted
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getContext(), "Permissão negada", Toast.LENGTH_SHORT).show();
            return;
        }

        //Starts requesting location updates
        if (canGetLocation) {
            if (isGPS) {
                lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            } else if (isNetwork) {
                // from Network Provider

                lm.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            }
        } else {
            Toast.makeText(getContext(), "Não é possível obter a localização", Toast.LENGTH_SHORT).show();
        }
    }




}







