package com.example.manageofleague;

public class UserAccount {

    private String idToken; // Firebase Uid 고유ID
    private String emailId; // 사용자 1인에 1개. 가지는   이메일
    private String password;

    public UserAccount() {

    }

    public String getIdToken() { return  idToken;}

    public void setIdToken(String idToken){
        this.idToken = idToken;
    }

    public String getEmailId(){
        return emailId;
    }

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
