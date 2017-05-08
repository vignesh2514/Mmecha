package com.motomecha.app.Global_classes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;
import com.motomecha.app.dbhandler.SessionManager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class BasicActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SessionManager session;
    private SQLiteHandler db;
    BottomBar bottomBar;
    public static String FACEBOOK_URL = "https://www.facebook.com/app.motomecha";
    public static String FACEBOOK_PAGE_ID = "app.motomecha";
    ConnectionDetector c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        c=new ConnectionDetector(BasicActivity.this);
        setTitle("");
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>MOTO</font><font color=#ffffff>MECHA</font>";
        tv.setText(Html.fromHtml(text));
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        FirebaseCrash.log("Activity created");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment selectedFragment = null;

           if (tabId == R.id.home_bar)
           {
               selectedFragment = HomeFragment.newInstance();
           }
               else if (tabId == R.id.tyre_bar)
           {
               selectedFragment = TyreFragement.newInstance();
           }
           else if (tabId == R.id.battery_bar)
           {
               selectedFragment = BatteryFragment.newInstance();
           }
           else if (tabId == R.id.more_bar)
           {
               selectedFragment = MoreFragment.newInstance();
           }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();

            }

        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.basic, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_profile) {

        } else if (id == R.id.nav_vehicle) {
            Intent intent =new Intent(BasicActivity.this,VehicleMode.class);
            startActivity(intent);
        } else if (id == R.id.nav_service) {
            Intent intent =new Intent(BasicActivity.this,ServiceHistory.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_buy_sell)
        {
            Intent intent =new Intent(BasicActivity.this,GlobalWebPage.class);
            intent.putExtra("title1","USED BIKES");
            intent.putExtra("wburl"," http://motomecha.com/");
            startActivity(intent);
        }
        else if (id == R.id.nav_face) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getFacebookPageURL(this);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        } else if (id == R.id.nav_you_tube) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UChqD_8YROz0KccDQz_p4-Rg")));
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "I AM HAPPY WITH MOTOMECHA";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "RIDE via"));

        }else if (id == R.id.nav_logt) {
logoutUser();
        }
        else if (id == R.id.nav_custom_speak)
        {
            Intent intent = new Intent(BasicActivity.this, CustomerReviews.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(BasicActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
