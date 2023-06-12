package com.namth.lab8_ph31259;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Bai2 extends AppCompatActivity implements updata,delete {
    private Studen svModel;
    ListView lv_studen;
    Context context;
    String file = "file_sv.txt";

    TextView edt_tim;

    public List<Studen> docfile_sv(String file) {
        List<Studen> list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = context.openFileInput(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            list = (List<Studen>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void ghfile_sv(Context context, String file, Studen studen) {
        List<Studen> listol = docfile_sv(file);
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(file, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            listol.add(studen);
            objectOutputStream.writeObject(listol);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    StudenSpinnerAdapter studenSpinnerAdapter;
    public static final String KEY_SVMOE = "vietnam";
    ArrayList<Studen> list = new ArrayList<>();
    ActivityResultLauncher<Intent> getDat = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String brand = intent.getStringExtra(MainActivity.KEY_BRAND);
                        String name = intent.getStringExtra(MainActivity.KEY_NAME);
                        String dress = intent.getStringExtra(MainActivity.KEY_DRESS);
                        list.add(new Studen(brand, name, dress));
                        fill();
                        Toast.makeText(Bai2.this, "Sinh viên đã được thêm vào thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> getupdeat = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1) {
                        Intent intent = result.getData();
                        if (intent != null) {

                            String name = intent.getStringExtra(MainActivity.KEY_NAME);
                            String dress = intent.getStringExtra(MainActivity.KEY_DRESS);
                            String brand = intent.getStringExtra(MainActivity.KEY_BRAND);


                            svModel.setName(name);
                            svModel.setAddress(dress);
                            svModel.setBranch(brand);
                            svModel = (Studen) intent.getSerializableExtra(KEY_SVMOE);
                            saveFile(list);
                            fill();
                        }
                    }
                }
            }
    );


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.menu_them) {
            showAddStudentDialog();
            return true;
        } else if (item.getItemId() == R.id.menu_dangxuat) {
            Intent intent = new Intent(Bai2.this, Dangnhap.class);
            startActivity(intent);
            Toast.makeText(this, "bạn đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.menu_timkiem) {
            if (edt_tim.getVisibility() == View.VISIBLE) {
                edt_tim.setVisibility(View.GONE);
            } else {
                edt_tim.setVisibility(View.VISIBLE);
            }
            edt_tim.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    studenSpinnerAdapter.getFilter().filter(charSequence);
                    lv_studen.setAdapter(studenSpinnerAdapter);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bai2_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);
        lv_studen = findViewById(R.id.lv_student);

        Toolbar toolbar = findViewById(R.id.toobal_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("HOME");
        context = this;

        List<Studen> dsdoc = new ArrayList<>();
        dsdoc.clear();
        dsdoc = docfile_sv(file);
        list.addAll(dsdoc);
        fill();
    }

    public void fill() {
        studenSpinnerAdapter =new StudenSpinnerAdapter(list,Bai2.this,Bai2.this,Bai2.this);
        lv_studen.setAdapter(studenSpinnerAdapter);
        studenSpinnerAdapter.notifyDataSetChanged();
    }



    private void showAddStudentDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_main, null);
        EditText edt_nameDN = view.findViewById(R.id.edt_nameDN);
        EditText edt_diachi = view.findViewById(R.id.edt_diachi);
        Spinner sp_school = view.findViewById(R.id.sp_school);
        Button btnsumit = view.findViewById(R.id.btn_submit);
        Spinner hien = view.findViewById(R.id.sp_school);
        ArrayList<School> listss = new ArrayList<>();
        listss.add(new School(R.mipmap.home, "hà nội"));
        listss.add(new School(R.mipmap.home, "ninh bình"));
        listss.add(new School(R.mipmap.home, "hà nam"));
        listss.add(new School(R.mipmap.home, "hải phòng"));
        SchoolSpinnerAdapter schoolSpinnerAdapter = new SchoolSpinnerAdapter(this, listss);
        hien.setAdapter(schoolSpinnerAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.show();

        btnsumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_nameDN.getText().toString();
                String diachi = edt_diachi.getText().toString();
                String ten_school = sp_school.getSelectedItem().toString();

                if (!name.isEmpty() && !diachi.isEmpty()) {
                    Studen s = new Studen(ten_school, name, diachi);
                    ghfile_sv(context, file, s);
                    list.add(s);  // Thêm sinh viên vào danh sách
                    fill();  // Cập nhật hiển thị của ListView
                    Toast.makeText(Bai2.this, "Sinh viên đã được thêm vào thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(Bai2.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveFile(List<Studen> list) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(file, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClickButtonDeleteItem(int position) {
        list.remove(position);
        saveFile(list);
        fill();
        Toast.makeText(this, "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickButtonUpdateItem(int position) {
        Intent intent = new Intent(Bai2.this, MainActivity.class);
        svModel = list.get(position);
        intent.putExtra(KEY_SVMOE, svModel);
        getupdeat.launch(intent);

    }
    public void update(int position) {

    }
}