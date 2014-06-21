package com.example.insightdemo;


enum AccountStatus {
	LOGIN,
	LOGOFF,
	NONE
}

enum AccountType {
	BOOKING,
	NONBOOKING,
	NONE
}

public class Account {
	
	private AccountStatus status;
	private AccountType type;
	private String id, pw;
	
	public AccountType GetType()
	{
		return type;
	}
	public void SetStatus(AccountStatus s)
	{
		status = s;
	}
	public AccountStatus GetStatus()
	{
		return status;
	}
	
	public String GetID()
	{
		return id;	
	}
	public Account(String id_, String pw_, AccountStatus s, AccountType t)
	{
		id = id_;
		pw = pw_;
		status = s;
		type = t;
 	}
	
	public boolean MatchPW(String pw_)
	{
		return pw.equals(pw_);
	}
	
	public boolean MatchID(String id_)
	{
		return id.equals(id_);
	}
}
