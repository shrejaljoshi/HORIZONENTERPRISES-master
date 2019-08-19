package com.client.vpman.horizonenterprisesempproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.client.vpman.horizonenterprisesempproject.Fragment.Recent;
import com.client.vpman.horizonenterprisesempproject.Fragment.DashboardFragment;
import com.client.vpman.horizonenterprisesempproject.Fragment.User;
import com.client.vpman.horizonenterprisesempproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import static com.client.vpman.horizonenterprisesempproject.useful.UtilityMethod.goNextFragmentWithBackStack;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    NavigationView navigationView;
    MenuItem prevMenuItem;
    public static DrawerLayout mDrawereLayout;
    private ActionBarDrawerToggle drawerToggle;
    Context mContext;
    FloatingActionButton floatingActionButton;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawereLayout = findViewById(R.id.Main);

        floatingActionButton = findViewById(R.id.fab007);
        floatingActionButton.setTransitionName("reveal");
        mContext = HomeActivity.this;

        navigationView = findViewById(R.id.navigation);


        DashboardFragment fragment = new DashboardFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.container, fragment, "");
        transaction.commit();


        mDrawereLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                displayView(item.getItemId());
                return true;
            }

        });


        BottomNavigationView bottomNav = findViewById(R.id.bottombar007);

        floatingActionButton.setOnClickListener(this::presentActivity);

        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.Todo:
                    goNextFragmentWithBackStack(mContext, new DashboardFragment());
                    floatingActionButton.setVisibility(View.VISIBLE);
                    break;
                case R.id.recent:
                    goNextFragmentWithBackStack(mContext, new Recent());
                    floatingActionButton.setVisibility(View.GONE);
                    break;
                case R.id.profile:
                    goNextFragmentWithBackStack(mContext, new User());
                    floatingActionButton.setVisibility(View.GONE);
                    break;
                default:
                    goNextFragmentWithBackStack(mContext, new DashboardFragment());
                    break;
            }
            return true;
        });



    }
    private void displayView(int itemId) {

        switch (itemId) {

            case (R.id.nav_home):
                mDrawereLayout.closeDrawers();
                goNextFragmentWithBackStack(mContext,new DashboardFragment());

                break;

            case (R.id.nav_about):
                mDrawereLayout.closeDrawers();

              //  goNextFragmentWithBackStack(mContext,fragment);

                break;

            case (R.id.nav_share):
                mDrawereLayout.closeDrawers();

           //     goNextFragmentWithBackStack(mContext,fragment);



                break;
            case (R.id.nav_setting):
                mDrawereLayout.closeDrawers();

                //    goNextFragmentWithBackStack(mContext,fragment);


                break;






            case (R.id.nav_logout):
                mDrawereLayout.closeDrawers();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle(mContext.getString(R.string.Logout));

                alertDialog.setMessage(mContext.getString(R.string.are_you_sure_you_want_to_logout));
                alertDialog.setPositiveButton(mContext.getString(R.string.yes),
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                //sessionManager.logoutUser();
                              //  startActivity(new Intent(mContext, LoginActivity.class));
                             //   finish();
                            }

                        });
                alertDialog.setNegativeButton(mContext.getString(R.string.no),

                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to invoke NO event
                                dialog.cancel();
                            }

                        });


                alertDialog.show();

                break;
            default:
                break;
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawereLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        backButtonHandler();
        return;
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, Add_Detail.class);
        intent.putExtra(Add_Detail.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(Add_Detail.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    public void backButtonHandler() {


        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if (backStackEntryCount > 0) {
            getFragmentManager().popBackStackImmediate();

            if (backStackEntryCount == 1) {

                super.onBackPressed();

            } else
                super.onBackPressed();

        } else {
            if (doubleBackToExitPressedOnce) {
                //super.onBackPressed();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(mContext.getString(R.string.exit_from_application));
                builder.setMessage(R.string.exit_app)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HomeActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

}
