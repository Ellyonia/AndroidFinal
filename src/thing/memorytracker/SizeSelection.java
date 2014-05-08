package thing.memorytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;



public class SizeSelection extends Activity {

	String gameSize;
	public static final int START_GAME =1234;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_size_selection);
	Button startGame = (Button) findViewById(R.id.startGame);
	
	
	// Set up the Spinner to select the game size to be played.
	 Spinner spinner = (Spinner) findViewById(R.id.gameSizePicker);
	 spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        gameSize = item.toString();
		        Log.v("CAK",gameSize);
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
	 
	 
	 // Start MemoryGame Activity when the start Game button is pushed.
	 // Also send an intent with the gameSize variable inside.
	 startGame.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(), MemoryGame.class);
			intent.putExtra(MemoryGame.GAME_KEY, gameSize);
			startActivityForResult(intent, START_GAME);
			
		}
		 
	 });
	
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	
      Toast.makeText(getBaseContext(), R.string.toast_message, 
                     Toast.LENGTH_LONG).show();
      return true;
  }

}
