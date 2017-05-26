package com.motomecha.app.Global_classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceTracking extends AppCompatActivity {
ImageView Iimage_view;
    private  ProgressDialog dialog;
    String uid,change_url;
    ListView listView;
    ConnectionDetector c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_tracking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>SERVICE</font> <font color=#ffffff>TRACKING</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceTracking.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        uid=user.get("uid");
        Iimage_view=(ImageView) findViewById(R.id.imageView3);
        listView=(ListView) findViewById(R.id.service_track);
        c = new ConnectionDetector(ServiceTracking.this);
        if (c.isConnect()) {

            change_url = GlobalUrlInit.SERVICE_TRACKING_URL + uid;
            new JSONTask().execute(change_url);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();

        }

    }

    public class MovieAdapter extends ArrayAdapter {

        private List<Service_Tracking_List> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdapter(Context context, int resource, List<Service_Tracking_List> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getViewTypeCount() {

            return 1;
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder  ;

            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();
                holder.plate_no=(TextView) convertView.findViewById(R.id.regis_number);
                holder.service_name=(TextView) convertView.findViewById(R.id.service_type);
                holder.order_statusshort=(TextView) convertView.findViewById(R.id.order_short);
                holder.order_statu=(TextView) convertView.findViewById(R.id.order_status);
                holder.curr_date=(TextView) convertView.findViewById(R.id.order_date);
                holder.first_image=(ImageView) convertView.findViewById(R.id.first_signal);
                holder.second_image=(ImageView) convertView.findViewById(R.id.second_signal);
                holder.third_image=(ImageView) convertView.findViewById(R.id.third_signal);
holder.fourth_image=(ImageView) convertView.findViewById(R.id.fourth_signal);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Service_Tracking_List categorieslist= movieModelList.get(position);
            holder.plate_no.setText(categorieslist.getRegNumber());
            holder.service_name.setText(categorieslist.getServiceType());
            holder.order_statusshort.setText(categorieslist.getOrder_status_short());
            holder.order_statu.setText(categorieslist.getOrderStatus());
            holder.curr_date.setText(categorieslist.getDate_time());
if ( holder.order_statusshort.getText().toString().contains("CLO"))
{
    holder.order_statusshort.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
}

else if (holder.order_statu.getText().toString().contains("CONFIRM"))
{
    holder.fourth_image.setImageResource(R.drawable.blue_active);

}
            if ( holder.order_statu.getText().toString().contains("PROCE"))
            {
                holder.fourth_image.setImageResource(R.drawable.blue_active);
                holder.second_image.setImageResource(R.drawable.yellow_active);

            }
       else if (holder.order_statu.getText().toString().contains("COMPLE"))
            {
                holder.fourth_image.setImageResource(R.drawable.blue_active);
                holder.second_image.setImageResource(R.drawable.yellow_active);
                holder.third_image.setImageResource(R.drawable.green_active);

            }


            return convertView;
        }

        class ViewHolder{

            private TextView plate_no,service_name,order_statu,order_statusshort,curr_date;
            private  ImageView first_image,second_image,third_image,fourth_image;
        }



    }
    public class JSONTask extends AsyncTask<String,String, List<Service_Tracking_List>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Service_Tracking_List> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<Service_Tracking_List> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    Service_Tracking_List categorieslist = gson.fromJson(finalObject.toString(), Service_Tracking_List.class);
                    movieModelList.add(categorieslist);

                }

                return movieModelList;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;

        }

        @Override
        protected void onPostExecute(final List<Service_Tracking_List> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();

            if(movieModelList.size()>0){
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row_service_tracking_list, movieModelList);
                listView.setAdapter(adapter);

            }
            else {
                Iimage_view.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);



            }


        }

    }
}
