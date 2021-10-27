package java.android.quanlybanhang.function.BepBar.Fragment;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.android.quanlybanhang.function.BepBar.BepActivity;

public class Fragment extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    private BepActivity bepActivity;

    public Fragment(Context context, FragmentManager fm, int totalTabs, BepActivity
            bepActivity) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.bepActivity=bepActivity;
    }

    // this is for fragment tabs
    @Override
    public androidx.fragment.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                DonHangOfflineFragment donOffline = new DonHangOfflineFragment();
                return donOffline;
            case 1:
                DonHangOnlineDangChoSuLiFragment donOnline = new DonHangOnlineDangChoSuLiFragment();
                return donOnline;
            case 2:
                DonHangOfflineFragment tableFragment3 = new DonHangOfflineFragment();
                return tableFragment3;
            case 3:
                DonHangOfflineFragment tableFragment4 = new DonHangOfflineFragment();
                return tableFragment4;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
