package com.example.gymslot;



import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SecondActivity extends ActionBarActivity {
	TextView money;
	Button book;
	String name;
	int money1;
	TimePicker hours;
	int hour;
	List<Integer> slots;
	List<Integer> slots1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		name = getIntent().getStringExtra("name");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Booking");
		query.whereEqualTo("Username",name);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			  public void done(ParseObject object, ParseException e) {
			    money1 = object.getInt("Money");
			    slots1 = object.getList("Slots");	
			    money = (TextView)findViewById(R.id.textView2);
		        money.setText(Integer.toString(money1));
			  }
			});
		
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Slots");
    	query1.getInBackground("mrCFmXkz90", new GetCallback<ParseObject>() {
        	public void done(ParseObject object, ParseException e) {
        		 slots = object.getList("Time");
        		}
        	});
    	
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	hours = (TimePicker)findViewById(R.id.timePicker1);
        		 hour= hours.getCurrentHour();
            	ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Slots");
            	query1.getInBackground("mrCFmXkz90", new GetCallback<ParseObject>() {
                	public void done(ParseObject object, ParseException e) {
                		 slots = object.getList("Time");
                		if(slots.get((hour)) < 5){
                			money1 = money1 - 100;
                			slots.set(hour , (slots.get((hour)) + 1));
                			slots1.add(hour);
                			AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecondActivity.this);
                			 
                	        // Setting Dialog Title
                	        alertDialog.setTitle("Thank You");
                	 
                	        // Setting Dialog Message
                	        alertDialog.setMessage("Your Slot has been Booked" );
                	 
                	        
                	 
                	        // Setting Positive "Yes" Button
                	        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                	            public void onClick(DialogInterface dialog,int which) {
                	 
                	            // Write your code here to invoke YES event
                	           finish();
                	            }
                	        });
                			
                			alertDialog.show();
                		}
                		else{
                			AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecondActivity.this);
               			 
                	        // Setting Dialog Title
                	        alertDialog.setTitle("Sorry");
                	 
                	        // Setting Dialog Message
                	        alertDialog.setMessage("All slots have been Filled. Please choose another slot" );
                	 
                	        
                	 
                	        // Setting Positive "Yes" Button
                	        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                	            public void onClick(DialogInterface dialog,int which) {
                	 
                	            // Write your code here to invoke YES event
                	           dialog.dismiss();
                	            }
                	        });
                			
                			alertDialog.show();
                		}
                	}
                });	
                
            	ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Slots");
                query2.getInBackground("mrCFmXkz90", new GetCallback<ParseObject>() {
                	public void done(ParseObject object, ParseException e) {
                		object.put("Time", slots);
        			    object.saveInBackground();
                	}
                });
            
            	ParseQuery<ParseObject> query = ParseQuery.getQuery("Booking");
        		query.whereEqualTo("Username",name);
        		query.getFirstInBackground(new GetCallback<ParseObject>() {
        			  public void done(ParseObject object, ParseException e) {
        			    object.put("Money", money1);
        			    object.put("Slots",slots1);
        			    object.saveInBackground();
        			    money = (TextView)findViewById(R.id.textView2);
        		        money.setText(Integer.toString(money1));
        			  }
        			});
            	}
            });
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	if(slots1.size() > 0)
            	{
            		
            		String radiobutton[] = new String[slots1.size()];
            		for(int i=0;i<slots1.size();i++)
            		{
            			
            			radiobutton[i] = "Booking at " + Integer.toString(slots1.get(i)) + ":00 hrs";
            			Log.d("dffs", radiobutton[i]);
            		}
            		AlertDialog.Builder builder2=new AlertDialog.Builder(SecondActivity.this);
            			builder2.setTitle("Choose the slot you want to cancel");
            			builder2.setSingleChoiceItems(radiobutton, -1, new DialogInterface.OnClickListener() {
            			@Override
            			public void onClick(DialogInterface dialog, int which) {
            				
            			// TODO Auto-generated method stub
            			slots.set(slots1.get(which) , (slots.get(slots1.get(which)) - 1));
            			slots1.remove(which);
            			money1 = money1 + 100 ;
            			
            			AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecondActivity.this);
              			 
            	        // Setting Dialog Title
            	        alertDialog.setTitle("Confirmation");
            	 
            	        // Setting Dialog Message
            	        alertDialog.setMessage("Your Slot has been cancelled And the Money will be Refunded within an hour." );
            	 
            	        
            	 
            	        // Setting Positive "Yes" Button
            	        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            	            public void onClick(DialogInterface dialog,int which) {
            	 
            	            // Write your code here to invoke YES event
            	           dialog.dismiss();
            	            }
            	        });
            			
            			alertDialog.show();
            			//dismissing the dialog when the user makes a selection.
            			dialog.dismiss();
            			}
            			});
            			builder2.show();
            		        	}
            	ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Slots");
                query2.getInBackground("mrCFmXkz90", new GetCallback<ParseObject>() {
                	public void done(ParseObject object, ParseException e) {
                		object.put("Time", slots);
        			    object.saveInBackground();
                	}
                });
            
            	ParseQuery<ParseObject> query5 = ParseQuery.getQuery("Booking");
        		query5.whereEqualTo("Username",name);
        		query5.getFirstInBackground(new GetCallback<ParseObject>() {
        			  public void done(ParseObject object, ParseException e) {
        			    object.put("Money", money1);
        			    object.put("Slots",slots1);
        			    object.saveInBackground();
        			    money = (TextView)findViewById(R.id.textView2);
        		        money.setText(Integer.toString(money1));
        			  }
        			});}});
          
		findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	if(slots1.size() > 0)
            	{
            		String radiobutton[] = new String[slots1.size()];
            		for(int i=0;i<slots1.size();i++)
            		{
            			radiobutton[i] = "Booking at " + Integer.toString(slots1.get(i)) + ":00 hrs";
            		}
            		AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
            		builder.setTitle("Your booking");
            		builder.setItems(radiobutton, new DialogInterface.OnClickListener() {
            		    public void onClick(DialogInterface dialog, int item) {
            		         // Do something with the selection
            		    	dialog.dismiss();
            		    }
            		});
            		builder.show();
            	}
            	ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Slots");
                query2.getInBackground("mrCFmXkz90", new GetCallback<ParseObject>() {
                	public void done(ParseObject object, ParseException e) {
                		object.put("Time", slots);
        			    object.saveInBackground();
                	}
                });
            
            	ParseQuery<ParseObject> query5 = ParseQuery.getQuery("Booking");
        		query5.whereEqualTo("Username",name);
        		query5.getFirstInBackground(new GetCallback<ParseObject>() {
        			  public void done(ParseObject object, ParseException e) {
        			    object.put("Money", money1);
        			    object.put("Slots",slots1);
        			    object.saveInBackground();
        			    money = (TextView)findViewById(R.id.textView2);
        			    money.setTextColor(Color.parseColor("#2016e5"));
        		        money.setText(Integer.toString(money1));
        			  }
        			});}});
		
		
    	
   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
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
