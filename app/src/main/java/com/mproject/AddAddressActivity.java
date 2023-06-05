package com.mproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAddressActivity extends AppCompatActivity {
    private EditText etName,etAge,etTel,etJob;
    private Button btn_insert,btn_list;
    private AddressInfo addressInfo;

    public void insertData(){
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("INSERT INTO address(name,age,tel,job) VALUES("+"'"+addressInfo.getName()+"','"+addressInfo.getAge()+"','"+addressInfo.getPhone()+"','"+addressInfo.getJob()+"')");
        dbHelper.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("주소록");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address);
        etName=(EditText) findViewById(R.id.name);
        etAge=(EditText) findViewById(R.id.age);
        etTel=(EditText) findViewById(R.id.phone_number);
        etJob=(EditText) findViewById(R.id.job);

        btn_insert=(Button)findViewById(R.id.btn_enter);
        btn_list=(Button) findViewById(R.id.btn_goList);
        //저장 버튼 눌렀을때
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String phone = etTel.getText().toString();
                String job = etJob.getText().toString();

                if (name.length()==0){
                    showDialog("이름을 입력하세요!");
                }
                else if (age.length()==0){
                    showDialog("나이를 입력하세요~");
                }
                else if (phone.length()==0){
                    showDialog("전화 번호를 입력하세요~");
                }
                else if(job.length()==0){
                    showDialog("직업을 입력하세요~");
                }
                else{

                    addressInfo = new AddressInfo();
                    addressInfo.setName(name);
                    addressInfo.setAge(age);
                    addressInfo.setPhone(phone);
                    addressInfo.setJob(job);
                    insertData();
                    //화면 전환
                    Intent intent = new Intent(AddAddressActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        //메인 화면으로 돌아가는 버튼의 리스너 구현
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAddressActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    //이벤트 발생시 메시지를 보여준다.
    private void showDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddAddressActivity.this);
        builder.setTitle("주의");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}