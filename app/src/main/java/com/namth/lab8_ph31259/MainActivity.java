package com.namth.lab8_ph31259;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner sp_school;
    String chonItemSpinner;
    public  static final String KEY_NAME="name";
    public  static final String KEY_BRAND="brand";
    public  static final String KEY_DRESS="dress";
    ArrayList<School> list=new ArrayList<>();
    public void hienthi( Spinner s){


        list.add(new School(R.mipmap.home,"hà nội"));
        list.add(new School(R.mipmap.home,"ninh bình"));
        list.add(new School(R.mipmap.home,"hà nam"));
        list.add(new School(R.mipmap.home,"hải phòng"));
        SchoolSpinnerAdapter schoolSpinnerAdapter=new SchoolSpinnerAdapter(this,list);
       s.setAdapter(schoolSpinnerAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText edt_nameDN=findViewById(R.id.edt_nameDN);
        EditText edt_diachi=findViewById(R.id.edt_diachi);
       sp_school=findViewById(R.id.sp_school);
        Button btn_submit=findViewById(R.id.btn_submit);
    hienthi(sp_school);


        Studen svmold = (Studen) getIntent().getSerializableExtra(Bai2.KEY_SVMOE);
                                            //getSerializableExtra


        if (svmold != null) {
            edt_nameDN.setText(svmold.getName());
            edt_diachi.setText(svmold.getAddress());

            int position = -1;
            for (int i = 0; i < list.size(); i++) {
                if (svmold.getBranch().equals(list.get(i).getTen())) {
                    position = i;
                    break;
                }
            }
            if (position != -1) {
                sp_school.setSelection(position);
            }
        }

        sp_school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chonItemSpinner=((School) sp_school.getItemAtPosition(i)).getTen();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
btn_submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this, Bai2.class);
        Bundle bundle=new Bundle();
        bundle.putString(KEY_BRAND,chonItemSpinner);
        bundle.putString(KEY_NAME, edt_nameDN.getText().toString());
       bundle.putString(KEY_DRESS, edt_diachi.getText().toString());
        intent.putExtras(bundle);
        setResult(1,intent);
        finish();
    }
});


    }
}