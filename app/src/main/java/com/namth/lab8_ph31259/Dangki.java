package com.namth.lab8_ph31259;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Dangki extends AppCompatActivity {
    public static String KEY_USERNAME = "username";
    public static String KEY_PASSWORD = "password";

    String file = "fileuser.txt";

    public void writeUser(Context context, String fileName, user users) throws IOException {
        List<user> list = new ArrayList<>();

        FileInputStream fileInputStream = context.openFileInput(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            list = (List<user>) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        objectInputStream.close();
        fileInputStream.close();

        list.add(users);

        FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        Context context = this;

        EditText edtname_signup = findViewById(R.id.edtname_signup);
        EditText edtpass_singup = findViewById(R.id.edtpass_Signup);
        EditText edtnhaplai = findViewById(R.id.edtnhaplai_signup);
        Button btndangki_signup = findViewById(R.id.btndangky_signup);

        btndangki_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtname_signup.getText().toString();
                String password = edtpass_singup.getText().toString();
                String nhaplai = edtnhaplai.getText().toString();
                if (username.trim().isEmpty()) {
                    Toast.makeText(Dangki.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
                } else if (password.trim().isEmpty()) {
                    Toast.makeText(Dangki.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (nhaplai.trim().isEmpty()) {
                    Toast.makeText(Dangki.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (!nhaplai.equals(password)) {
                    Toast.makeText(Dangki.this, "Mật khẩu nhập lại sai", Toast.LENGTH_SHORT).show();
                } else {
                    user themuser = new user(username, password);
                    try {
                        writeUser(context, file, themuser);
                        Toast.makeText(Dangki.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intent = new Intent(Dangki.this, Dangnhap.class);

                    startActivity(intent);
                }
            }
        });
    }
}
