package com.motomecha.app.Global_classes;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MoreFragment extends ListFragment {
ListView menu_list;
ProgressDialog dialog;
    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.fragment_more, container, false);
        dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        menu_list =(ListView) v.findViewById(android.R.id.list);
        new JSONTask().execute(GlobalUrlInit.MORE_SERVICES);
        return v;
    }
    public class MovieAdapter extends ArrayAdapter {

        private List<More_list_menu> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;

        public MovieAdapter(Context context, int resource, List<More_list_menu> objects) {
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

            More_list_menu more_list_menu= movieModelList.get(position);
            Glide.with(context).load(more_list_menu.getMore_image()).error(R.drawable.car_new).into(holder.more_img);
            holder.mor_title.setText(more_list_menu.getMore_title());
            holder.more_des.setText(more_list_menu.getMore_description());
            holder.more_ex.setText(more_list_menu.getMore_expiry());


            return convertView;

        }

        class ViewHolder{
            private ImageView more_img;
            private TextView mor_title,more_des,more_ex;



        }

    }
    public class JSONTask extends AsyncTask<String,String, List<More_list_menu> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<More_list_menu> doInBackground(String... params) {
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
                List<More_list_menu> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);


                        More_list_menu homeMenuMon = gson.fromJson(finalObject.toString(), More_list_menu.class);
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
        protected void onPostExecute(final List<More_list_menu> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList != null) {
                MovieAdapter adapter = new MovieAdapter(getActivity(), R.layout.row_more_fragment,  movieModelList);
                menu_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        More_list_menu more_list_menu = movieModelList.get(position);
                        Intent intent = new Intent(getActivity(), GlobalWebPage.class);
                        intent.putExtra("title1",more_list_menu.getMore_title());
                        intent.putExtra("wburl", more_list_menu.getRequest_link());
                        startActivity(intent);

                    }
                });
            } else {
                Toast.makeText(getActivity(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
