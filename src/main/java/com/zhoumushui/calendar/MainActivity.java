package com.zhoumushui.calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.zhoumushui.calendar.adapter.CountRecyclerAdapter;
import com.zhoumushui.calendar.util.DateUtil;
import com.zhoumushui.calendar.util.MyLog;
import com.zhoumushui.calendar.view.RecyclerGridDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ViewPager viewPager;
    private List<View> viewList;

    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;

    private RecyclerView recyclerView;
    private CountRecyclerAdapter countRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);

        initialLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.count, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.count_add:

                break;

            case R.id.count_delete:
                if (countRecyclerAdapter != null)
                    countRecyclerAdapter.removeData(1);
                break;
        }
        return true;
    }

    private void initialLayout() {
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView);
        bottomNavigationView.
                setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener);

        setupViewPager();
    }

    private void setupViewPager() {
        viewList = new ArrayList<>();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        viewList.add(layoutInflater.inflate(R.layout.activity_main_diary, null));
        viewList.add(layoutInflater.inflate(R.layout.activity_main_count, null));
        viewList.add(layoutInflater.inflate(R.layout.activity_main_event, null));

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                MyLog.d("onPageScrolled:" + position);
            }

            @Override
            public void onPageSelected(int position) {
                MyLog.d("onPageSelected:" + position);

                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                MyLog.d("onPageScrollStateChanged:" + state);
            }
        });

        // 禁止ViewPager滑动
        /**
         viewPager.setOnTouchListener(new View.OnTouchListener() {
        @Override public boolean onTouch(View v, MotionEvent event) {
        return true;
        }
        });
         */

    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            updateLayout(position);

            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            MyLog.v("destroyItem position" + position);
            container.removeView(viewList.get(position));
        }
    };

    private void updateLayout(int position) {
        MyLog.v("updateLayout:" + position);
        switch (position) {
            case 0: { // Diary
                final TextView textLunarDate = (TextView) findViewById(R.id.textLunarDate);
                Calendar calendar = Calendar.getInstance();
                textLunarDate.setText(DateUtil.getLunarDateByCalendar(calendar));

                CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month,
                                                    int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        textLunarDate.setText(DateUtil.getLunarDateByCalendar(calendar));
                    }
                });
            }
            break;

            case 1: { // Count
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                // recyclerView.setLayoutManager(new LinearLayoutManager(this));
                // recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL));
                ArrayList<Count> arrayListCount = new ArrayList<Count>();
                for (int i = 0; i < 55; i++) {
                    Count count = new Count();
                    count.setCalendar(Calendar.getInstance());
                    arrayListCount.add(count);
                }
                countRecyclerAdapter = new CountRecyclerAdapter(context, arrayListCount);
                recyclerView.setAdapter(countRecyclerAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //recyclerView.addItemDecoration(new RecyclerListDecoration(context,
                //       RecyclerListDecoration.LIST_VERTICAL));
                recyclerView.addItemDecoration(new RecyclerGridDecoration(context));
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                FloatingActionButton floatActionAdd = (FloatingActionButton)
                        findViewById(R.id.floatActionAdd);
                floatActionAdd.setOnClickListener(myOnClickLister);
            }
            break;

            case 2: { // Event

            }
            break;

            default:
                break;
        }
    }

    private View.OnClickListener myOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatActionAdd:
                    Count count = new Count();
                    count.setCalendar(Calendar.getInstance());
                    if (countRecyclerAdapter != null)
                        countRecyclerAdapter.addData(1, count);
                    break;

                default:
                    break;
            }
        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener myOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_diary:
                    viewPager.setCurrentItem(0);
                    return true;

                case R.id.navigation_count:
                    viewPager.setCurrentItem(1);
                    return true;

                case R.id.navigation_event:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };
}
