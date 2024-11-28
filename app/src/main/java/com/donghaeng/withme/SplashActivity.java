package com.donghaeng.withme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.donghaeng.withme.roomdatabase.guide.GuideBook;
import com.donghaeng.withme.roomdatabase.guide.GuideBookDatabase;
import com.donghaeng.withme.roomdatabase.guide.GuideBookRepository;
import com.donghaeng.withme.screen.start.StartActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private GuideBookDatabase guideBookDatabase;
    private GuideBookRepository guideBookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        // 최초 실행 여부 확인
        checkFirstRun();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }); */

    private void checkFirstRun() {
        guideBookDatabase = GuideBookDatabase.getInstance(this);
        if (AppLaunchChecker.isFirstRun(this)) {
            Toast.makeText(this, "앱 최초 실행", Toast.LENGTH_SHORT).show();
            // 필요한 초기 설정 작업 수행
            downloadGuideBooks();
        }
    }
    private void downloadGuideBooks() {
        FirebaseFirestore externalDatabase = FirebaseFirestore.getInstance();
        guideBookRepository = new GuideBookRepository(this);

        externalDatabase.collection("app_guide_book")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            // 중복 검사 => 보류 : 모두 조회 = 속도 저하
//                            if(document.get("title", String.class).equals(guideBookRepository.getAppGuides().get(0).getTitle())) {}
                            GuideBook guideBook = document.toObject(GuideBook.class);
                            guideBookRepository.insert(guideBook);
                        }
                    }
                });
        externalDatabase.collection("smartphone_guide_book")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            GuideBook guideBook = document.toObject(GuideBook.class);
                            guideBookRepository.insert(guideBook);
                        }
                    }
                });
        externalDatabase.collection("controller_instruction")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            GuideBook guideBook = document.toObject(GuideBook.class);
                            guideBookRepository.insert(guideBook);
                        }
                    }
                });
    }
}
