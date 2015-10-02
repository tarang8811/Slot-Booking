package com.example.gymslot;



import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends ActionBarActivity {
	EditText name;
	EditText Pass;
	Button login;
	Button Signup;
	List<Integer> slots = new ArrayList<Integer>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_main);
		
		 Parse.initialize(this, "JrgYYEBfjGaRzcEreIc1TeEBoroSgrvk9dwV0aR0", "kRmW0UXJhURoyygaDGxrCWCvUiJo8gqk8izmW2QM");
		name = (EditText)findViewById(R.id.editText1);
		Pass = (EditText)findViewById(R.id.editText2);
		login = (Button)findViewById(R.id.button2);
		Signup = (Button)findViewById(R.id.button1);
		
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
		ParseUser user = new ParseUser();
		user.setUsername(name.getText().toString());
		user.setPassword(Pass.getText().toString());
		
		user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
			    if (e == null) {
			      // Hooray! Let them use the app now.
			    	
			    	ParseObject Booking = new ParseObject("Booking");
			    	Booking.put("Username", name.getText().toString());
			    	Booking.put("Money", 1000);
			    	Booking.put("Slots", slots);
			    	Booking.saveInBackground();
			    	Intent intent = new Intent(MainActivity.this, SecondActivity.class);
			    	intent.putExtra("name", name.getText().toString());
			    	startActivity(intent);
			    } else {
			      // Sign up didn't succeed. Look at the ParseException
			      // to figure out what went wrong
			    	Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
			    }
			  }
			});
            }
            });
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
		
		
		ParseUser.logInInBackground(name.getText().toString(), Pass.getText()
                .toString(), new LogInCallback() {

            public void done(ParseUser user, ParseException e){
			    if (e == null) {
			      // Hooray! Let them use the app now.
			    	Intent intent = new Intent(MainActivity.this, SecondActivity.class);
			    	intent.putExtra("name", name.getText().toString());
			    	startActivity(intent);
			    } else {
			      // Sign up didn't succeed. Look at the ParseException
			      // to figure out what went wrong
			    	Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
			    }
			  }
			});
            }
            });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
