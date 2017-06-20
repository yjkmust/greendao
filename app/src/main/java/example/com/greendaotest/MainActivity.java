package example.com.greendaotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OneTableDialogFragment.addUserOnClickListener,oneTableItemDialogFragment.EditUserOnClickListener{
    private Toolbar toolbar;
    private ListView lv_oneTable;
    private List<People> list_people;
    private onetable_adapter adapter;
    private DbUtils db;
    private oneTableItemDialogFragment oneItemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("Hello GreenDao");
        setSupportActionBar(toolbar);
        db = DbUtils.getDbUtils(this);
        initControls();
        initData();

    }
    /**
     * 初始化数据，刚进入页面时加载
     */
     private void initData(){
         list_people = new ArrayList<>();
         list_people = db.loadPeoples();
         adapter = new onetable_adapter(this,list_people);
         lv_oneTable.setAdapter(adapter);
     }
    /**
     * c初始化控件
     */
    private void initControls(){
        lv_oneTable =(ListView) findViewById(R.id.lv_oneTable);
        lv_oneTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,list_people.get(position).getName()+".."
               +list_people.get(position).getId(),Toast.LENGTH_LONG ).show();
                oneItemDialog = new oneTableItemDialogFragment(list_people.get(position).getId(),position);
                oneItemDialog.show(getFragmentManager(),"编辑");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if (id==R.id.menu_add){
            OneTableDialogFragment oneDialog = new OneTableDialogFragment(0,"","","","",0);
            oneDialog.show(getFragmentManager(),"添加用户");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onAddUserOnClick(long uId,String uName, String uSex, String uAge, String uTel,int flag) {
        People user = new People();
        if(flag==1) {
            user.setId(uId);
        }
        user.setSex(uSex);
        user.setPhone(uTel);
        user.setAge(uAge);
        user.setName(uName);
        if(flag==0) {
            if (db.ChangePeople(user)> 0) {
                list_people.add(0, user);
                adapter.notifyDataSetChanged();
            }
        }else
        {
            if (db.ChangePeople(user)> 0) {

                int num = 0;
                for(People u:list_people)
                {
                    if(u.getId()==uId)
                    {
                        list_people.remove(num);
                        list_people.add(num,user);

                        break;
                    }
                    num++;
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onEditUserOnClick(long id,int postion,int flag) {
        if(flag==0) {
            db.deleteOne(id);
            list_people.remove(postion);
            adapter.notifyDataSetChanged();
            oneItemDialog.dismiss();
        }else
        {
            People updateUser = db.loadPeople(id);
            OneTableDialogFragment oDialog = new OneTableDialogFragment(updateUser.getId(),updateUser.getName(),updateUser.getSex(),
                    updateUser.getAge() , updateUser.getPhone(),1);
            oneItemDialog.dismiss();
            oDialog.show(getFragmentManager(),"修改");
        }


    }
}
