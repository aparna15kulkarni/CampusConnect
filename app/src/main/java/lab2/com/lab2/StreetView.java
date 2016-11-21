package lab2.com.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

public class StreetView extends AppCompatActivity {

    Double Lat;
    Double Long;
    String BN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);

        final Intent intent = getIntent();

        Button back= (Button) findViewById(R.id.backButton);
        Lat=intent.getDoubleExtra("Lat",-1);
        Long=intent.getDoubleExtra("Long",-1);
        BN=intent.getStringExtra("BN");
        final LatLng Temp=new LatLng(Lat,Long);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        panorama.setPosition(Temp);
                    }
                });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i=new Intent(StreetView.this, BuildingDetail.class);
                i.putExtra("BuildingName",BN);
                finish();
                startActivity(i);
            }
        });
    }
}
