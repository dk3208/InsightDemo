package com.example.insightdemo;

import java.util.ArrayList;

import android.app.Activity;


public class AccountManager {
	
	
	private ArrayList<Account> list = new ArrayList<Account>();
	private Account current = null;
	
	
	public AccountManager()
	{
		Account default_account = new Account("111", "111", AccountStatus.LOGOFF, AccountType.BOOKING);
		AddAccount(default_account);
	}
	
	public void SetCurrent(Account a)
	{
		//log off the previous account first
		if(current != null)
			current.SetStatus(AccountStatus.LOGOFF);
		//log in current
		current = a;
		current.SetStatus(AccountStatus.LOGIN);
	}
	
	public void LogoffCurrent()
	{
		if(current != null)
			current.SetStatus(AccountStatus.LOGOFF);
		current = null;
	}
	
	public boolean AddAccount(Account a)
	{
		for(Account acc : list)
		{
			if(acc.MatchID(a.GetID()))
			{
				return false;
			}
		}
		list.add(a);
		return true;
	}
	
	public AccountStatus GetCurrentStatus()
	{
		if(current == null)
			return AccountStatus.NONE;
		
		return current.GetStatus();
	}
	
	public String CreateMembershipID()
	{
		while(true)
		{
			int number = (int)(Math.random()*(999 - 100 + 1)) + 100;
			String s = String.valueOf(number);
			boolean used = false;
			
			for(Account a : list)
			{
				if(a.MatchID(s))
				{
					used = true;
				}
			}
			if(!used)
				return s;
		}
	}
	
	public AccountType GetCurrentType()
	{
		if(current == null)
			return AccountType.NONE;
		
		return current.GetType();
	}
	
	public Account FindAccount(String id, String pw)
	{
		Account found = null;
		
		for(Account acc : list)
		{
			if(acc.MatchID(id) && acc.MatchPW(pw))
				found = acc;
		}
		
		return found;
	}
}
