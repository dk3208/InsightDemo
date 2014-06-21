package com.example.insightdemo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	static private Pattern pattern = null;
	static private Matcher matcher = null;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	 
	private static final String PHONE_PATTERN = 
			"-?\\d+(\\.\\d+)?";

	private static final String NAME_PATTERN = 
			"\\w+";
	
	static public boolean ValidateEmail(final String email)
	{
		pattern = Pattern.compile(EMAIL_PATTERN);	
		matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
	
	static public boolean ValidatePhone(final String phone)
	{
		pattern = Pattern.compile(PHONE_PATTERN);	
		matcher = pattern.matcher(phone);
		
		return matcher.matches();
	}
	
	static public boolean ValidateName(final String name)
	{
		pattern = Pattern.compile(NAME_PATTERN);	
		matcher = pattern.matcher(name);
		
		return matcher.matches();
	}
}
