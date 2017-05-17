package com.motomecha.app.Global_classes;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ListVechicle extends AppCompatActivity {
    ListView listView;
    private  ProgressDialog dialog;
    String uid,vehicletype,change_url,servce_type,vehicle_no,bookig_id,current_date;
Context context;
    TextView date_alltext;
    ImageView Inovehicle;
    ImageButton Baddvec;
    private int mYear, mMonth, mDay;
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
        Inovehicle =(ImageView) findViewById(R.id.imageView4);
        Baddvec=(ImageButton) findViewById(R.id.button2);
        date_alltext=(TextView) findViewById(R.id.alltext_date);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String text = "<font color=#ff1545>SELECT</font> <font color=#ffffff>VEHICLE</font>";
        tv.setText(Html.fromHtml(text));
        ImageView imageView=(ImageView) findViewById(R.id.dark_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListVechicle.this,BasicActivity.class);
                startActivity(intent);
            }
        });
            servce_type = getIntent().getStringExtra("servicetype");
            vehicletype = getIntent().getStringExtra("vehicletype");
        Baddvec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListVechicle.this,BrandSelect.class);
                intent.putExtra("brandtype",vehicletype);
                startActivity(intent);
            }
        });
        change_url=GlobalUrlInit.MY_VEHICLE+"?uid="+uid+"&vtype="+vehicletype;
        new JSONTask().execute(change_url);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth=mMonth+1;

    }

    public void getmyotherbooking(final String uid, final String servce_type, final String current_date, final String vehicle_no, final String bookig_id) {

        StringRequest stringRequest =new StringRequest(Request.Method.POST, GlobalUrlInit.OTHER_SERVICE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean exits = jObj.getBoolean("exits");
                    if (exits)
                    {new DroidDialog.Builder(context)
                                .icon(R.drawable.msingletone_logo)
                                .title("BOOKING CONFIRMED")
                                .content("YOUR BOOKING HAS BEEN CONFIRMED WE WILL CONTACT YOU SHORTLY!")
                                .cancelable(true, true)
                                .positiveButton("OK", new DroidDialog.onPositiveListener() {
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
                    else
                    {
                        Toast.makeText(getApplicationContext(),"BOOKING HAS BEEN CANCELLED.TRY AGAIN AFTER SOMETIME!",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"TRY AGAIN AFTER SOMETIME",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("uid",uid);
                params.put("servetype",servce_type);
                params.put("current_date",current_date);
                params.put("vehicleno",vehicle_no);
                params.put("booking_id",bookig_id);

                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest,"CHECKING");

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
            Random r = new Random();
            int ri = r.nextInt((99999 - 12345)+1) + 1234;
            bookig_id= "MM"+ri;
            date_alltext.setText(mDay+"-"+mMonth+"-"+mYear);
            if(movieModelList.size()>0){

    MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row_my_vehicle, movieModelList);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (servce_type!=null) {
            Myvehicle_list categorieslist = movieModelList.get(position);
            vehicle_no = categorieslist.getRegister_number();
            current_date = date_alltext.getText().toString();
            new DroidDialog.Builder(context)
                    .icon(R.drawable.msingletone_logo)
                    .title("PLEASE CONFIRM YOUR BOOKING")
                    .content("MOTOMECHA WILL RECOGNIZE THE BEST SERVICE PROVIDER FOR YOUR VEHICLE AND GUARANTEE THE BEST SERVICES")
                    .cancelable(true, true)
                    .positiveButton("CONFIRM YOUR BOOKING", new DroidDialog.onPositiveListener() {
                        @Override
                        public void onPositive(Dialog droidDialog) {
                            droidDialog.dismiss();
                            getmyotherbooking(uid, servce_type, current_date, vehicle_no, bookig_id);
                        }
                    })
                    .typeface("rama.ttf")
                    .animation(AnimUtils.AnimZoomInOut)
                    .color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white),
                            ContextCompat.getColor(context, R.color.colorRed))
                    .divider(true, ContextCompat.getColor(context, R.color.colorAccent))
                    .show();
            }
            else {
                Myvehicle_list categorieslist = movieModelList.get(position);
                vehicle_no = categorieslist.getRegister_number();
                String bike_model=categorieslist.getModel_brand();
                Intent intent=new Intent(ListVechicle.this,New_Plate_registration.class);
                intent.putExtra("vehicle_no",vehicle_no);
                intent.putExtra("bike_model",bike_model);
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
