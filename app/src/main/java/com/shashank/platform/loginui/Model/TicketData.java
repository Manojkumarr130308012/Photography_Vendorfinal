package com.shashank.platform.loginui.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Customer")
public class TicketData {
    @PrimaryKey(autoGenerate = true)
    private int autoid;

    @ColumnInfo(name = "CustomerID")
    private String TicketID;

    @ColumnInfo(name = "Customername")
    private String TicketNo;

    @ColumnInfo(name = "CustomerCityName")
    private String TicketDate;

    @ColumnInfo(name = "CustomerMobile")
    private String CustomerName;
    @ColumnInfo(name = "CustomerAddress")
    private String CustomerAddress;
    @ColumnInfo(name = "Description")
    private String Description;
    @ColumnInfo(name = "Priority")
    private String Priority;
    @ColumnInfo(name = "Status")
    private String Status;
    @ColumnInfo(name = "CreatedBy")
    private String CreatedBy;
    private boolean isShrink = true;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getTicketID() {
        return TicketID;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public void setTicketID(String ticketID) {
        TicketID = ticketID;
    }

    public String getTicketNo() {
        return TicketNo;
    }

    public void setTicketNo(String ticketNo) {
        TicketNo = ticketNo;
    }

    public String getTicketDate() {
        return TicketDate;
    }

    public void setTicketDate(String ticketDate) {
        TicketDate = ticketDate;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
