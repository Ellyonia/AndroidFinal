package thing.memorytracker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class MemoryGame extends Activity {

	public static final String GAME_KEY = "iliekchocolatemilk";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory_game_board);
		
		Intent sentIntent = getIntent();
		String gameSize= sentIntent.getStringExtra(GAME_KEY);
		Log.v("CAK",gameSize);
		GridView gameBoard = (GridView) findViewById(R.id.gameBoard);
		
		switch(gameSize)
		{
		case "3x4":
			gameBoard.setNumColumns(3);
			break;
		case "4x4":
			gameBoard.setNumColumns(4);
			break;
		case "4x5":
			gameBoard.setNumColumns(3);
			break;
		case "4x6":
			gameBoard.setNumColumns(4);
			break;
		case "5x6":
			gameBoard.setNumColumns(3);
			break;
		case "6x6":
			gameBoard.setNumColumns(4);
			break;
		case "6x7":
			gameBoard.setNumColumns(3);
			break;
		case "7x8":
			gameBoard.setNumColumns(4);
			break;
		case "8x8":
			gameBoard.setNumColumns(3);
			break;
		case "8x9":
			gameBoard.setNumColumns(4);
			break;
		case "9x10":
			gameBoard.setNumColumns(3);
			break;
		case "6x8":
			gameBoard.setNumColumns(4);
			break;
		
		}
	}
}
