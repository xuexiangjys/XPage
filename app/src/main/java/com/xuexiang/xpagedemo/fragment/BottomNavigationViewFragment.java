package com.xuexiang.xpagedemo.fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpagedemo.R;
import com.xuexiang.xpagedemo.databinding.FragmentBottomNavigationviewBinding;
import com.xuexiang.xutil.common.CollectionUtils;
import com.xuexiang.xutil.tip.ToastUtils;

/**
 * @author xuexiang
 * @since 2019-06-19 14:29
 */
@Page(name = "Tab主页联动使用")
public class BottomNavigationViewFragment extends XPageFragment implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    FragmentBottomNavigationviewBinding binding;

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToRoot) {
        binding = FragmentBottomNavigationviewBinding.inflate(inflater, container, attachToRoot);
        return binding.getRoot();
    }

    @Override
    protected TitleBar initTitleBar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_navigation_back_white);
        binding.toolbar.setTitle("Tab主页联动使用");
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popToBack();
            }
        });
        return null;
    }

    int[] menuItemIds = new int[]{R.id.item_dashboard, R.id.item_photo, R.id.item_music, R.id.item_movie};
    String[] titles = new String[]{"资讯", "照片", "音乐", "电影"};

    @Override
    protected void initViews() {
        FragmentAdapter<XPageFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        for (String title : titles) {
            adapter.addFragment(SimpleListFragment.newInstance(title), title);
        }
        binding.viewPager.setOffscreenPageLimit(titles.length - 1);
        binding.viewPager.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        binding.viewPager.addOnPageChangeListener(this);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.toast("新建");
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        binding.bottomNavigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(titles, menuItem.getTitle());
        if (index != -1) {
            binding.viewPager.setCurrentItem(index, true);
            return true;
        }
        return false;
    }

}
