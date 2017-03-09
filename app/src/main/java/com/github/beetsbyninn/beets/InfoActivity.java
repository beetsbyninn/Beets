package com.github.beetsbyninn.beets;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity {
    private Button btnDoneWitnTutorial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyAdapter());

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager, true);

    }

    private class MyAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            InfoObject infoObject = InfoObject.values()[position];
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(infoObject.getLayoutId(), collection, false);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return InfoObject.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }

    public void myFancyMethod(View v) {
        Log.d("TAG","HEJ");
    }

}
