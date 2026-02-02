package com.example.marhaba;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.marhaba.Domains.AttractionDomain;
import com.example.marhaba.Domains.DestinationDomain;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.marhaba.databinding.ActivityMapsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private DestinationDomain city;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MapsActivity.this, City_Activity.class);
                    intent.putExtra("city", city);
                    startActivity(intent);
                    finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        try {
            LatLng pos;
            double lat, lon;
            Intent intent = getIntent();
            if (intent.getSerializableExtra("city") instanceof DestinationDomain) {
                city = (DestinationDomain) intent.getSerializableExtra("city");
                lat = city.getLocation().get("latitude");
                lon = city.getLocation().get("longitude");
                pos = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(pos).title(city.getTitle()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 12));
            } else {
                pos = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(pos).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));
            }
            // Add a marker in Sydney and move the camera
        }
        catch (Exception e){
            String err = e.getMessage();
            Toast.makeText(MapsActivity.this, err.split("double")[1], Toast.LENGTH_LONG).show();
        }

    }
}