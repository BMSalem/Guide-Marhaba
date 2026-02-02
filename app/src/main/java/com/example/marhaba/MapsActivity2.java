package com.example.marhaba;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.marhaba.Domains.AttractionDomain;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.marhaba.databinding.ActivityMaps2Binding;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private AttractionDomain attr;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMaps2Binding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity2.this, AttractionDetails.class);
                intent.putExtra("attraction", attr);
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
        LatLng pos;
        double lat, lon;
        Intent intent = getIntent();
        if (intent.getSerializableExtra("attr") instanceof AttractionDomain) {
            attr = (AttractionDomain) intent.getSerializableExtra("attr");
            lat = attr.getLocation().get("latitude");
            lon = attr.getLocation().get("longitude");
            pos = new LatLng(lat, lon);mMap.addMarker(new MarkerOptions().position(pos).title(attr.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));
        } else {
            pos = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(pos).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));
        }
    }
}