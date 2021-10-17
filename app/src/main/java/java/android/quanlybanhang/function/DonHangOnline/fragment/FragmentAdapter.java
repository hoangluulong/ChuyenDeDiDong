package java.android.quanlybanhang.function.DonHangOnline.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.android.quanlybanhang.function.BaoCao.fragment.ChiSoSanPhamFragment;
import java.android.quanlybanhang.function.BaoCao.fragment.DanhSachTopSanPhamFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ChoXacNhanFragment();
            case 1:
                return new DanhSachTopSanPhamFragment();
            case 2:
                return new DonHoanThanhFragment();
            case 3:
                return new DonHuyFragment();

        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
