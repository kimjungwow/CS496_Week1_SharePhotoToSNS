package com.example.myapplication;


public class ContactsListviewItem {
    private String name;
    private String phoneNumber;

    public String getName() {
        return name;
    };
    public void setName(String newName){
        this.name=newName;
    };

    public String getPhoneNumber(){
        return phoneNumber;
    };
    public void setPhoneNumber(String newPhoneNumber){
        this.phoneNumber=newPhoneNumber;
    };
}
