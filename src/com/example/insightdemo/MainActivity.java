package com.example.insightdemo;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
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
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.os.Build;

//roy give me money
public class MainActivity extends Activity implements OnItemClickListener {

	private ActionBar actionBar;
	private ViewAnimator viewAnimator;
	private Animation slide_in_left, slide_out_right;
	private Context context = this;
	private boolean login = false;
	private static boolean currentMain = true;


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

		/*
		 * slide_in_left = AnimationUtils.loadAnimation(this,
		 * android.R.anim.slide_in_left); slide_out_right =
		 * AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
		 * 
		 * viewAnimator.setInAnimation(slide_in_left);
		 * viewAnimator.setOutAnimation(slide_out_right);
		 */

		Button btn = (Button) findViewById(R.id.button1);

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentMain) {
					WelcomeFragment frg = new WelcomeFragment();  
		            getFragmentManager().beginTransaction().replace(R.id.container, frg).commit();
				} else {
					setContentView(R.layout.activity_main);
				}
				currentMain = !currentMain;
			}
		});


		
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
//d
	public void showLoginDialog()
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
		Button SigninButton = (Button) dialog.findViewById(R.id.btn_popSignin);
		SigninButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		
				String strURL1 = "http://tw.yahoo.com";
				Intent ie = new Intent(Intent.ACTION_VIEW,Uri.parse(strURL1));
				startActivity(ie);
			}
		});
		Button JoinButton = (Button) dialog.findViewById(R.id.btn_popJoinUs);
		JoinButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// custom dialog
				final Dialog JoinDlg = new Dialog(context);
				JoinDlg.setContentView(R.layout.join_pop);
				JoinDlg.setTitle("Join Page...");
				
				JoinDlg.show();
			}
		});
		dialog.show();
	
	}

	public void jumpMyRoomPage(){

        setContentView(R.layout.myroom_page);
/*
        Button button02= (Button)findViewById(R.id.Button02);

        button02.setOnClickListener(new Button.OnClickListener(){

            publicvoid onClick(View v) {

                // TODO Auto-generated method stub

                jumpToLayout01();

            }           

        });*/

    }
	
	//welcome page
	public static class WelcomeFragment extends Fragment {

		private ListView listView;
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
			
			String[] welcome = getResources().getStringArray(R.array.welcome_array);
			listView = (ListView) rootView.findViewById(R.id.welcomeList);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
					 android.R.layout.simple_expandable_list_item_1, welcome);

			listView.setAdapter(adapter);
			
			signinBtn = (Button) rootView.findViewById(R.id.welcomeBtn);
			signinBtn.setOnClickListener(new OnClickListener() {

				  public void onClick(View arg0) {
					
					  //Log.d("info", "aaa");
					  ((MainActivity)getActivity()).showLoginDialog();
					
				  }
				});
			
			listView.setOnItemClickListener(new OnItemClickListener() {
			    @Override
			    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					switch ((int)id)
					{
					case 2:
						//Log.d("info", "aaa");
						 ((MainActivity)getActivity()).jumpMyRoomPage();
						break;
					
					}
			    }
			});
				
			return rootView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	        // 返回建
	        new AlertDialog.Builder(MainActivity.this).setTitle("提示")
	                .setIconAttribute(android.R.attr.alertDialogIcon)
	                .setMessage("確定要退出吗?")
	                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	MainActivity.this.finish();
	                    }})
	                .setNegativeButton("取消", null)
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
