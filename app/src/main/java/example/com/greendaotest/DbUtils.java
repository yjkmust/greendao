package example.com.greendaotest;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import example.com.greendaotest.gen.DaoMaster;
import example.com.greendaotest.gen.DaoSession;
import example.com.greendaotest.gen.PeopleDao;

/**
 * Created by 11432 on 2017/4/15.
 */

public class DbUtils {
    private static final String TAG = DbUtils.class.getSimpleName();
    private static DbUtils dbUtils;
    private static Context context;
    private static PeopleDao peopleDao ;
    private static DaoSession daoSession;
    private DbUtils(){

    }
    /**
     * 采用单列模式
     */
    public static DbUtils getDbUtils(Context context){
        if (dbUtils==null){
            dbUtils = new DbUtils();
            if (context == null){
                context = context.getApplicationContext();
            }
            dbUtils.daoSession = DaoManger.getDaoSession(context);
            dbUtils.peopleDao = dbUtils.daoSession.getPeopleDao();

        }
        return dbUtils;
    }
    /**
     * 根据用户ID，取出用户信息
     */
    public People loadPeople(long id){
      return peopleDao.load(id);
    }
    /**
     * 取出所有数据
     */
    public List<People> loadPeoples(){
        return peopleDao.loadAll();
    }
    /**
     * 生成ID排倒叙的表
     */
    public List<People> queryPeople(String where, String...param){
        return peopleDao.queryRaw(where, param);
    }
    /**
     * 根据用户信息，修改信息
     */
    public long ChangePeople(People user){
        return peopleDao.insertOrReplace(user);
    }
    public void ChangePeoples(final List<People> list){
        if (list==null||list.isEmpty()){
            return;
        }
        peopleDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<list.size();i++){
                    People people = list.get(i);
                    peopleDao.insertOrReplace(people);
                }
            }
        });
    }
    /**
     * 删除所有数据
     */
    public void deleteAll(){
        peopleDao.deleteAll();
    }
    /**
     * 根据ID删除数据
     */
    public void deleteOne(Long id){
        peopleDao.deleteByKey(id);
        Log.i(TAG,"delete");
    }
    /**
     * 根据用户类删除信息
     */
    public void deletePeople(People people){
        peopleDao.delete(people);
    }

}
