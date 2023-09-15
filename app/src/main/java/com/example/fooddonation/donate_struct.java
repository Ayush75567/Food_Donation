package com.example.fooddonation;

public class donate_struct {
    String fname,lname,email,address,ngo_address,contact,ftype,quantity;

//    public donate_struct(String fname,String lname,String email,String address,String ngo_address,String contact,String ftype,String quantity){
//        this.fname=fname;
//        this.lname=lname;
//        this.email=email;
//        this.address=address;
//        this.ngo_address=ngo_address;
//        this.contact=contact;
//        this.ftype=ftype;
//        this.quantity=quantity;
//    }
    public String getFname(){
        return fname;
    }
    public void setFname(String fname){
        this.fname=fname;
    }
    public String getLname(){
        return lname;
    }
    public void setLname(String lname){
        this.lname=lname;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getNgo_address(){
        return ngo_address;
    }
    public void setNgo_address(String ngo_address){
        this.ngo_address=ngo_address;
    }
    public String getContact(){
        return contact;
    }
    public void setContact(String contact){
        this.contact=contact;
    }
    public String getFtype(){
        return ftype;
    }
    public void setFtype(String ftype){
        this.ftype=ftype;
    }
    public String getQuantity(){
        return quantity;
    }
    public void setQuantity(String quantity){
        this.quantity=quantity;
    }
}
