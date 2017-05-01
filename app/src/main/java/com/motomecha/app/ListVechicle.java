package com.motomecha.app;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.google.gson.Gson;
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

public class ListVechicle extends AppCompatActivity {
    ListView listView;
    private  ProgressDialog dialog;
    String uid,vehicletype,change_url,servce_type;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vechicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        context=this;
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        uid=user.get("uid");
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        listView=(ListView) findViewById(R.id.list_in_way);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>MY</font> <font color=#ffffff>VEHICLE</font>";
        tv.setText(Html.fromHtml(text));
        try {
            servce_type = getIntent().getStringExtra("servicetype");
            vehicletype = getIntent().getStringExtra("vehicletype");
        } catch (Exception e) {
            e.printStackTrace();
        }
        change_url=GlobalUrlInit.MY_VEHICLE+"?uid="+uid+"&vtype="+vehicletype;
        new JSONTask().execute(change_url);
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
            if(movieModelList != null){
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row_my_vehicle, movieModelList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        new DroidDialog.Builder(context)
                                .icon(R.drawable.ic_stat_name)
                                .title("All Done!")
                                .content("Your booking has been confirmed we will contact you shortly!")
                                .cancelable(true, true)
                                .positiveButton("Ok", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        droidDialog.dismiss();
                                        Intent intent = new Intent(ListVechicle.this, BasicActivity.class);
                                        startActivity(intent);
                                    }
                                })


                                .typeface("rama.ttf")
                                .animation(AnimUtils.AnimZoomInOut)
                                .color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white),
                                        ContextCompat.getColor(context, R.color.colorRed))
                                .divider(true, ContextCompat.getColor(context, R.color.colorAccent))
                                .show();

                    }
                });


            }
            else {

                listView.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Please check your internet connection!",Toast.LENGTH_SHORT).show();

            }


        }

    }
}
