package com.motomecha.app.Global_classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.motomecha.app.R;
import com.motomecha.app.bike_module.BikeMapActivity;
import com.motomecha.app.car_module.Car_ServiceProviders;
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

public class MyVechicle extends AppCompatActivity {
ImageButton Baddvec;
    String servicetype,vehicletype;
    ListView listView;
    private  ProgressDialog dialog;
String change_url,uid;
    ImageView Inovehicle;

    ConnectionDetector c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vechicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        uid = user.get("uid");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Inovehicle = (ImageView) findViewById(R.id.imageView4);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>MY</font> <font color=#ffffff>VEHICLE</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView = (ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyVechicle.this, BasicActivity.class);
                startActivity(intent);
            }
        });
        c = new ConnectionDetector(MyVechicle.this);
        if (c.isConnect()) {


        vehicletype = getIntent().getStringExtra("vehicletype");
        servicetype = getIntent().getStringExtra("servicetype");
        listView = (ListView) findViewById(R.id.list_new_bikes);
        Baddvec = (ImageButton) findViewById(R.id.button2);
        Baddvec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyVechicle.this, BrandSelect.class);
                intent.putExtra("brandtype", vehicletype);
                startActivity(intent);
            }
        });

        change_url = GlobalUrlInit.MY_VEHICLE + "?uid=" + uid + "&vtype=" + vehicletype;
        new JSONTask().execute(change_url);
    }
else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();
        }

    }

    public class MovieAdapter extends ArrayAdapter {

        private List<Myvehicle_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdapter(Context context, int resource, List<Myvehicle_list> objects) {
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
                holder.plate_no=(TextView) convertView.findViewById(R.id.plate_num);
                holder.model_b=(TextView) convertView.findViewById(R.id.model_br);

                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Myvehicle_list categorieslist= movieModelList.get(position);
            holder.plate_no.setText(categorieslist.getRegister_number());
            holder.model_b.setText(categorieslist.getModel_brand());
            return convertView;
        }

        class ViewHolder{

            private TextView plate_no,model_b;
        }



    }
    public class JSONTask extends AsyncTask<String,String, List<Myvehicle_list>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Myvehicle_list> doInBackground(String... params) {
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
                List<Myvehicle_list> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    Myvehicle_list categorieslist = gson.fromJson(finalObject.toString(), Myvehicle_list.class);
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
        protected void onPostExecute(final List<Myvehicle_list> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList.size()>0){
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row_my_vehicle, movieModelList);
                listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Myvehicle_list categorieslist= movieModelList.get(position);

                            if (vehicletype.equals("bike"))
                            {
                                Intent intent = new Intent(MyVechicle.this, BikeMapActivity.class);
                                intent.putExtra("servicetype",servicetype);
                                intent.putExtra("vehicletype",categorieslist.getModel_brand());
                                intent.putExtra("vehicleno",categorieslist.getRegister_number());
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(MyVechicle.this, Car_ServiceProviders.class);
                                intent.putExtra("servicetype",servicetype);
                                intent.putExtra("vehicleno",categorieslist.getRegister_number());
                                intent.putExtra("vehicletype",categorieslist.getModel_brand());
                                startActivity(intent);
                            }


                        }
                    });


            }
            else {
                Inovehicle.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);


            }


        }

    }
}
