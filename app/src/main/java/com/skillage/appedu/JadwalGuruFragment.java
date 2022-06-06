package com.skillage.appedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class JadwalGuruFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ViewPager viewPager;
    TabLayout tabLayout;


    TextView txtTest;
    public JadwalGuruFragment() {

    }

    public static JadwalGuruFragment newInstance(String param1, String param2) {
        JadwalGuruFragment fragment = new JadwalGuruFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jadwal_guru, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        GlobalVar.jadwalkuliah.getRecords("SELECT  * from vw_jadwal where Tahun_pelajaran='"+GlobalVar.TahunPelajaran+"' and Semester='"+GlobalVar.Semester+"' and id_guru='"+ GlobalVar.idGuru +"'");

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String Hari = tab.getText().toString();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(fragmentBaru("SENIN"),"SENIN");
        adapter.addFragment(fragmentBaru("SELASA"),"SELASA");
        adapter.addFragment(fragmentBaru("RABU"),"RABU");
        adapter.addFragment(fragmentBaru("KAMIS"),"KAMIS");
        adapter.addFragment(fragmentBaru("JUMAT"),"JUMAT");
        adapter.addFragment(fragmentBaru("SABTU"),"SABTU");

        viewPager.setAdapter(adapter);

    }

    private ScheduleFragment fragmentBaru(String hari){
        Bundle b=new Bundle(); b.putString("hari",hari);
        ScheduleFragment f=new ScheduleFragment();
        f.setArguments(b);
        return  f;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}