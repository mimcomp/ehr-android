package io.github.louistsaitszho.ehrtesting;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amulyakhare.textdrawable.TextDrawable;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
//        tb.setTitle(R.string.triage);
        getSupportActionBar().setTitle(getResources().getString(R.string.triage));
        tb.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));
        tb.setCollapsible(true);
        tb.setSubtitle("Village name"); //TODO get it dynamically

        PrimaryDrawerItem triage = new PrimaryDrawerItem()
                .withName(R.string.triage)
                .withIcon(new IconicsDrawable(getApplicationContext(), CommunityMaterial.Icon.cmd_thermometer).color(Color.GRAY).paddingDp(2))
                .withIdentifier(1);
        PrimaryDrawerItem consultation = new PrimaryDrawerItem()
                .withName(R.string.consultation)
                .withIcon(new IconicsDrawable(getApplicationContext(), CommunityMaterial.Icon.cmd_hospital).color(Color.GRAY).paddingDp(2))
                .withIdentifier(2);
        PrimaryDrawerItem pharmacy = new PrimaryDrawerItem()
                .withName(R.string.pharmacy)
                .withIcon(new IconicsDrawable(getApplicationContext(), CommunityMaterial.Icon.cmd_pharmacy).color(Color.GRAY).paddingDp(2))
                .withIdentifier(3);

        PrimaryDrawerItem inventory = new PrimaryDrawerItem()
                .withName(R.string.inventory)
                .withIcon(new IconicsDrawable(getApplicationContext(), FontAwesome.Icon.faw_medkit).color(Color.GRAY).paddingDp(2))
                .withIdentifier(4);
        PrimaryDrawerItem adminDashboard = new PrimaryDrawerItem()
                .withName(R.string.admin_dashboard)
                .withIcon(new IconicsDrawable(getApplicationContext(), FontAwesome.Icon.faw_male).color(Color.GRAY).paddingDp(2))
                .withIdentifier(5);

        SecondaryDrawerItem settings = new SecondaryDrawerItem()
                .withName(R.string.settings)
                .withIcon(new IconicsDrawable(getApplicationContext(), GoogleMaterial.Icon.gmd_settings).color(Color.GRAY).paddingDp(2))
                .withIdentifier(6);
        SecondaryDrawerItem about = new SecondaryDrawerItem()
                .withName(R.string.about)
                .withIcon(new IconicsDrawable(getApplicationContext(), GoogleMaterial.Icon.gmd_info).color(Color.GRAY).paddingDp(2))
                .withIdentifier(7);

        DividerDrawerItem ddi = new DividerDrawerItem();

        AccountHeader ah = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_background)
                .addProfiles(new ProfileDrawerItem()
                        //TODO get and set the actual image. If image does not exist, load drawable
                        .withIcon(TextDrawable.builder().beginConfig().width(60).height(60).endConfig().buildRound("LT", ContextCompat.getColor(this, R.color.accent_color)))
                        .withName("Louis Tsai M.D.")
                        .withEmail("louis@email.com"))
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(tb)
                .withSelectedItem(-1)
                .addDrawerItems(triage, consultation, pharmacy, ddi, inventory, adminDashboard, ddi, settings, about)
                .withAccountHeader(ah)
                .build();

        TabLayout tl = (TabLayout) findViewById(R.id.tablayout);
        tl.addTab(tl.newTab().setText(R.string.queue));
        tl.addTab(tl.newTab().setText(R.string.finished));
        tl.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));

        //Attach FAB to Recycler View to enable auto hide
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerview);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingactionbutton);
        fab.attachToRecyclerView(rv);
        fab.setImageDrawable(new IconicsDrawable(getApplicationContext(), GoogleMaterial.Icon.gmd_add).color(Color.WHITE).sizeDp(16));

        //Crash test for parse
        final Context context = this;   //Is there a better way to do this -_-
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title("This is going to crash")
                        .content("Confirm to crash this thing to test Parse crash report")
                        .positiveText("Yes")
                        .negativeText("No")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                throw new RuntimeException("Test Exception!");
                            }
                        })
                        .show();
            }
        });

        //TODO transparent status bar padding
        //http://blog.raffaeu.com/archive/2015/04/11/android-and-the-transparent-status-bar.aspx
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_search).setIcon(new IconicsDrawable(getApplicationContext(), GoogleMaterial.Icon.gmd_search).color(Color.BLACK).actionBar().paddingDp(2));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
