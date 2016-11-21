package lab2.com.lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Extra.DistanceClass;


public class BuildingDetail extends AppCompatActivity {



   DistanceClass DC = DistanceClass.getObj();
    Double Lat;
    Double Long;
    String BuildingName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail);

        Intent intent = getIntent();

        BuildingName=intent.getStringExtra("BuildingName");
        TextView BN = (TextView) findViewById(R.id.buildingName);
        TextView BA = (TextView) findViewById(R.id.buildingAddress);
        ImageView iv=(ImageView) findViewById(R.id.Buildingimage);
        Button SV= (Button) findViewById(R.id.button);
        Button back= (Button) findViewById(R.id.back_button);

        TextView Time = (TextView) findViewById(R.id.Time);
        TextView Distance = (TextView) findViewById(R.id.Distance);

        switch (BuildingName)
        {
            case "King library" :BN.setText("King library");
                BA.setText("Dr. Martin Luther King, Jr. library, 150 East San Fernando Street, San Jose, CA 95112\n");
                iv.setImageResource(R.drawable.library4);
                Lat=37.335785;
                Long=-121.885790;
                break;
            case "Engineering Building": BN.setText("Engineering Building");
                BA.setText("San José State University Charles W. Davidson College of Engineering, 1 Washington Square, San Jose, CA 95112\n");
                iv.setImageResource(R.drawable.engg_building);
                Lat=37.337404;
                Long=-121.882614;
                break;
            case "Yoshihiro Uchida Hall":
                BN.setText("Yoshihiro Uchida Hall");
                BA.setText("Yoshihiro Uchida Hall, San Jose, CA 95112\n");
                iv.setImageResource(R.drawable.yuh);
                Lat=37.333362;
                Long=-121.884132;
                break;
            case "Student Union":
                BN.setText("Student Union");
                BA.setText("Student Union Building, San Jose, CA 95112\n");
                iv.setImageResource(R.drawable.student_union);
                Lat=37.337247;
                Long=-121.882780;
                break;
            case "BBC":
                BN.setText("BBC");
                BA.setText("Boccardo Business Complex, San Jose, CA 95112\n");
                iv.setImageResource(R.drawable.bbc);
                Lat=37.336855;
                Long=-121.878296;
                break;
            case "South Parking":
                BN.setText("South Parking Garage");
                BA.setText("San Jose State University South Garage, 330 South 7th Street, San Jose, CA 95112\n");
                iv.setImageResource(R.drawable.south_parking_garage);
                Lat=37.332687;
                Long=-121.880516;
                break;
        }

        Log.v("Time in Activity",String.valueOf(DistanceClass.getObj().getTime()));
        Log.v("Distance in Activity",String.valueOf(DistanceClass.getObj().getDistance()));
        if(DC != null) {
            String s1="";
            if(DC.getTime()!=null) {
                s1 = String.valueOf((int) (DC.getTime() / 60) + " hr " + (int) (DC.getTime() % 60) + " mins");
            }
                Time.setText(s1);
            String s2="";
            if(DC.getDistance()!=null) {
                s2 = String.valueOf((int) (DC.getDistance() / 1000) + " kilometers " + (int) (DC.getDistance() % 1000) + " meters");
            }
            Distance.setText(s2);
        }
        SV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent i=new Intent(BuildingDetail.this, StreetView.class);
                i.putExtra("BN",BuildingName);
                i.putExtra("Lat",Lat);
                i.putExtra("Long",Long);
                finish();
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                finish();
                Intent i=new Intent(BuildingDetail.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

}
