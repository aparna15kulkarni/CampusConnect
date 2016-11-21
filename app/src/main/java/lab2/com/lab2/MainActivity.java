package lab2.com.lab2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    final String API_KEY = "AIzaSyAxE0rsI3vx4rvcSu1yIpvDShCX7kDx5Kg";
    private static final int ACCESS_COARSE_LOCATION = 1;
    private static double CurrentLat;
    private static double CurrentLong;
    private static double DestLat;
    private static double DestLong;
    private static float PinX=0;
    private static float PinY=0;
    private static float currX=0;
    private static float currY=0;

    private Bitmap b3;
    private static DrawCanvas v1;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        v1=new DrawCanvas(MainActivity.this);

        setContentView(v1);

        getCurrentLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Log.d("In Text Search","In Text Search");
        Log.d("Val of query",query.toLowerCase());
        switch(query.toLowerCase())
        {
            case "king library":PinX= 150;
                                PinY=700;
                                break;
            case "engineering building":PinX= 850;
                PinY=720;
                break;
            case "yuh":PinX= 175;
                PinY=1300;
                break;
            case "student union":PinX= 903;
                PinY=950;
                break;
            case "bbc":PinX= 1200;
                PinY=1100;
                break;
            case "south parking garage":PinX= 544;
                PinY=1800;
                break;
            case "":
                PinX=0;
                PinY=0;
                v1.ResetParams();
                if(b3!=null)
                    v1.clearPin();
                break;
        }
        v1.SetParams(PinX,PinY);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.equals(""))
        {
            v1.ResetParams();
            if(b3!=null)
                v1.clearPin();

        }
        return false;
    }

    public void MapCurrToImage()
    {
        Location ULeft = new Location("");
        ULeft.setLatitude(37.335828);
        ULeft.setLongitude(-121.885993);
        Location LRight = new Location("");
        LRight.setLatitude(37.334548);
        LRight.setLongitude(-121.876520);
        Location current = new Location("");
        current.setLatitude(CurrentLat);
        current.setLongitude(CurrentLong);
        double hypo = ULeft.distanceTo(current);
        double bearing = ULeft.bearingTo(current);

        double currDistX = Math.cos(bearing* Math.PI / 180.0) * hypo;
        double totalHypo = ULeft.distanceTo(LRight);
        double totalDistX = totalHypo * Math.cos(ULeft.bearingTo(LRight)* Math.PI / 180.0);
        double currPixelX = currDistX / totalDistX * 493;
        currX=(int) ((1300/360.0) * (180 + CurrentLong));
        //currX=(float) Math.abs(currPixelX);

        double currDistY = Math.sin(bearing* Math.PI / 180.0) * hypo;
        double totalDistY = totalHypo * Math.sin(ULeft.bearingTo(LRight)* Math.PI / 180.0);
        double currPixelY = currDistY / totalDistY * 493;

        currY=(int) ((1460/180.0) * (90 - CurrentLat));

        //currY=(float) Math.abs(currPixelY);

        Log.d("In MapCurrToImage"," setting canvas to"+currX+" "+currY);
        v1.SetCurrentLoc(currX,currY);

    }
    public void getCurrentLocation()
    {
        LocationManager locationManager;
        locationManager = (LocationManager) MainActivity.this.getSystemService(LOCATION_SERVICE);
        boolean isGpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        try {
            Log.v("abc1","inside try");
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (isGpsOn && isNetworkOn && locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Log.v("In if of current","Get current Location");
                Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                CurrentLat=location.getLatitude();
                CurrentLong=location.getLongitude();
                MapCurrToImage();
                System.out.println("lat: "+String.valueOf(CurrentLat)+" Long: "+String.valueOf(CurrentLong));

            } else if (isNetworkOn) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                String bestProvider = locationManager.getBestProvider(new Criteria(), false);

            } else {
                Toast.makeText(MainActivity.this, "Cannot get Location! Check Network & GPS", Toast.LENGTH_SHORT).show();
            }
        }
        catch (SecurityException permissionException) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_COARSE_LOCATION);
            }
        }
///current location finished
    }

    public void CalculateDistanceAndTime()
   {

        //Replace with CurrLat and CurrLong
        String stringURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+CurrentLat+","+
                CurrentLong+"&destinations="+DestLat+","+DestLong;
        stringURL += "&mode=walking&language=fr-FR&avoid=tolls&key=" + API_KEY;
        Log.v("Dist URL:",stringURL);
        try {
            new DistanceCalculation(getApplicationContext()).execute(stringURL).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }


    public class DrawCanvas extends View
    {
        Paint p;
        private float PinPosX;
        private float PinPosY;
        float CurrPosX;
        float CurrPosY;
        Bitmap b1;


        public DrawCanvas(Context context)
        {
            super(context);
        }
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);

                p = new Paint();

                b1 = BitmapFactory.decodeResource(getResources(), R.drawable.campusmap);
                Bitmap scaled = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
                canvas.drawBitmap(scaled, 0, 0, p);

            if(PinPosX!=0 && PinPosY!=0)
            {
                Bitmap b2=BitmapFactory.decodeResource(getResources(), R.drawable.pin);
                Bitmap scaled2 = Bitmap.createScaledBitmap(b2, 150, 150, true);
                b3=scaled2;
                canvas.drawBitmap(b3, PinPosX, PinPosY, p);

            } else if(b3!=null) {
                Log.d("In b3 null","Reset Pin");
                b3.eraseColor(Color.TRANSPARENT);
            }
            if(CurrPosX!=0 && CurrPosY!=0)
            {
                Log.d("In canvas draw","Drawing circle at "+CurrPosX+" "+CurrPosY);
                p.setColor(Color.parseColor("#FF0000"));
                canvas.drawCircle(CurrPosX,CurrPosY,50,p);
            }

        }

        public void clearPin(){
            b3.eraseColor(Color.TRANSPARENT);
            invalidate();
        }
        public void ResetParams()
        {
            PinPosX=0;
            PinPosY=0;
            invalidate();
        }

        public void SetParams(float X, float Y)
        {
            PinPosX=X;
            PinPosY=Y;
            invalidate();
        }

        public void SetCurrentLoc(float currX,float currY)
        {
            CurrPosX=0;
            CurrPosY=0;
            invalidate();
            CurrPosX= currX;
            CurrPosY= currY+300;
//            CurrPosX= Math.abs((float) (currX*(Math.cos(-45))));
//            CurrPosY= Math.abs((float) (2640-Math.abs((currY*(Math.sin(-45))))));
            Log.d("In Setcurrent Loc"," Drawing circle on canvas to"+CurrPosX+" "+CurrPosY);
            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            int x = (int)motionEvent.getX();
            int y = (int)motionEvent.getY();
            Intent i=new Intent(MainActivity.this, BuildingDetail.class);
            Log.d("X on Canvas >>",String.valueOf(x));
            Log.d("Y on Canvas>>",String.valueOf(y));

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isBetween(x, 160, 279) && isBetween(y, 605, 835)) {  //King Library
                        System.out.println("King Library");
                        Toast.makeText(MainActivity.this, "King library", Toast.LENGTH_SHORT).show();
                        i.putExtra("BuildingName","King library");
                        DestLat=37.335507;
                        DestLong=-121.884999;
                        CalculateDistanceAndTime();
                        startActivity(i);
                    }
                    if (isBetween(x, 758, 928) && isBetween(y, 605, 875)) {  //Engineering Building
                        System.out.println("Engineering Building");
                        Toast.makeText(MainActivity.this, "Engineering Building", Toast.LENGTH_SHORT).show();
                        i.putExtra("BuildingName","Engineering Building");
                        DestLat=37.335142;
                        DestLong=-121.881276;
                        CalculateDistanceAndTime();
                        startActivity(i);
                    }
                    if (isBetween(x, 140, 259) && isBetween(y, 1235, 1435)) {  //YUH
                        System.out.println("YUH");
                        Toast.makeText(MainActivity.this, "Yoshihiro Uchida Hall", Toast.LENGTH_SHORT).show();
                        i.putExtra("BuildingName","Yoshihiro Uchida Hall");
                        DestLat=37.333770;
                        DestLong=-121.883388;
                        CalculateDistanceAndTime();
                        startActivity(i);
                    }
                    if (isBetween(x, 753, 1088) && isBetween(y, 950, 1070)) {  //Student union
                        System.out.println("Student union");
                        Toast.makeText(MainActivity.this, "Student Union", Toast.LENGTH_SHORT).show();
                        i.putExtra("BuildingName","Student Union");
                        DestLat=37.336348;
                        DestLong=-121.881552;
                        CalculateDistanceAndTime();
                        startActivity(i);
                    }
                    if ((isBetween(x, 1148, 1287) && isBetween(y, 1165, 1225)) || (isBetween(x,1237,1287)&& isBetween(y,1095,1165))) {  //BBC
                        System.out.println("BBC");
                        Toast.makeText(MainActivity.this, "BBC", Toast.LENGTH_SHORT).show();
                        i.putExtra("BuildingName","BBC");
                        DestLat=37.336561;
                        DestLong=-121.878723;
                        CalculateDistanceAndTime();
                        startActivity(i);
                    }
                    if (isBetween(x, 434, 694) && isBetween(y, 1730, 1950)) {  //South Parking
                        System.out.println("South Parking");
                        Toast.makeText(MainActivity.this, "South Parking", Toast.LENGTH_SHORT).show();
                        i.putExtra("BuildingName","South Parking");
                        DestLat=37.333474;
                        DestLong=-121.879916;
                        CalculateDistanceAndTime();
                        startActivity(i);
                    }
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
            }
            return false;

        }
        public  boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
    }//Keypaint Finish


}
