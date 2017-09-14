package com.competition.android.competition_five.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.competition.android.competition_five.R;
import com.competition.android.competition_five.Uilt.OpenUile;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

/**
 * Created by hasee on 2017/8/18.
 */

public class HomeFragment extends Fragment {

    private SliderLayout mSliderLayout;

    private int[] imageUrl= {R.drawable.first,R.drawable.two,R.drawable.third};
    private String[] imageName = {"第一行代码","Android开发艺术探索","Android群英传"};

    public static Fragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        OpenUile.setPrimaryDark(getActivity(), R.color.darkorange);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        initSlider();
        return view;
    }


    private void initSlider() {


        for (int i=0;i<3;i++) {
            final TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView.image(imageUrl[i]);
            textSliderView.description(imageName[i]);
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.makeText(getContext(),textSliderView.getDescription(), Toast.LENGTH_SHORT).show();
                }
            });

            mSliderLayout.addSlider(textSliderView);
        }

        //默认的indicator
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //自定义的indicator
        //mSliderLayout.setCustomIndicator(indicator);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());//动画效果
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);

        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {



            }

            @Override
            public void onPageScrollStateChanged(int i) {



            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mSliderLayout.stopAutoCycle();
    }
}
