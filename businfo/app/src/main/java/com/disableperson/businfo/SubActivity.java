package com.disableperson.businfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.disableperson.businfo.busnumsystem.DataModel;
import com.disableperson.businfo.busnumsystem.RetrofitClient;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.speech.tts.TextToSpeech;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    private Button btn_back;
    private EditText et_busNum ;
    private String busInform;
    Call<DataModel> call;
    private TextToSpeech tts;
    private Timer timer;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub1);
        String busInform  = getIntent().getStringExtra("busInform");

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

//        TextView textBusNum = (TextView) findViewById(R.id.et_busNum);
//        textBusNum.setText(busInform);



        btn_back = findViewById(R.id.btn_back);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                call = RetrofitClient.getApiService().api_get(busInform); //수정 get or get2
                call.enqueue(new Callback<DataModel>() {
                    @Override
                    public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                        if (response.isSuccessful()) {
                            DataModel result = response.body();
                            String str;
                            String full_tts="";
                            String text_tts = "에서 시각장애인 승차 예정입니다";
                            String final_tts="";
                            str = result.getAlarm();
                            if (str.equals("1")){
                                full_tts = result.getBusStopName();
                                System.out.println(full_tts);
                                final_tts = full_tts + text_tts;
                                tts.setPitch(1.0f);
                                tts.setSpeechRate(1.0f);
                                tts.speak(final_tts, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DataModel> call, Throwable t) {

                    }
                });

            }
        };

        timer = new Timer();
        timer.schedule(tt, 0, 5000);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onPause() {
        timer.cancel();

        super.onPause();
    }
}
