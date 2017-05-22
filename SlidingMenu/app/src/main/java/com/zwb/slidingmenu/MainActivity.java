package com.zwb.slidingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
    }

    public void open(View view) {
        if (slidingMenu.isOpen()) {
            slidingMenu.close();
        } else {
            slidingMenu.open();
        }
    }

}
