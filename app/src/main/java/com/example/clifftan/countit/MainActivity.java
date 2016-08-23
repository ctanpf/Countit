package com.example.clifftan.countit;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int THEMEBLACK = 0;
    private int incrementorInt = 1;
    RelativeLayout bg;
    Button inc;
    Button dec;
    TextView count;
    Window window;
    ActionBar actionBar;
    EditText incrementor;
    int val = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        bg = (RelativeLayout) findViewById(R.id.bg);

        count = (TextView) findViewById(R.id.count);
        count.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
        inc = (Button) findViewById(R.id.inc);
        dec = (Button) findViewById(R.id.dec);
        incrementor = (EditText) findViewById(R.id.incrementor);
        inc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                modifyCount(1);
            }
        });

        dec.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                modifyCount(0);
                changeTheme(THEMEBLACK);
            }
        });

        count.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (val != 0) {
                    val = 0;
                    count.setText("0");
                }
                return false;
            }
        });

        incrementor.setText("");
        incrementor.append("1");
        incrementor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do whatever you want here
                    String counter = incrementor.getText().toString();
                    int checkCounter = Integer.parseInt(counter);
                    String updateMessage = "";
                    if (checkCounter < 0){
                        updateMessage = "Counter need to be positive";
                    }
                    else{
                        updateMessage = "Counter is updated to : " + counter;
                        incrementorInt = checkCounter;
                    }

                    Toast.makeText(getApplicationContext(), updateMessage, Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(incrementor.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }





    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    modifyCount(1);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    modifyCount(0);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    public void modifyCount(int what){
        if (what == 1){
            val = val + incrementorInt;
            count.setText(Integer.toString(val));
        }

        else {
            if (val > 0) {
                val = val - incrementorInt;
                count.setText(Integer.toString(val));
            }
        }
    }

    public void changeTheme(int theme){
        switch(theme){
            case 0:
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent2)));
                bg.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark2));
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary2));
                break;

        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(getApplicationContext(), "Camera", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(getApplicationContext(), "Gallery", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(getApplicationContext(), "Slideshow", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(getApplicationContext(), "Tools", Toast.LENGTH_SHORT).show();
        } /*else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_SHORT).show();
        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
