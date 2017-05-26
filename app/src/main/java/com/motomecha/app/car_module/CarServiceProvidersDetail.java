package com.motomecha.app.car_module;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.freshdesk.hotline.Hotline;
import com.freshdesk.hotline.HotlineConfig;
import com.freshdesk.hotline.HotlineUser;
import com.google.gson.Gson;
import com.motomecha.app.Global_classes.BasicActivity;
import com.motomecha.app.Global_classes.ConnectionDetector;
import com.motomecha.app.Global_classes.GlobalUrlInit;
import com.motomecha.app.R;
import com.motomecha.app.bike_module.ConfirmBooking;
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

public class CarServiceProvidersDetail extends AppCompatActivity {
String Saddress,Sdisplay_name,Sid,Sprice,Slikes,Sservice_description,Smerchant_image,name,Scall_number,email,mobile_number,kaddress,vehicleno,content_descrip,servetype,Smerchant_id;
    TextView Taddress,Tdisplay_name,Tprice,Tlikes;
    ImageView Imerchant_image;
    ImageButton Ibooknw,Icallnw,Ichatnw;
    private  ProgressDialog dialog;
    Context context;
    ListView car_review_new;
    ConnectionDetector c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_service_providers_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        context=CarServiceProvidersDetail.this;
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        assert tv != null;
        tv.setTypeface(custom_font);
        setTitle("");
        String text = "<font color=#ff1545>SERVICE PROVIDER</font> <font color=#ffffff>INFO</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CarServiceProvidersDetail.this,BasicActivity.class);
                startActivity(intent);
            }
        });
        Taddress=(TextView) findViewById(R.id.car_service_addres);
        Tlikes=(TextView) findViewById(R.id.likes_db);
        Tprice=(TextView) findViewById(R.id.price_car_ser);
        Tdisplay_name=(TextView) findViewById(R.id.car_shop_name);
        Imerchant_image=(ImageView) findViewById(R.id.car_sp_image);
        Ibooknw=(ImageButton) findViewById(R.id.booknow_map);
        Icallnw=(ImageButton) findViewById(R.id.call_now_map);
        Ichatnw=(ImageButton) findViewById(R.id.chat_map);
        car_review_new=(ListView) findViewById(R.id.car_rev) ;
        Saddress = getIntent().getStringExtra("address");
        Sdisplay_name = getIntent().getStringExtra("display_name");
        Sid = getIntent().getStringExtra("id");
        Sprice = getIntent().getStringExtra("price");
        servetype = getIntent().getStringExtra("servetype");
        Slikes = getIntent().getStringExtra("likes");
        Smerchant_id = getIntent().getStringExtra("id");
        Scall_number = getIntent().getStringExtra("number");
        Smerchant_image = getIntent().getStringExtra("merchant_image");
        Sservice_description = getIntent().getStringExtra("service_description");
        content_descrip=Sservice_description;
        content_descrip=content_descrip.replace("<ul>", "");
        content_descrip=content_descrip.replace("</ul>", "");
        content_descrip=content_descrip.replace("<li>", "");
        content_descrip=content_descrip.replace("</li>", "");
        content_descrip=content_descrip.replace("<br>", "\n");
        vehicleno = getIntent().getStringExtra("vehicleno");
        c = new ConnectionDetector(CarServiceProvidersDetail.this);
        if (c.isConnect()) {

            Glide.with(context).load(Smerchant_image).into(Imerchant_image);
            Tdisplay_name.setText(Sdisplay_name);
            Taddress.setText(Saddress);
            Tlikes.setText(Slikes);
            Tprice.setText("\u20B9" + Sprice);
            SQLiteHandler db = new SQLiteHandler(getApplicationContext());
            final HashMap<String, String> user = db.getUserDetails();
            name = user.get("name");
            email = user.get("email");
            mobile_number = user.get("pnum");
            kaddress = user.get("kaddre");
            HotlineConfig hotlineConfig = new HotlineConfig("a77f9cdb-24d2-441a-a950-c6a2ee3a97da", "79e50529-54c3-4bd3-a6ff-add1bcfafbb8");
            hotlineConfig.setVoiceMessagingEnabled(true);
            hotlineConfig.setCameraCaptureEnabled(true);
            hotlineConfig.setPictureMessagingEnabled(true);
            Hotline.getInstance(getApplicationContext()).init(hotlineConfig);

            //Update user information
            HotlineUser user1 = Hotline.getInstance(getApplicationContext()).getUser();
            user1.setName(name).setEmail(email).setPhone("+91", mobile_number);
            ;
            Hotline.getInstance(getApplicationContext()).updateUser(user1);

            Ibooknw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DroidDialog.Builder(context)
                            .icon(R.drawable.msingletone_logo)
                            .title("GENERAL SERVICE")
                            .content(content_descrip)
                            .cancelable(true, true)
                            .positiveButton("BOOK NOW", new DroidDialog.onPositiveListener() {
                                @Override
                                public void onPositive(Dialog droidDialog) {
                                    droidDialog.dismiss();
                                    Intent intent = new Intent(CarServiceProvidersDetail.this, ConfirmBooking.class);
                                    intent.putExtra("kaddress", kaddress);
                                    intent.putExtra("vechicletype", "CAR");
                                    intent.putExtra("vehicleno", vehicleno);
                                    intent.putExtra("price", Sprice);
                                    intent.putExtra("merchant_id", Smerchant_id);
                                    intent.putExtra("servetype", servetype);
                                    intent.putExtra("service_description", Sservice_description);
                                    startActivity(intent);
                                }
                            }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).divider(true, ContextCompat.getColor(context, R.color.colorAccent)).show();
                }
            });
            Icallnw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                            "tel", Scall_number, null));
                    startActivity(phoneIntent);
                }
            });
            Ichatnw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Hotline.showConversations(CarServiceProvidersDetail.this);

                }
            });

            new JSONTask().execute(GlobalUrlInit.CAR_REVIWES);
        }
        else {
            Toast.makeText(getApplicationContext(),"PLEASE CHECK YOUR INTERNET CONNECTIVITY",Toast.LENGTH_SHORT).show();

        }
    }

    public class MovieAdapter extends ArrayAdapter {

        private List<car_reviews> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdapter(Context context, int resource, List<car_reviews> objects) {
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
                holder.display_name=(TextView) convertView.findViewById(R.id.review_name);
                holder.display_company=(TextView) convertView.findViewById(R.id.review_dom);
                holder.display_description=(TextView) convertView.findViewById(R.id.review_desc);

                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            car_reviews categorieslist= movieModelList.get(position);
            holder.display_name.setText(categorieslist.getName());
            holder.display_company.setText(categorieslist.getReview_domain());
            holder.display_description.setText(categorieslist.getReview_message());

            return convertView;
        }

        class ViewHolder{

            private TextView display_name,display_company,display_description;
        }



    }
    public class JSONTask extends AsyncTask<String,String, List<car_reviews>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<car_reviews> doInBackground(String... params) {
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
                List<car_reviews> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    car_reviews categorieslist = gson.fromJson(finalObject.toString(), car_reviews.class);
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
        protected void onPostExecute(final List<car_reviews> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList.size()>0) {
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row_car_reviews, movieModelList);
                car_review_new.setAdapter(adapter);
            }
            else
            {
                car_review_new.setVisibility(View.INVISIBLE);

            }

        }

    }

}
