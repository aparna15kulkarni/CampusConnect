package lab2.com.lab2;

        import android.content.Context;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

        import Extra.DistanceClass;

public class DistanceCalculation extends AsyncTask<String, Void, String>{


        Context mContext;
        DistanceClass distanceClass=DistanceClass.getObj();

public DistanceCalculation(Context mContext){
        this.mContext=mContext.getApplicationContext();

        }

@Override
protected void onPostExecute(String aDouble){
        super.onPostExecute(aDouble);
        Log.v("PRINT:","Inside OnPostExecute()");
        if(aDouble!=null)
        {
                Log.v("Distance matrix",aDouble);
                distanceClass.ResetDistanceClass();
                distanceClass.setDistanceTime(aDouble);

        }
        else
        Toast.makeText(mContext,"Error4!Please Try Again wiht proper values",Toast.LENGTH_SHORT).show();
        }

@Override
protected String doInBackground(String...params){
        try{

                Log.v("InDistanceCal","Hii In DistanceCalc");
        URL url=new URL(params[0]);
        HttpURLConnection con=(HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int statuscode=con.getResponseCode();
                Log.v("Status code",String.valueOf(statuscode));
        if(statuscode==HttpURLConnection.HTTP_OK)
        {
                Log.v("Inside HTTPOK","Inside HTTPOK");
        BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb=new StringBuilder();
        String line=br.readLine();
        while(line!=null)
        {
        sb.append(line);
        line=br.readLine();
        }
        String json=sb.toString();
        JSONObject root=new JSONObject(json);
        JSONArray array_rows=root.getJSONArray("rows");
        JSONObject object_rows=array_rows.getJSONObject(0);
        JSONArray array_elements=object_rows.getJSONArray("elements");
        JSONObject object_elements=array_elements.getJSONObject(0);
        JSONObject object_duration=object_elements.getJSONObject("duration");
        JSONObject object_distance=object_elements.getJSONObject("distance");
        return object_duration.getString("value")+","+object_distance.getString("value");
        }
        }catch(MalformedURLException e){
        Log.d("error","error1");
        }catch(IOException e){
        Log.d("error","error2");
        }catch(JSONException e){
        Log.d("error","error3");
        }
        return null;
        }
        }



