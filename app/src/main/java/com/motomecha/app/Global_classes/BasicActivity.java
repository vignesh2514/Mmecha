package com.motomecha.app.Global_classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.freshdesk.hotline.Hotline;
import com.freshdesk.hotline.HotlineConfig;
import com.freshdesk.hotline.HotlineUser;
import com.google.firebase.crash.FirebaseCrash;
import com.motomecha.app.R;
import com.motomecha.app.dbhandler.SQLiteHandler;
import com.motomecha.app.dbhandler.SessionManager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.HashMap;

public class BasicActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SessionManager session;
  String name,email,mobile_number;
    private SQLiteHandler db;

    BottomBar bottomBar;
    public static String FACEBOOK_URL = "https://www.facebook.com/app.motomecha";
    public static String FACEBOOK_PAGE_ID = "app.motomecha";
    ConnectionDetector c;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        c=new ConnectionDetector(BasicActivity.this);
        setTitle("");
        context=BasicActivity.this;
        TextView tv = (TextView) findViewById(R.id.text_view_toolb);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        Typeface custom_font = Typeface.createFromAsset(getApplication().getAssets(), "fonts/rama.ttf");
        assert tv != null;
        tv.setTypeface(custom_font);
        String text = "<font color=#ff1545>MOTO</font><font color=#ffffff>MECHA</font>";
        tv.setText(Html.fromHtml(text));

        session = new SessionManager(getApplicationContext());
         db = new SQLiteHandler(getApplicationContext());
        if (!session.isLoggedIn()) {
            session.setLogin(false);
            db.deleteUsers();
            Intent intent = new Intent(BasicActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        final HashMap<String, String> user = db.getUserDetails();
        name=user.get("name");
        email=user.get("email");
        mobile_number=user.get("pnum");

        HotlineConfig hotlineConfig = new HotlineConfig("a77f9cdb-24d2-441a-a950-c6a2ee3a97da", "79e50529-54c3-4bd3-a6ff-add1bcfafbb8");
        hotlineConfig.setVoiceMessagingEnabled(true);
        hotlineConfig.setCameraCaptureEnabled(true);
        hotlineConfig.setPictureMessagingEnabled(true);
        Hotline.getInstance(getApplicationContext()).init(hotlineConfig);

        //Update user information
        HotlineUser user1 = Hotline.getInstance(getApplicationContext()).getUser();
        user1.setName(name).setEmail(email).setPhone("+91", mobile_number);
        Hotline.getInstance(getApplicationContext()).updateUser(user1);
        FirebaseCrash.log("Activity created");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);

            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "PLEASE CLICK BACK AGAIN TO EXIT", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

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
            Intent intent =new Intent(BasicActivity.this,Profile_page.class);
            startActivity(intent);
        } else if (id == R.id.nav_vehicle) {
            Intent intent =new Intent(BasicActivity.this,VehicleMode.class);
            startActivity(intent);
        } else if (id == R.id.nav_service) {
            Intent intent =new Intent(BasicActivity.this,ServiceTracking.class);
            startActivity(intent);
        }
//        else if (id == R.id.nav_buy_sell)
//        {
//            Intent intent =new Intent(BasicActivity.this,GlobalWebPage.class);
//            intent.putExtra("title1","USED BIKES");
//            intent.putExtra("wburl"," http://motomecha.com/");
//            startActivity(intent);
//        }
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
            String shareBody = "I AM IMPRESSED WITH MY BIKE SERVICE WITH MOTOMECHA TRY AT https://play.google.com/store/apps/details?id=com.motomecha.app";
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
        else if (id == R.id.nav_shop)
        {
            Intent intent=new Intent(BasicActivity.this,GlobalWebPage.class);
            intent.putExtra("title1","MM SHOPING");
            intent.putExtra("wburl"," http://motomecha.com/");
            startActivity(intent);
        }
        else if (id == R.id.nav_email)
        {
         Intent   intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"goride@gmail.com"});
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
        }
        else if (id == R.id.nav_mobile)
        {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                    "tel", "9677086686", null));
            startActivity(phoneIntent);
        }
        else if (id == R.id.nav_chat)
        {
            Hotline.showConversations(BasicActivity.this);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logoutUser() {
        new DroidDialog.Builder(context)
                .icon(R.drawable.msingletone_logo)
                .title("ARE YOU SURE !")
                .content("DO YOU WANT TO LOGOUT?")
                .cancelable(true, true)
                .positiveButton("YES", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog dialog) {
                        session.setLogin(false);
                        db.deleteUsers();
                        Intent intent = new Intent(BasicActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .negativeButton("NO", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        droidDialog.dismiss();
                    }

                }).typeface("rama.ttf").animation(AnimUtils.AnimZoomInOut).color(ContextCompat.getColor(context, R.color.colorRed), ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorRed)).divider(true, ContextCompat.getColor(context, R.color.colorAccent)).show();


    }

}
