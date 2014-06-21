package com.example.insightdemo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnItemClickListener {
	
	public AccountManager mgr = new AccountManager();
	private Context context = this;
	private boolean login = false;
	static WelcomeFragment welcomeFrg = new WelcomeFragment(); 
	static  MyroomFragment myroomFrg = new MyroomFragment();
	private MessageFragment messageFrg = new MessageFragment();
	private ViewFlipper flipper = null;
	
	public static String messStr = "";
	public static boolean bNewMess = false;
	public static List<String> top250 = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar);
		
		welcomeFrg.SetAccountMgr(mgr);
		flipper = (ViewFlipper) findViewById(R.id.flipper1);
		flipper.startFlipping();
		
		
		TextView view = (TextView) findViewById(R.id.textHomeLink);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strURL1 = "http://tw.yahoo.com";
				Intent ie = new Intent(Intent.ACTION_VIEW, Uri.parse(strURL1));
				startActivity(ie);
			}
		});

		final Button btn = (Button) findViewById(R.id.button1);

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
				
				if (!welcomeFrg.isVisible()) {
					 
		            ft.replace(R.id.container, welcomeFrg, "detailFragment");
		            btn.setBackgroundResource(R.drawable.droidmore);
		            flipper.stopFlipping();
				} else {
					ft.remove(welcomeFrg);				
					btn.setBackgroundResource(R.drawable.droidmenu);
					flipper.startFlipping();
				}
				ft.commit();
			}
		});
		
		//NFC
		Intent sendIntent = new Intent();	    
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Control");
		sendIntent.setType("pkinno/odin");
		startActivityForResult(sendIntent, 0);
		
		// disable parts of items for non-login user
		if (!login) {
			Log.d("info", "grey out items");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click

		return super.onOptionsItemSelected(item);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}	
	
	public void showWelcome()
	{
		
		Fragment currentFragment = getFragmentManager().findFragmentByTag("unlockFragment");
	    FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
	    fragTransaction.detach(currentFragment);
	    fragTransaction.attach(currentFragment);
	    fragTransaction.commit();
	    
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.welcome_home);
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){ 
            @Override 
            public void onDismiss(DialogInterface dialog) { 
            	jumpMyRoomPage();
            } 
        }); 
		dialog.show();

	}
	
	public void showAccessDenied()
	{
		Fragment currentFragment = getFragmentManager().findFragmentByTag("unlockFragment");
	    FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
	    fragTransaction.detach(currentFragment);
	    fragTransaction.attach(currentFragment);
	    fragTransaction.commit();
	    
	    Dialog dialog = new Dialog(this);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.access_denied);
		
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){ 
            @Override 
            public void onDismiss(DialogInterface dialog) { 
            	jumpMyRoomPage();
            } 
        }); 
		
		dialog.show();
		
	}
	
	public void showLoginDialog(final Button _btn)
	{
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.login_pop);
		dialog.setTitle("Login...");
		
		Button CloseButton = (Button) dialog.findViewById(R.id.btn_popClose);
		CloseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		final Button SigninButton = (Button) dialog.findViewById(R.id.btn_popSignin);
		SigninButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		
				//String strURL1 = "http://tw.yahoo.com";
				//Intent ie = new Intent(Intent.ACTION_VIEW,Uri.parse(strURL1));
				//startActivity(ie);
				String id = ((EditText) dialog.findViewById(R.id.et_popMS)).getText().toString();
				String pw = ((EditText) dialog.findViewById(R.id.et_popPW)).getText().toString();
				Account found = mgr.FindAccount(id, pw);
				if (found != null)
				{
					mgr.SetCurrent(found);
					_btn.setText("Sign Out");  

					dialog.dismiss();
				}
				else
				{
					new AlertDialog.Builder(MainActivity.this)
			        .setTitle("Error").setMessage("Wrong password?")
			        .setPositiveButton("OK",
			         new DialogInterface.OnClickListener() {
			         public void onClick(DialogInterface dialog, int which) {
			          }
			          }).show();
					dialog.dismiss();
					return;
				}
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
				
				if (welcomeFrg.isVisible()) {
					ft.remove(welcomeFrg);
					
				}
				ft.replace(R.id.container, welcomeFrg, "detailFragment");
				ft.commit();
			}
		});
		Button JoinButton = (Button) dialog.findViewById(R.id.btn_popJoinUs);
		JoinButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				final Dialog JoinDlg = new Dialog(context);
				JoinDlg.setContentView(R.layout.register1);
				JoinDlg.setTitle("Join Page...");
				Button regCloseButton = (Button) JoinDlg.findViewById(R.id.btn_regclose);
				regCloseButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						JoinDlg.dismiss();
					}
				});

				JoinDlg.show();
				Button regreJoinButton = (Button) JoinDlg.findViewById(R.id.btn_regjoin);
				regreJoinButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						// custom dialog
						final Dialog MessDlg = new Dialog(context);
						MessDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
						MessDlg.setContentView(R.layout.register2);
						MessDlg.setCancelable(false);
						Button ConfirmButton = (Button) MessDlg.findViewById(R.id.nConfirm);
						TextView tvID = (TextView)MessDlg.findViewById(R.id.reg_memnum_show);
						String id = mgr.CreateMembershipID();
						tvID.setText(id);
						
						JoinDlg.dismiss();
						dialog.dismiss();
						
						MessDlg.show();
						
						ConfirmButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								
								String pw = ((EditText)MessDlg.findViewById(R.id.nPassword)).getText().toString();
								String con_pw = ((EditText)MessDlg.findViewById(R.id.nConfiPass)).getText().toString();
								
								if(pw.isEmpty() || !pw.equals(con_pw))
								{
									new AlertDialog.Builder(MainActivity.this)
							        .setTitle("Error").setMessage("Please check your input")
							        .setPositiveButton("OK",
							         new DialogInterface.OnClickListener() {
							         public void onClick(DialogInterface dialog, int which) {
							          }
							          }).show();
									return;
								}
								
								String newMemberID = ((TextView)MessDlg.findViewById(R.id.reg_memnum_show)).getText().toString();
								
								Account a = new Account(newMemberID, pw, AccountStatus.LOGIN, AccountType.NONBOOKING);			
								mgr.AddAccount(a);
								mgr.SetCurrent(a);
								
								MessDlg.dismiss();
								
								
								FragmentTransaction ft = getFragmentManager().beginTransaction();
								ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
								
								if (welcomeFrg.isVisible()) {
								
									ft.remove(welcomeFrg);				
									flipper.startFlipping();
								}
								ft.commit();
							}
						});
					}
				});
			}
		});
		

		dialog.show();
	
	}

	public void jumpMyRoomPage(){
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		
		if (welcomeFrg.isVisible()) {
			ft.remove(welcomeFrg);
		}
		
		if (!myroomFrg.isVisible()) {
			  
            ft.replace(R.id.container, myroomFrg,  "myroomFragment");
		}
		
		ft.commit();
    }
	
	//myroom page
	public static class MyroomFragment extends Fragment {

		public Context mycontext;
		public MyroomFragment() {
			//mycontext = this.getc;
		}
		
		@Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        
	        
	    }  
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.myroom_page_1, container,
					false);
			mycontext = rootView.getContext();
			rootView.setBackgroundColor(Color.BLACK);
			Button unlock = (Button)rootView.findViewById(R.id.btn2);
			unlock.setOnClickListener(new OnClickListener() {

				  public void onClick(View arg0) {
					  
					  FragmentTransaction ft = getFragmentManager().beginTransaction();
					  UnlockFragment unlockFrg = new UnlockFragment();
					    ft.replace(R.id.container, unlockFrg,  "unlockFragment");
					
					
					ft.commit();
				  }
			});
			
			Button instanthelp = (Button)rootView.findViewById(R.id.btn6);
			instanthelp.setOnClickListener(new OnClickListener() {

				  public void onClick(View arg0) {
					  
					// custom dialog
					final Dialog JoinDlg = new Dialog(mycontext);
					JoinDlg.setContentView(R.layout.instant_help1);
					//JoinDlg.setTitle("Join Page...");
					
					JoinDlg.show();
					
					Button button1 = (Button)JoinDlg.findViewById(R.id.button1);
					button1.setOnClickListener(new OnClickListener() {

						  public void onClick(View arg0) {
							  
							// custom dialog
							final Dialog JoinDlg2 = new Dialog(mycontext);
							JoinDlg2.setContentView(R.layout.instant_help2);
							
							JoinDlg2.show();
							
							Button button1 = (Button)JoinDlg2.findViewById(R.id.button1);
							button1.setOnClickListener(new OnClickListener() {

								  public void onClick(View arg0) {
									  
									// custom dialog
									final Dialog JoinDlg3 = new Dialog(mycontext);
									JoinDlg3.setContentView(R.layout.instant_help3);
									
									JoinDlg3.show();
									
									Button button1 = (Button)JoinDlg3.findViewById(R.id.button1);
									button1.setOnClickListener(new OnClickListener() {

										  public void onClick(View arg0) {
											  
											  messStr = "This additional request will charge you $$$."+
													  	"Your request will be completed in XX mins.";
											  if (messStr != "")
													bNewMess = true;
											// custom dialog
											final Dialog JoinDlg4 = new Dialog(mycontext);
											JoinDlg4.setContentView(R.layout.successed);
											
											JoinDlg4.show();
											JoinDlg3.dismiss();
											JoinDlg2.dismiss();
											JoinDlg.dismiss();
										  }
									});
									
									Button button2 = (Button)JoinDlg3.findViewById(R.id.button2);
									button2.setOnClickListener(new OnClickListener() {

										  public void onClick(View arg0) {
											  JoinDlg2.dismiss();
												JoinDlg.dismiss();
											  JoinDlg3.dismiss();
										  }
									});
								  }
							});
							
							Button button2 = (Button)JoinDlg2.findViewById(R.id.button2);
							button2.setOnClickListener(new OnClickListener() {

								  public void onClick(View arg0) {
									  
									JoinDlg2.dismiss();
									JoinDlg.dismiss();
								  }
							});
						  }
					});
					
					Button button2 = (Button)JoinDlg.findViewById(R.id.button2);
					button2.setOnClickListener(new OnClickListener() {

						  public void onClick(View arg0) {
							  
							// custom dialog
							final Dialog JoinDlg2 = new Dialog(mycontext);
							JoinDlg2.setContentView(R.layout.instant_help2);
							
							JoinDlg2.show();
							
							Button button1 = (Button)JoinDlg2.findViewById(R.id.button1);
							button1.setOnClickListener(new OnClickListener() {

								  public void onClick(View arg0) {
									  
									// custom dialog
									final Dialog JoinDlg3 = new Dialog(mycontext);
									JoinDlg3.setContentView(R.layout.instant_help3);
									
									JoinDlg3.show();
									
									Button button1 = (Button)JoinDlg3.findViewById(R.id.button1);
									button1.setOnClickListener(new OnClickListener() {

										  public void onClick(View arg0) {
											  
											  messStr = "This additional request will charge you $$$."+
													  	"Your request will be completed in XX mins.";
											  if (messStr != "")
													bNewMess = true;
											// custom dialog
											final Dialog JoinDlg4 = new Dialog(mycontext);
											JoinDlg4.setContentView(R.layout.successed);
											
											JoinDlg4.show();
											JoinDlg3.dismiss();
											JoinDlg2.dismiss();
											JoinDlg.dismiss();
										  }
									});
									
									Button button2 = (Button)JoinDlg3.findViewById(R.id.button2);
									button2.setOnClickListener(new OnClickListener() {

										  public void onClick(View arg0) {
											  
											JoinDlg2.dismiss();
											JoinDlg.dismiss();
											JoinDlg3.dismiss();
										  }
									});
								  }
							});
							
							Button button2 = (Button)JoinDlg2.findViewById(R.id.button2);
							button2.setOnClickListener(new OnClickListener() {

								  public void onClick(View arg0) {
									  
										JoinDlg2.dismiss();
										JoinDlg.dismiss();
								  }
							});
						  }
					});
					
					Button button3 = (Button)JoinDlg.findViewById(R.id.button3);
					button3.setOnClickListener(new OnClickListener() {

						  public void onClick(View arg0) {
							  
							// custom dialog
							final Dialog JoinDlg2 = new Dialog(mycontext);
							JoinDlg2.setContentView(R.layout.instant_help2);
							
							JoinDlg2.show();
							
							Button button1 = (Button)JoinDlg2.findViewById(R.id.button1);
							button1.setOnClickListener(new OnClickListener() {

								  public void onClick(View arg0) {
									  
									// custom dialog
									final Dialog JoinDlg3 = new Dialog(mycontext);
									JoinDlg3.setContentView(R.layout.instant_help3);
									
									JoinDlg3.show();
									
									Button button1 = (Button)JoinDlg3.findViewById(R.id.button1);
									button1.setOnClickListener(new OnClickListener() {

										  public void onClick(View arg0) {
											  
											  messStr = "This additional request will charge you $$$."+
													  	"Your request will be completed in XX mins.";
											  if (messStr != "")
													bNewMess = true;
											// custom dialog
											final Dialog JoinDlg4 = new Dialog(mycontext);
											JoinDlg4.setContentView(R.layout.successed);
											
											JoinDlg2.dismiss();
											JoinDlg.dismiss();
										JoinDlg3.dismiss();
											JoinDlg4.show();
										  }
									});
									
									Button button2 = (Button)JoinDlg3.findViewById(R.id.button2);
									button2.setOnClickListener(new OnClickListener() {

										  public void onClick(View arg0) {
											  
												JoinDlg2.dismiss();
												JoinDlg.dismiss();
											JoinDlg3.dismiss();
										  }
									});
								  }
							});
							
							Button button2 = (Button)JoinDlg2.findViewById(R.id.button2);
							button2.setOnClickListener(new OnClickListener() {

								  public void onClick(View arg0) {
										JoinDlg2.dismiss();
										JoinDlg.dismiss(); 
								  }
							});
						  }
					});
					
					Button button4 = (Button)JoinDlg.findViewById(R.id.button4);
					button4.setOnClickListener(new OnClickListener() {

						  public void onClick(View arg0) {
							  
							// custom dialog
							final Dialog JoinDlg2 = new Dialog(mycontext);
							JoinDlg2.setContentView(R.layout.instant_help2);
							
							JoinDlg2.show();
							
							Button button1 = (Button)JoinDlg2.findViewById(R.id.button1);
							button1.setOnClickListener(new OnClickListener() {

								  public void onClick(View arg0) {
									  
									// custom dialog
									final Dialog JoinDlg3 = new Dialog(mycontext);
									JoinDlg3.setContentView(R.layout.instent_help4);
									
									JoinDlg3.show();
									
									Button button1 = (Button)JoinDlg3.findViewById(R.id.button1);
									button1.setOnClickListener(new OnClickListener() {
										// input user message
										  public void onClick(View arg0) {
											  
												// custom dialog
												final Dialog JoinDlg4 = new Dialog(mycontext);
												JoinDlg4.setContentView(R.layout.successed);
												EditText lv = (EditText) JoinDlg3.findViewById(R.id.editText1);
												messStr = "Operator will assist you asap... : "+lv.getText().toString();
												
												if (messStr != "")
													bNewMess = true;
												
												JoinDlg2.dismiss();
												JoinDlg.dismiss();
												JoinDlg3.dismiss();
												JoinDlg4.show();
										  }
									});
									
									Button button2 = (Button)JoinDlg3.findViewById(R.id.button2);
									button2.setOnClickListener(new OnClickListener() {

										  public void onClick(View arg0) {
											  	JoinDlg2.dismiss();
												JoinDlg.dismiss();
												JoinDlg3.dismiss();
										  }
									});
								  }
							});
							
							Button button2 = (Button)JoinDlg2.findViewById(R.id.button2);
							button2.setOnClickListener(new OnClickListener() {

								  public void onClick(View arg0) {
										JoinDlg2.dismiss();
										JoinDlg.dismiss();
								  }
							});
						  }
					});
				  }
			});
			
			return rootView;
		}
		
	}

	//unlock page
	public static class UnlockFragment extends Fragment {

		View rootView = null;
		public String room_number = "111";
		public String member_id = "111";
		private AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);  
		public UnlockFragment() {
		}
		
		@Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        
	        
	    }  
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			Log.d("aaa", "in")	;
			if(data != null)	
				switch(requestCode){	    		
	    		case 0:
	    			
	    			String result_status = data.getExtras().get(Intent.EXTRA_TEXT).toString();    			    			    			    			
	    			Log.d("aaa", result_status);
	    			alphaAnimation1.cancel();
	    			
	    			if(result_status.hashCode() == 1177596730)
	    			{
	    				((MainActivity)getActivity()).showWelcome();
	    			}
	    			else
	    			{
	    				((MainActivity)getActivity()).showAccessDenied();
	    			}
	    			
	    			break;
	    			    		
				}
			
		}	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			 	rootView = inflater.inflate(R.layout.myroom_page_2, container, false);
		        		 				
				rootView.setBackgroundColor(Color.BLACK);
				VerticalSeekBar verticalSeebar = (VerticalSeekBar)rootView.findViewById(R.id.verticalSeekbar);
				verticalSeebar.setEnabled(false);
		        verticalSeebar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		        	public void onStopTrackingTouch(SeekBar seekBar) {
		        		
		        	}
		        	
		        	public void onStartTrackingTouch(SeekBar seekBar) {
		        		
		        	}

		        	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
		        		
		        		if (progress > 80)
		        		{
		        			TextView tvn = (TextView) rootView.findViewById(R.id.tv_open);
		        			tvn.setBackgroundColor(Color.BLUE);
		        			tvn = (TextView) rootView.findViewById(R.id.tv_lock);
		        			tvn.setBackgroundColor(Color.WHITE);
		        			
		        			//NFC
		        			Intent sendIntent = new Intent();
	        				sendIntent.setAction(Intent.ACTION_SEND);
	        				sendIntent.putExtra(Intent.EXTRA_TEXT, "Open");
	        				sendIntent.setType("pkinno/odin");
	        				startActivityForResult(sendIntent, 0);
	        				
	        				ImageView iv = (ImageView) rootView.findViewById(R.id.imageofnfc);
	        				alphaAnimation1.setDuration(1000);  
	        				alphaAnimation1.setRepeatCount(Animation.INFINITE);  
	        				alphaAnimation1.setRepeatMode(Animation.REVERSE);
	        				
	        				iv.setAnimation(alphaAnimation1);
	        				alphaAnimation1.start();
	        				
	        				iv.setVisibility(View.GONE);
		        		}
		        	}
		        });   
		        
		        Button btnOk = (Button)rootView.findViewById(R.id.btnOk);
				
		        btnOk.setOnClickListener(new OnClickListener()
				{
					  public void onClick(View arg0)
					  {
						  EditText room = (EditText) rootView.findViewById(R.id.editText1);
					  
						  EditText id = (EditText) rootView.findViewById(R.id.editText2);
						  if (room.getText().toString().equals(room_number) && (id.getText().toString().equals(member_id)))
						  {
							  VerticalSeekBar verticalSeebar = (VerticalSeekBar)rootView.findViewById(R.id.verticalSeekbar);
							  verticalSeebar.setEnabled(true);
							  room.setEnabled(false);
							  id.setEnabled(false);
							  arg0.setEnabled(false);
						  }
						  else
						  {
							  new AlertDialog.Builder(getActivity())
						        .setTitle("Error").setMessage("Wrong password?")
						        .setPositiveButton("OK",
						         new DialogInterface.OnClickListener() {
						         public void onClick(DialogInterface dialog, int which) {
						          }
						          }).show();
						  }
					  }
				});
		        
				return rootView;
			}
		}
		
	//welcome page
	public static class WelcomeFragment extends Fragment {

		
		private ListView listV;
	    //List<List_Initem> movie_list = new ArrayList<List_Initem>();
	    
	    private AccountManager mgr = null;
		//private ListView listView;
		final List<List_Initem> wellcome_list = new ArrayList<List_Initem>();
		private MyAdapter adapter;
		
		private Button signinBtn;
		
		public WelcomeFragment() {
			
		}
		public void SetAccountMgr(AccountManager mgr_)
		{
			mgr = mgr_;
		}
		@Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        
	        
	    } 
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.welcome, container,
					false);
			
			final String[] welcome = getResources().getStringArray(R.array.welcome_array);
			int[] wecome_type = getResources().getIntArray(R.array.wellcome_type);
			ImageView iv = (ImageView)rootView.findViewById(R.id.item_image);
			if (wellcome_list.size() != 0)
				wellcome_list.removeAll(wellcome_list);
			
			for (int i = 0; i < welcome.length; i++)
			{
				if (mgr.GetCurrentStatus() == AccountStatus.LOGIN)
				{
					if(mgr.GetCurrentType() == AccountType.NONBOOKING && (i == 2 || i == 3))
						wellcome_list.add(new List_Initem(wecome_type[i], welcome[i], iv));
					else
						wellcome_list.add(new List_Initem(1, welcome[i], iv));
					
				}
				else
				{
					wellcome_list.add(new List_Initem(wecome_type[i], welcome[i], iv));
				}
			}
			
			
			//listView = (ListView) rootView.findViewById(R.id.welcomeList);
			listV=(ListView)rootView.findViewById(R.id.welcomeList);
			adapter = new MyAdapter(getActivity(),wellcome_list);
			listV.setAdapter(adapter);
			
			
			//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
					// android.R.layout.simple_expandable_list_item_1, welcome);

			//listView.setAdapter(adapter);
			
			signinBtn = (Button) rootView.findViewById(R.id.welcomeBtn);
			if (mgr.GetCurrentStatus() == AccountStatus.LOGIN)
			{
				//bUserLogin = false;
				signinBtn.setText("Sign Out");
			}
			
			
			signinBtn.setOnClickListener(new OnClickListener()
			{
				  public void onClick(View arg0)
				  {
					  if (mgr.GetCurrentStatus() == AccountStatus.LOGIN)
					  {
						  mgr.LogoffCurrent();
						  signinBtn.setText("Sign In");
						  FragmentTransaction ft = getFragmentManager().beginTransaction();
						  ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

							if (MainActivity.welcomeFrg.isVisible()) {
								ft.remove(MainActivity.welcomeFrg);
								
							}
							ft.replace(R.id.container, MainActivity.welcomeFrg, "detailFragment");
							ft.commit();
					  }
					  else
					  {
						  ((MainActivity)getActivity()).showLoginDialog(signinBtn);
					  }
				  }
			});
			
			listV.setOnItemClickListener(new OnItemClickListener() {
			    @Override
			    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
			    	if (((int)id == 2 || (int)id == 3) && mgr.GetCurrentType() == AccountType.NONBOOKING)
			    		return;
			    	
			    	if(mgr.GetCurrentStatus() == AccountStatus.LOGOFF)
			    		return;
			    	
			    	switch ((int)id)
					{
					case 2:
						 ((MainActivity)getActivity()).jumpMyRoomPage();
						break;
					case 5:
						 ((MainActivity)getActivity()).jumpMessagePage();
						break;
					}
			    }
			});
			
			if (bNewMess == true)
			{
				ImageView miv = (ImageView) rootView.findViewById(R.id.messageview);
				miv.setVisibility(View.VISIBLE);
				miv.setOnClickListener(new OnClickListener()
				{
					  public void onClick(View arg0)
					  {
						  ((MainActivity)getActivity()).jumpMessagePage();
					  }
				});
			}
			else
			{
				ImageView miv = (ImageView) rootView.findViewById(R.id.messageview);
				miv.setVisibility(View.INVISIBLE);
			}
				
			return rootView;
		}
		
	}

	//myroom page
	public static class MessageFragment extends Fragment {

		public ExpandableListAdapter2 listAdapter;
	    ExpandableListView expListView;
	    List<String> listDataHeader;
	    HashMap<String, List<String>> listDataChild;
	    
		public MessageFragment() {
		}
		
		@Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);	             
	    }  
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.message, container, false);
			
			// get the listview
	        expListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
	 
	        // preparing list data
	        prepareListData();
	 
	        listAdapter = new ExpandableListAdapter2(rootView.getContext(), listDataHeader, listDataChild);
	 
	        // setting list adapter 
	        expListView.setAdapter(listAdapter);
			
			rootView.setBackgroundColor(Color.BLACK);

			return rootView;
		}
		
		 private void prepareListData() {
		        listDataHeader = new ArrayList<String>();
		        listDataChild = new HashMap<String, List<String>>();
		 
		        if (messStr == ""  && top250.size() == 0)
		        {
		        	listDataHeader.add("No message");
			        // Adding child data
			        //List<String> top250 = new ArrayList<String>();
			        //top250.add(messStr);
			        
			        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		        }
		        else if (bNewMess == true)
		        {
			        // Adding child data
			        listDataHeader.add("new message!!!");
			 
			        // Adding child data
			        //List<String> top250 = new ArrayList<String>();
			        top250.add(messStr);
			 
			        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
			        
			        bNewMess = false;
			        messStr = "";
		        }
		        else
		        {
		        	listDataHeader.add("You have message");
			        // Adding child data
			        //List<String> top250 = new ArrayList<String>();
			        //top250.add(messStr);
			        
			        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		        }
		    }
		
	}
	
	public void jumpMessagePage(){
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		
		if (messageFrg.isVisible()) {
			ft.remove(messageFrg);
		}
		
		if (!messageFrg.isVisible()) {
			  
            ft.replace(R.id.container, messageFrg,  "messageFragment");
		}
		
		ft.commit();
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	        new AlertDialog.Builder(MainActivity.this).setTitle(R.string.MP_prompt)
	                .setIconAttribute(android.R.attr.alertDialogIcon)
	                .setMessage(R.string.MP_confirm)
	                .setPositiveButton(R.string.MP_OK, new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	MainActivity.this.finish();
	                    }})
	                .setNegativeButton(R.string.MP_cancel, null)
	                .create().show();
	        return false;
	    } else if(keyCode == KeyEvent.KEYCODE_MENU) {
	        // MENU KEY
	        Toast.makeText(MainActivity.this, "Menu", Toast.LENGTH_SHORT).show();
	        return false;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}



