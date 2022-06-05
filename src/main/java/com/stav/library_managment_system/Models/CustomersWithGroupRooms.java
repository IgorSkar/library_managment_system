package com.stav.library_managment_system.Models;

public class CustomersWithGroupRooms {

    private int customerId;
    private int timeId;

    public CustomersWithGroupRooms(int customerId, int timeId) {
        this.customerId = customerId;
        this.timeId = timeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }
}
