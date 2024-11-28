package com.donghaeng.withme.screen.main;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.ViewGroup;

import com.donghaeng.withme.R;

import com.donghaeng.withme.screen.start.connect.TargetConnectFragment;

public class TargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_target);

        // Fragment 초기화 로직을 분리
        if (savedInstanceState == null && getIntent().getStringExtra("fragmentName") != null) {
            if(getIntent().getStringExtra("fragmentName").equals("target_QR")){
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new TargetConnectFragment())
                        .commit();
            }
        }
        else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new TargetMainFragment())
                    .commit();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.topMargin = systemBars.top;
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 뒤로가기 애니메이션 설정
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}