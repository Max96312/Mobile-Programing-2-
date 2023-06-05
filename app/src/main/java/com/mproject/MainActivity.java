
package com.mproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mproject.databinding.MainListBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private ListView addList;
   private Button btn_add;
   private EditText c_name,c_age,c_tel,c_job;
   private View dialogView;
   private AddressInfo addressInfo;
   private ArrayList<AddressInfo> alist;
   private CustomAdapter adapter;

    public void getData(){
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM address",null);
        while(cursor.moveToNext()){
            addressInfo = new AddressInfo();
            addressInfo.setName(cursor.getString(1));
            addressInfo.setAge(cursor.getString(2));
            addressInfo.setPhone(cursor.getString(3));
            addressInfo.setJob(cursor.getString(4));
            alist.add(addressInfo);
        }
        cursor.close();
        dbHelper.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);
        setTitle("주소록");






        alist = new ArrayList<AddressInfo>();
        getData();
        addList = (ListView) findViewById(R.id.listview);
        btn_add = (Button) findViewById(R.id.btn_add);

        adapter = new CustomAdapter(this);

        addList.setAdapter(adapter);



        //주소 삽입 페이지로 이동하는 버튼
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //주소 삽입으로 이동
                Intent intent = new Intent(MainActivity.this, AddAddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        //롱 클릭 시 데이터 수정
        addList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int check_position = addList.getCheckedItemPosition();
                AddressInfo addressInfo = (AddressInfo) parent.getAdapter().getItem(position);
                showDialog(alist,position);
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    //메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater minFlater = getMenuInflater();
        minFlater.inflate(R.menu.menu,menu);
        return true;
    }

    //데이터 수정 메시지 박스를 보여준다.
    private void showDialog(ArrayList<AddressInfo> alist,int position){
        dialogView = (View)View.inflate(MainActivity.this,R.layout.correction_address,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        c_name = (EditText) dialogView.findViewById(R.id.correct_name);
        c_age = (EditText) dialogView.findViewById(R.id.correct_age);
        c_tel = (EditText) dialogView.findViewById(R.id.correct_phone_number);
        c_job = (EditText) dialogView.findViewById(R.id.correct_job);

        c_name.setText(alist.get(position).getName());
        c_age.setText(alist.get(position).getAge());
        c_tel.setText(alist.get(position).getPhone());
        c_job.setText(alist.get(position).getJob());
        builder.setView(dialogView);
        builder.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

               //데이터 수정
                alist.get(position).setName(c_name.getText().toString());
                alist.get(position).setAge(c_age.getText().toString());
                alist.get(position).setPhone(c_tel.getText().toString());
                alist.get(position).setJob(c_job.getText().toString());
                addList.setAdapter(adapter); //서버시간에 따라 갱신이 늦을 수 있음
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
    //리스트 뷰와 연동한 adapter
    private class CustomAdapter extends BaseAdapter {
        Context context;

        public CustomAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return alist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = View.inflate(context, R.layout.item, null);
            TextView nameItem = itemView.findViewById(R.id.NameItem);
            TextView numItem = itemView.findViewById(R.id.AgeItem);
            TextView telItem = itemView.findViewById(R.id.TelItem);
            TextView jobItem = itemView.findViewById(R.id.JobItem);

            nameItem.setText(alist.get(position).getName());
            numItem.setText(alist.get(position).getAge());
            telItem.setText(alist.get(position).getPhone());
            jobItem.setText(alist.get(position).getJob());

            return itemView;
        }
    }

}