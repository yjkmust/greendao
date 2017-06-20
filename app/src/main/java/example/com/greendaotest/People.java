package example.com.greendaotest;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 11432 on 2017/4/15.
 */
@Entity
public class People {
    @org.greenrobot.greendao.annotation.Id(autoincrement = true)
    private Long Id;
    @Property
    private String Name;
    @Property
    private String Sex;
    @Property
    private String Phone;
    @Property
    private String Age;
    @Keep
    public People(Long Id, String Name, String Sex, String Phone, String Age) {
        this.Id = Id;
        this.Name = Name;
        this.Sex = Sex;
        this.Phone = Phone;
        this.Age = Age;
    }
    @Keep
    public People() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getName() {
        return this.Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getSex() {
        return this.Sex;
    }
    public void setSex(String Sex) {
        this.Sex = Sex;
    }
    public String getPhone() {
        return this.Phone;
    }
    public void setPhone(String  Phone) {
        this.Phone = Phone;
    }
    public String getAge() {
        return this.Age;
    }
    public void setAge(String Age) {
        this.Age = Age;
    }


}
