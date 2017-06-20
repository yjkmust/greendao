package example.com.greendaotest;

import android.app.Application;
import android.content.Context;

import example.com.greendaotest.gen.DaoMaster;
import example.com.greendaotest.gen.DaoSession;

/**
 * Created by 11432 on 2017/4/15.
 */

public class DaoManger extends Application {
    private static DaoManger daoManger;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        if (daoManger==null){
            daoManger = this;
        }
    }
    /**
     * 取得DaoMaster
     */
    public static DaoMaster getDaoMaster(Context context){
        if (daoManger==null){
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,"people",null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }
    /**
     *取得DaoSession
     */
    public static DaoSession getDaoSession(Context context){
        if (daoSession==null){
            if (daoMaster==null){
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
