package com.example.insightdemo;
import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

//roy give me money
public class MainActivity extends Activity implements OnItemClickListener {

	private Context context = this;
	private boolean login = false;
	private WelcomeFragment welcomeFrg = new WelcomeFragment(); 
	private MyroomFragment myroomFrg = new MyroomFragment();
	private MessageFragment messageFrg = new MessageFragment();
	
	public String ID1 = "123456";
	public String ID2 = "654321";
	public String PW1 = "1234";
	public String PW2 = "5678";
	
	private static boolean bUserLogin = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar);

		ImageView img1 = (ImageView) findViewById(R.id.imageView1);
		img1.setBackgroundColor(Color.rgb(170, 170, 15));

		ImageView img2 = (ImageView) findViewById(R.id.imageView2);
		img2.setBackgroundColor(Color.rgb(170, 170, 15));

		ImageView img3 = (ImageView) findViewById(R.id.imageView3);
		img3.setBackgroundColor(Color.rgb(170, 170, 15));

		ImageView img4 = (ImageView) findViewById(R.id.imageView4);
		img4.setBackgroundColor(Color.rgb(170, 170, 15));
		
		
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
				} else {
					ft.remove(welcomeFrg);				
					btn.setBackgroundResource(R.drawable.droidmenu);
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
		Log.d("aaa", "in")	;
		if(data != null)	
			switch(requestCode){	    		
    		case 0:
    			String result_status = data.getExtras().get(Intent.EXTRA_TEXT).toString();    			    			    			    			
    			Log.d("aaa", result_status)	;
    			break;
    			    		
			}
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
				EditText etMS = (EditText) dialog.findViewById(R.id.et_popMS);
				EditText etPW = (EditText) dialog.findViewById(R.id.et_popPW);
				if ((etMS.getText().toString().equals(ID1) && etPW.getText().toString().equals(PW1)) ||
					(etMS.getText().toString().equals(ID2) && etPW.getText().toString().equals(PW2))	)
				{
					bUserLogin = true;
					_btn.setText("Sing Out");  

					dialog.dismiss();
				}
				else
				{
					dialog.dismiss();
				}
			}
		});
		Button JoinButton = (Button) dialog.findViewById(R.id.btn_popJoinUs);
		JoinButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// custom dialog
				final Dialog JoinDlg = new Dialog(context);
				JoinDlg.setContentView(R.layout.register1);
				JoinDlg.setTitle("Join Page...");
				
				JoinDlg.show();
				
				Button regCloseButton = (Button) JoinDlg.findViewById(R.id.btn_regclose);
				regCloseButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						JoinDlg.dismiss();
					}
				});
				
				Button regreJoinButton = (Button) JoinDlg.findViewById(R.id.btn_regjoin);
				regreJoinButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						final Dialog MessDlg = new Dialog(context);
						MessDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
						MessDlg.setContentView(R.layout.register2);
						
						MessDlg.show();
						
						JoinDlg.dismiss();
						dialog.dismiss();
						
						Button ConfirmButton = (Button) MessDlg.findViewById(R.id.nConfirm);
						ConfirmButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								final Dialog confDlg = new Dialog(context);
								confDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
								confDlg.setContentView(R.layout.successed);
								
								confDlg.show();
								
								MessDlg.dismiss();
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

		public MyroomFragment() {
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
			
			return rootView;
		}
		
	}

	//unlock page
	public static class UnlockFragment extends Fragment {

		public UnlockFragment() {
		}
		
		@Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        
	        
	    }  
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.myroom_page_2, container,
					false);
		        		 				
				rootView.setBackgroundColor(Color.BLACK);
				VerticalSeekBar verticalSeebar = (VerticalSeekBar)rootView.findViewById(R.id.verticalSeekbar);
				

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
	        				AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);  
	        				alphaAnimation1.setDuration(1000);  
	        				alphaAnimation1.setRepeatCount(Animation.INFINITE);  
	        				alphaAnimation1.setRepeatMode(Animation.REVERSE);  
	        				iv.setAnimation(alphaAnimation1);
	        				alphaAnimation1.start(); 
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
	    
	    
		//private ListView listView;
		final List<List_Initem> wellcome_list = new ArrayList<List_Initem>();
		private MyAdapter adapter;
		
		private Button signinBtn;
		public WelcomeFragment() {
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
			//ImageView iv = null;
			if (wellcome_list.size() != 0)
				wellcome_list.removeAll(wellcome_list);
			
			for (int i = 0; i < welcome.length; i++)
			{
				if (bUserLogin == true)
					wellcome_list.add(new List_Initem(1, welcome[i]/*, iv*/));
				else
					wellcome_list.add(new List_Initem(wecome_type[i], welcome[i]/*, iv*/));
			}
			
			
			//listView = (ListView) rootView.findViewById(R.id.welcomeList);
			listV=(ListView)rootView.findViewById(R.id.welcomeList);
			adapter = new MyAdapter(getActivity(),wellcome_list);
			listV.setAdapter(adapter);
			
			
			//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
					// android.R.layout.simple_expandable_list_item_1, welcome);

			//listView.setAdapter(adapter);
			
			signinBtn = (Button) rootView.findViewById(R.id.welcomeBtn);
			if (bUserLogin == true)
			{
				//bUserLogin = false;
				signinBtn.setText("Sing Out");
			}
			
			
			signinBtn.setOnClickListener(new OnClickListener()
			{
				  public void onClick(View arg0)
				  {
					  if (bUserLogin == true)
					  {
						  bUserLogin = false;
						  signinBtn.setText("Sing In");
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
					
			    	if ((int)id == 2 || (int)id == 4)
			    		if (bUserLogin == false)
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
				
			return rootView;
		}
		
	}

	//myroom page
	public static class MessageFragment extends Fragment {

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
			
			rootView.setBackgroundColor(Color.BLACK);

			return rootView;
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



