package com.disableperson.businfo;

import static com.disableperson.businfo.busnumsystem.RetrofitClient.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.disableperson.businfo.busnumsystem.BusnumModel;
import com.disableperson.businfo.busnumsystem.DataModel;
import com.disableperson.businfo.busnumsystem.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btn_move;
    public EditText et_driverNum;
    private String busInfor;
    Handler handler = new Handler();
    Call<BusnumModel> busCall;

    Context ctx;
    Call<DataModel> call;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ctx = this.getApplicationContext();

        et_driverNum = findViewById(R.id.et_driverNum);

        btn_move = findViewById(R.id.btn_move);

        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> input = new HashMap<>();
                busInfor = et_driverNum.getText().toString();
                //수정 완
                input.put("busNumber",busInfor);
                btn_move.setClickable(false);

                busCall = RetrofitClient.getApiService().api_post(input);
                busCall.enqueue(new Callback<BusnumModel>() {
                    @Override
                    public void onResponse(Call<BusnumModel> call, Response<BusnumModel> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this, SubActivity.class);
                            intent.putExtra("busInform", busInfor);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(MainActivity.this, SubActivity.class);
                            intent.putExtra("busInform", busInfor);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<BusnumModel> call, Throwable throwable) {
                        Intent intent = new Intent(MainActivity.this, SubActivity.class);
                        intent.putExtra("busInform", busInfor);
                        startActivity(intent);
                    }
                });
            }

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        }
        @Override
    protected void onStart() {
            btn_move.setClickable(true);

            super.onStart();
        }


}