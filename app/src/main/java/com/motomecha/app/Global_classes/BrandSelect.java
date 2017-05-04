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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.motomecha.app.R;


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
import java.util.List;

public class BrandSelect extends AppCompatActivity {
    private ProgressDialog dialog;
    GridView cate_list;
String change_url,btype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cate_list=(GridView) findViewById(R.id.cato_listv);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        setTitle("");
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>SELECT</font> <font color=#ffffff>BRAND</font>";
        tv.setText(Html.fromHtml(text));
        change_url=GlobalUrlInit.VECHILE_LISTING_BIKE;
        Intent intent = getIntent();
         btype = intent.getStringExtra("brandtype");
        try {
            if (btype.equals("bike"))
            {
                change_url=change_url+"brand_select_bike.php";
            }
            else
            {
                change_url=change_url+"brand_select_car.php";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new JSONTask().execute(change_url);
    }
    public class MovieAdapter extends ArrayAdapter {

        private List<BrandSelect_list> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdapter(Context context, int resource, List<BrandSelect_list> objects) {
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
                holder.brand_image = (ImageView)convertView.findViewById(R.id.brand_image);
                holder.brand_name=(TextView) convertView.findViewById(R.id.brand_text);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            BrandSelect_list categorieslist= movieModelList.get(position);
            Glide.with(context).load(categorieslist.getBrand_image()).into(holder.brand_image);
            holder.brand_name.setText(categorieslist.getBrand());
            return convertView;
        }

        class ViewHolder{
            private ImageView brand_image;
            private TextView brand_name;
        }



    }
    public class JSONTask extends AsyncTask<String,String, List<BrandSelect_list>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<BrandSelect_list> doInBackground(String... params) {
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
                List<BrandSelect_list> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                        BrandSelect_list categorieslist = gson.fromJson(finalObject.toString(), BrandSelect_list.class);
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
        protected void onPostExecute(final List<BrandSelect_list> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();

            if(movieModelList != null){
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row_brand_list, movieModelList);
                cate_list.setAdapter(adapter);

                cate_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        BrandSelect_list categorieslist = movieModelList.get(position);
                        Intent intent = new Intent(BrandSelect.this, ModelSelect.class);
                        intent.putExtra("brand",categorieslist.getBrand());
                        intent.putExtra("brand_type",btype);
                        startActivity(intent);

                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(),"Please check your internet connection!",Toast.LENGTH_SHORT).show();

            }


        }

    }

}
