package Extra;


import android.app.Activity;
import android.util.Log;

public class DistanceClass extends Activity{

    private static DistanceClass Obj;
    private static Double Lat;
    private static Double Lang;


    private static Double Distance;
    private static Double Time;

    private static String DistanceTime;


public void ResetDistanceClass()
{
    Lat=0.00;
    Lang=0.00;
    Distance=0.00;
    Time=0.00;
    DistanceTime="";
}

    public static void setObj(DistanceClass obj) {
        Obj = obj;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLang() {
        return Lang;
    }

    public void setLang(Double lang) {
        Lang = lang;
    }

    public Double getDistance() {
        Log.v("Object", getObj().toString());
        return getObj().Distance;
    }

    public void setDistance(Double distance) {
        Log.v("Setting Dist",String.valueOf(distance));
        getObj().Distance = distance;
    }

    public Double getTime() {
        return getObj().Time;
    }

    public void setTime(Double time) {
        Log.v("Setting Time",String.valueOf(time));
        getObj().Time = time;
    }


    public String getDistanceTime() {
        return DistanceTime;
    }

    public void setDistanceTime(String distanceTime) {
        DistanceTime = distanceTime;

        Log.v("Inside SetDouble","Inside SetDouble");
        String res[]=distanceTime.split(",");
        Double min=Double.parseDouble(res[0])/60;
        Double dist=Double.parseDouble(res[1]);
        getObj().setDistance(dist);
        getObj().setTime(min);

        Log.v("Duration","Duration= " + (int) (min / 60) + " hr " + (int) (min % 60) + " mins");
        Log.v("Distance","Distance= " + dist/1000 + " kilometers "+dist%1000+" meters");
    }

    public static DistanceClass getObj()
    {
        if(Obj==null)
        {
            Log.v("In getObj", "Inside Get Object");
            return new DistanceClass();
        }
        else
        {
            return Obj;
        }
    }


}
