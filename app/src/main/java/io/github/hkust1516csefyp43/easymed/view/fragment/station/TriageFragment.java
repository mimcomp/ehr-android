package io.github.hkust1516csefyp43.easymed.view.fragment.station;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import io.github.hkust1516csefyp43.easymed.R;
import io.github.hkust1516csefyp43.easymed.listener.OnFragmentInteractionListener;
import io.github.hkust1516csefyp43.easymed.pojo.server_response.Clinic;
import io.github.hkust1516csefyp43.easymed.utility.Cache;
import io.github.hkust1516csefyp43.easymed.utility.Const;
import io.github.hkust1516csefyp43.easymed.view.activity.PatientVisitEditActivity;
import io.github.hkust1516csefyp43.easymed.view.activity.SearchActivity;
import io.github.hkust1516csefyp43.easymed.view.fragment.PatientListFragment;

public class TriageFragment extends Fragment implements OnFragmentInteractionListener {

  private OnFragmentInteractionListener mListener;
  private TabLayout tabLayout;
  private ViewPager viewPager;
  private FloatingActionButton floatingActionButton;

  public static TriageFragment newInstance() {
    TriageFragment fragment = new TriageFragment();
    return fragment;
  }

  public TriageFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_toolbar_tablayout_viewpager_fab, container, false);
    Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    toolbar.setTitle("Triage");
    Clinic thisClinic = Cache.CurrentUser.getClinic(getContext());
    if (thisClinic != null) {
      toolbar.setSubtitle(thisClinic.getEnglishName());
    }
    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    if (drawer != null) {
      drawer.setDrawerListener(toggle);
      toggle.syncState();
    }

    tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
    viewPager = (ViewPager) view.findViewById(R.id.viewPager);

    tabLayout.addTab(tabLayout.newTab().setText("After"));
    tabLayout.addTab(tabLayout.newTab().setText("Everyone else"));
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }
    });

    viewPager.setAdapter(new TwoPagesAdapter(getFragmentManager()));
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    viewPager.setOffscreenPageLimit(2);

    floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
    floatingActionButton.setImageDrawable(new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_add).color(Color.WHITE).paddingDp(3).sizeDp(16));
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addPatientDialog();
      }
    });

    return view;
  }

  private void addPatientDialog() {
    final Dialog dialog = new Dialog(getContext(), R.style.AppTheme);
    dialog.setContentView(R.layout.dialog_app_patient);

    ImageView ivExistingPatient = (ImageView) dialog.findViewById(R.id.ivExistingPatient);
    ivExistingPatient.setImageDrawable(new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_search).color(Color.WHITE).sizeDp(32));

    ImageView ivNotSure = (ImageView) dialog.findViewById(R.id.ivNotSure);
    ivNotSure.setImageDrawable(new IconicsDrawable(getContext(), FontAwesome.Icon.faw_question).color(Color.WHITE).sizeDp(32));

    ImageView ivNewPatient = (ImageView) dialog.findViewById(R.id.ivNewPatient);
    ivNewPatient.setImageDrawable(new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_add).color(Color.WHITE).sizeDp(32));

    ImageView ivOpenSaves = (ImageView) dialog.findViewById(R.id.ivOpenSaves);
    ivOpenSaves.setImageDrawable(new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_folder_open).color(getResources().getColor(R.color.secondary_text_color)).sizeDp(32));

    LinearLayout llExistingPatient = (LinearLayout) dialog.findViewById(R.id.llExistingPatient);
    llExistingPatient.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtra(Const.BundleKey.IS_TRIAGE, true);
        startActivity(intent);
        dialog.dismiss();
      }
    });

    LinearLayout llNotSure = (LinearLayout) dialog.findViewById(R.id.llNotSure);
    llNotSure.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        //Also search, but maybe a extra + button for easier add new patient? (extra)
        startActivity(intent);
        dialog.dismiss();
      }
    });

    LinearLayout llNewPatient = (LinearLayout) dialog.findViewById(R.id.llNewPatient);
    llNewPatient.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), PatientVisitEditActivity.class);
        startActivity(intent);
        dialog.dismiss();
      }
    });

    LinearLayout llOpenSave = (LinearLayout) dialog.findViewById(R.id.llOpenSave);
    llOpenSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //dialog shows list of saved stuff (can you show dialog on top of dialog? No >> dismiss this first)
        dialog.dismiss();
      }
    });

    dialog.show();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }
//
//  public void updateTabTitleCounter(int whichPage, int howMuch) {
//    if (tabLayout != null) {
//      switch (whichPage) {
//        case Const.PatientListPageId.POST_TRIAGE:
//            tabLayout.getTabAt(0).setText("AFTER (" + howMuch + ")");
//          break;
//        case Const.PatientListPageId.NOT_YET:
//        default:        //TODO idk
//          tabLayout.getTabAt(1).setText("AFTER (" + howMuch + ")");
//          break;
//
//      }
//    }
//  }

  private class TwoPagesAdapter extends FragmentStatePagerAdapter {

    public TwoPagesAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return PatientListFragment.newInstance(Const.PatientListPageId.POST_TRIAGE, null);
        case 1:
          return PatientListFragment.newInstance(Const.PatientListPageId.NOT_YET, null);
        default:
          return PatientListFragment.newInstance(Const.PatientListPageId.NOT_YET, null);    //TODO idk?

      }
    }

    @Override
    public int getCount() {
      return 2;
    }
  }

}