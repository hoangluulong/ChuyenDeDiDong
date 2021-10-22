package java.android.quanlybanhang.function.BaoCao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.android.quanlybanhang.R;
import java.util.Random;

public class BaoCaoChiSoActivity extends AppCompatActivity {

    private ValueLineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_chi_so);

        IDLayout();
        setLineChart();
    }

    private void IDLayout() {
        lineChart = (ValueLineChart)findViewById(R.id.lineChart);
    }

    private void setLineChart() {
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(getColor(R.color.so_hoa_don));

        ValueLineSeries series2 = new ValueLineSeries();
        series2.setColor(getColor(R.color.doanh_so));
        Random rd = new Random();


//        for (int i = 0 ; i < 50; i++) {
////            int number = rd.nextInt();  // trả về 1 số nguyên bất kỳ
//            int number1 = rd.nextInt(50);
//
//            series.addPoint(new ValueLinePoint(i+"", number1));
//            series2.addPoint(new ValueLinePoint(i+"", number1));
//        }

//        series.addPoint(new ValueLinePoint("24", 2f));
//        series2.addPoint(new ValueLinePoint("1", 1f));


        


        lineChart.addSeries(series);
        lineChart.addSeries(series2);
        lineChart.startAnimation();
    }
}