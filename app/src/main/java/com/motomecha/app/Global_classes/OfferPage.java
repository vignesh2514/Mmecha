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
import android.widget.ImageView;
import android.widget.ListView;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OfferPage extends AppCompatActivity {
    private  ProgressDialog dialog;
    ListView menu_list;
    ConnectionDetector c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>MY</font> <font color=#ffffff>OFFERS</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OfferPage.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        c = new ConnectionDetector(OfferPage.this);
        if (c.isConnect()) {

            menu_list = (ListView) findViewById(R.id.offer_list);
            new JSONTask().execute(GlobalUrlInit.OFFER_SERVICES);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();

        }

    }

    public class MovieAdapter extends ArrayAdapter {

        private List<OfferList> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        public MovieAdapter(Context context, int resource, List<OfferList> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getViewTypeCount() {

            return getCount();
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
                holder.more_img = (ImageView)convertView.findViewById(R.id.imageView2);
                holder.mor_title = (TextView)convertView.findViewById(R.id.offer_title);
                holder.more_des = (TextView)convertView.findViewById(R.id.offer_description);
                holder.more_ex=(TextView)convertView.findViewById(R.id.offer_validity);

                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            OfferList more_list_menu= movieModelList.get(position);
            Glide.with(context).load(more_list_menu.getOffer_image()).error(R.drawable.car_new).into(holder.more_img);
            holder.mor_title.setText(more_list_menu.getOffer_title());
            holder.more_des.setText(more_list_menu.getOffer_desc());
            holder.more_ex.setText(more_list_menu.getOffer_validity());


            return convertView;

        }

        class ViewHolder{
            private ImageView more_img;
            private TextView mor_title,more_des,more_ex;



        }

    }
    public class JSONTask extends AsyncTask<String,String, List<OfferList>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();

        }

        @Override
        protected List<OfferList> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<OfferList> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);


                    OfferList homeMenuMon = gson.fromJson(finalObject.toString(), OfferList.class);
                    movieModelList.add(homeMenuMon);

                }
                return movieModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
        protected void onPostExecute(final List<OfferList> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();

            if(movieModelList.size() >0) {
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row_more_fragment,  movieModelList);
                menu_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        OfferList more_list_menu = movieModelList.get(position);
                        Intent intent = new Intent(OfferPage.this, GlobalWebPage.class);
                        intent.putExtra("title1",more_list_menu.getOffer_title());
                        intent.putExtra("wburl", more_list_menu.getOffer_link());
                        startActivity(intent);

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "NO OFFERS RIGHT NOW", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
