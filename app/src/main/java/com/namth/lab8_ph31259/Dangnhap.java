package com.namth.lab8_ph31259;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Dangnhap extends AppCompatActivity {

    String file = "fileuser.txt";
    EditText edtname ;
    EditText edtpass ;
    List<user> listac=new ArrayList<>();
    CheckBox chk_ghntk ;
    public List<user> readUser(Context context, String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = context.openFileInput(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        List<user> userList = (List<user>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return userList;
    }
public void remember(String ten,String mk,boolean chkRemeber){
    SharedPreferences sharedPreferences=getSharedPreferences("remenber",MODE_PRIVATE);
    SharedPreferences.Editor editor=sharedPreferences.edit();
    editor.putString("user",ten);
    editor.putString("pass",mk);
    editor.putBoolean("chkremenber",chkRemeber);
    editor.apply();
}
public void checkremenber(){
        SharedPreferences sharedPreferences=getSharedPreferences("remenber",MODE_PRIVATE);
        String user=sharedPreferences.getString("user","");
        String pass=sharedPreferences.getString("pass","");

        boolean chRemenber1=sharedPreferences.getBoolean("chkremenber",false);
        chk_ghntk.setChecked(chRemenber1);
        if(chk_ghntk.isChecked()){
            edtname.setText(user);
            edtpass.setText(pass);
        }

}




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        Context context = this;


        edtname = findViewById(R.id.edtname_signin);
        edtpass = findViewById(R.id.edtpass_signin);
        chk_ghntk=findViewById(R.id.chk_ghntk);

        Button btndangnhap = findViewById(R.id.btndangnhap);
        Button btndangky = findViewById(R.id.btndangky);
        String name=edtname.getText().toString();
        String pass=edtpass.getText().toString();
        boolean check=chk_ghntk.isChecked();
        List<user> list=new ArrayList<>();
        list.clear();
        try {
            list=(List<user>) readUser(context,"fileuser.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        listac.addAll(list);
        checkremenber();




        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dangnhap.this, Dangki.class));
                finish();
            }
        });

        btndangnhap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               int checkfile=0, checkremenber=1;


                String name = edtname.getText().toString();
                String pass = edtpass.getText().toString();
                if (name.trim().isEmpty()) {
                    Toast.makeText(Dangnhap.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pass.trim().isEmpty()) {
                    Toast.makeText(Dangnhap.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        List<user>userList=readUser(context,file);

                        for (user user : userList) {
                            if (user.getName().equals(name) && user.getPass().equals(pass)) {
                               checkfile=1;
                                break;
                            }
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }


                    for (int i=0;i<listac.size();i++){
                        if(name.equals(listac.get(i).getName())&&pass.equals(listac.get(i).getPass())){
                            checkremenber=1;
                            break;

                        }
                    }
                        if(checkfile==1&&checkremenber==1){
                            remember(name,pass,chk_ghntk.isChecked());
                            Intent intent=new Intent(Dangnhap.this,Bai2.class);
                            startActivity(intent);
                        }
                }





            }

        });
    }
}
