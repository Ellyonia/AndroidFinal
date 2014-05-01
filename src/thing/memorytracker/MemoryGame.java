package thing.memorytracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MemoryGame extends Activity {

	public static final String GAME_KEY = "iliekchocolatemilk";
	int gameCase=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory_game_board);
		
		Intent sentIntent = getIntent();
		String gameSize= sentIntent.getStringExtra(GAME_KEY);
		Log.v("CAK",gameSize);
		GridView gameBoard = (GridView) findViewById(R.id.gameBoard);
		TypedArray icons = getResources().obtainTypedArray(R.array.gamePieces);
		
		Drawable[] drawables = new Drawable[icons.length()];
		for (int i = 0; i < icons.length(); i++) 
		{
			drawables[i] = icons.getDrawable(i);
		}
		icons.recycle();
		
		
		switch(gameSize)
		{
		case "3x4":
			gameBoard.setNumColumns(3);
			break;
		case "4x4":
			gameBoard.setNumColumns(4);
			gameCase = 1;
			break;
		case "4x5":
			gameBoard.setNumColumns(4);
			gameCase = 2;
			break;
		case "4x6":
			gameBoard.setNumColumns(4);
			gameCase = 3;
			break;
		}//End of Switch Case
		ImageAdapter mAdapter = new ImageAdapter(this,gameCase, drawables);
		gameBoard.setAdapter(mAdapter);
	}
		
		class ImageAdapter extends BaseAdapter {
		    private Context mContext;
		    private int mGameCase;
		    private Drawable[] mGamePieces;

		    public ImageAdapter(Context c, int gameCase, Drawable[] pieces) {
		        mContext = c;
		        mGameCase = gameCase;
		        mGamePieces = pieces;
		    }

		    public int getCount() {
		        switch(mGameCase){
		        case 0:
		        	return 6;
		        case 1:
		        	return 8;
		        case 2:
		        	return 10;
		        case 3:
		        	return 12;
		        }
		        return -1;
		    }

		    public Object getItem(int position) {
		        return null;
		    }

		    public long getItemId(int position) {
		        return 0;
		    }

		    // create a new ImageView for each item referenced by the Adapter
		    public View getView(int position, View convertView, ViewGroup parent) {
		        ImageView imageView;
		        if (convertView == null) {  // if it's not recycled, initialize some attributes
		            imageView = new ImageView(mContext);
		            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
		            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		        } else {
		            imageView = (ImageView) convertView;
		        }

		        imageView.setImageDrawable(mGamePieces[position]);
		        return imageView;
		    }

		    // references to our images
		}
}