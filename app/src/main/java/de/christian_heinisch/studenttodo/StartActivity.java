package de.christian_heinisch.studenttodo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Läd das Übersichtsfragment
        startFragment();


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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return false;
    }*/



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

        if (id == R.id.nav_overview) {

            startFragment();

        } else if (id == R.id.nav_meeting) {

            titelleiste("Termine");

        } else if (id == R.id.nav_todo) {

            todo();

        } else if (id == R.id.nav_money) {

            money();

        } else if (id == R.id.nav_about) {

            about();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Funktion um den Text der Titelleiste zu ändern
    public void titelleiste(String title){

        // Setzt den Übergebenen Wert als Titelleiste
        setTitle(title);
    }

    /*
    Fragmente
     */

    public void startFragment() {

        titelleiste(getString(R.string.overview_title));

        Fragment f = new StartFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_start, f);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void about() {

        titelleiste(getString(R.string.about_title));

        Fragment f = new AboutFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_start, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void todo() {

        titelleiste(getString(R.string.todo_title));

        Fragment f = new ToDoFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_start, f);
        ft.addToBackStack(null);
        ft.commit();

    }


    public void money() {

        titelleiste(getString(R.string.money_title));

        Fragment f = new MoneyFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_start, f);
        ft.addToBackStack(null);
        ft.commit();

    }


    /*
    Dialoge
     */

    public void DialogAddToDO(){

        Bundle args = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // Create and show the dialog.
        DialogAddTodoFragment newFragment = new DialogAddTodoFragment();
        newFragment.setArguments(args);
        newFragment.show(ft, "dialog");

    }

    public void DialogEditToDO(long id){

        Bundle args = new Bundle();
        args.putLong("id", id);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // Create and show the dialog.
        DialogEditTodoFragment newFragment = new DialogEditTodoFragment();
        newFragment.setArguments(args);
        newFragment.show(ft, "dialog");

    }

    public void DialogAddMoney(){


        Bundle args = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // Create and show the dialog.
        DialogAddMoneyFragment newFragment = new DialogAddMoneyFragment();
        newFragment.setArguments(args);
        newFragment.show(ft, "dialog");

    }

    public void DialogAddMoneyDetail(){


        Bundle args = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // Create and show the dialog.
        DialogAddMoneyDetailFragment newFragment = new DialogAddMoneyDetailFragment();
        newFragment.setArguments(args);
        newFragment.show(ft, "dialog");

    }

    public void moneyDetail()
    {
        Fragment f = new MoneyDetail();
        FragmentManager fragmentManager;
        fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_start, f);
        ft.addToBackStack(null);
        ft.commit();
    }


}
