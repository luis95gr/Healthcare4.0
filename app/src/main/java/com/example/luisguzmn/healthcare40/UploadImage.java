package com.example.luisguzmn.healthcare40;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;


public class UploadImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);


        //MENU
        //-----------------------------------------------
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarMain);
        toolbar.setTitle("Upload Photo");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        GlobalVars g = (GlobalVars) getApplication();
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem().withName(g.getName()).
                                withEmail(g.getEmail()).withIcon("http://meddata.sytes.net/newuser/profileImg/" + g.getImage()))
                .withSelectionListEnabledForSingleProfile(false).withHeaderBackground(R.drawable.header)
                .build();
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_main).withIcon(GoogleMaterial.Icon.gmd_accessibility);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_profile).withIcon(GoogleMaterial.Icon.gmd_account_balance);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_records).withIcon(GoogleMaterial.Icon.gmd_add_to_photos);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_statistics).withIcon(GoogleMaterial.Icon.gmd_adb);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_healt).withIcon(GoogleMaterial.Icon.gmd_attach_file);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName(R.string.drawer_item_settinds).withIcon(GoogleMaterial.Icon.gmd_bluetooth);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_about).withIcon(GoogleMaterial.Icon.gmd_terrain);

        new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar).withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5,
                        new DividerDrawerItem(),
                        item6,
                        item7
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        if (position == 2) {
                            Intent intent = new Intent(UploadImage.this, Profile.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        }
                        if (position == 1) {
                            Intent intent = new Intent(UploadImage.this, MainScreen.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        }


                        return false;
                    }
                })
                .build();

        //Image Menu
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            GlobalVars g = (GlobalVars) getApplication();
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load("http://meddata.sytes.net/newuser/profileImg/" + g.getImage())
                        .placeholder(placeholder).into(imageView);
            }
            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });

        //-------------------------------------------------------------------------------------------
        //MENU

    }


    //ELIMINAR BACK PRESS
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (keyCode == KeyEvent.KEYCODE_BACK ? true : super.onKeyDown(keyCode, event));
    }
    //MENU 3 DOTS
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(UploadImage.this,MainScreen.class));
                return true;

            case R.id.logout:
                SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.apply();
                SharedPreferences spLogin = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor spLoginEditor = spLogin.edit();
                spLoginEditor.putBoolean("success",false);
                spLoginEditor.apply();
                startActivity(new Intent(UploadImage.this,MainActivity.class));
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}