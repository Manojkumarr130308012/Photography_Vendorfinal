package com.shashank.platform.loginui.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Customer")
public class CustomerData {
    @PrimaryKey(autoGenerate = true)
    private int autoid;

    @ColumnInfo(name = "Customername")
    private String Customername;

    @ColumnInfo(name = "CustomerCityName")
    private String CustomerCityName;

    @ColumnInfo(name = "CustomerMobile")
    private String CustomerMobile;

    @ColumnInfo(name = "CustomerOSAmount")
    private String CustomerOSAmount;


    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }


    public String getCustomername() {
        return Customername;
    }

    public void setCustomername(String customername) {
        Customername = customername;
    }

    public String getCustomerCityName() {
        return CustomerCityName;
    }

    public void setCustomerCityName(String customerCityName) {
        CustomerCityName = customerCityName;
    }

    public String getCustomerMobile() {
        return CustomerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        CustomerMobile = customerMobile;
    }

    public String getCustomerOSAmount() {
        return CustomerOSAmount;
    }

    public void setCustomerOSAmount(String customerOSAmount) {
        CustomerOSAmount = customerOSAmount;
    }
}
