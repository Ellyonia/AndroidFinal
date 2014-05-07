package thing.memorytracker;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MemoryGame extends Activity {

//	private CameronWait mWait = new CameronWait();
	public static final String GAME_KEY = "iliekchocolatemilk";
	int gameCase=0;
	boolean isSecond = false;
	ImageView previousView;
	int previousLoc = -1;
	Drawable[] drawablesFirst;
	Drawable[] drawablesSecond;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory_game_board);
		
		Intent sentIntent = getIntent();
		String gameSize= sentIntent.getStringExtra(GAME_KEY);
		Log.v("CAK",gameSize);
		GridView gameBoard = (GridView) findViewById(R.id.gameBoard);
		gameBoard.setBackgroundColor(Color.BLACK);
		TypedArray icons = getResources().obtainTypedArray(R.array.gamePieces);
		
		Drawable[] drawables = new Drawable[icons.length()];
		for (int i = 0; i < icons.length(); i++) 
		{
			drawables[i] = icons.getDrawable(i);
		}
		icons.recycle();
		
		TypedArray iconsPt2 = getResources().obtainTypedArray(R.array.gamePiecesPt2);
		
		Drawable[] drawablesPt2 = new Drawable[iconsPt2.length()];
		for (int i = 0; i < iconsPt2.length(); i++) 
		{
			drawablesPt2[i] = iconsPt2.getDrawable(i);
		}
		iconsPt2.recycle();
	
		
		
		
		switch(gameSize)
		{
		case "3x4":
			gameBoard.setNumColumns(4);
			drawablesFirst = Arrays.copyOfRange(drawables, 0, 6);
			drawablesSecond = Arrays.copyOfRange(drawablesPt2, 0, 6);
			break;
		case "4x4":
			gameBoard.setNumColumns(4);
			gameCase = 1;
			drawablesFirst = Arrays.copyOfRange(drawables, 0, 8);
			drawablesSecond = Arrays.copyOfRange(drawablesPt2, 0, 8);
			break;
		case "4x5":
			gameBoard.setNumColumns(4);
			gameCase = 2;
			drawablesFirst = Arrays.copyOfRange(drawables, 0, 11);
			drawablesSecond = Arrays.copyOfRange(drawablesPt2, 0, 11);
			break;
		case "4x6":
			gameBoard.setNumColumns(4);
			gameCase = 3;
			drawablesFirst = drawables;
			drawablesSecond = drawablesPt2;
			break;
		}//End of Switch Case
		
		int randSwap1;
		int randSwap2;
		int randSwap3;
		int randSwap4;
		Drawable helper1;
		Drawable helper2;
		int drawableLength = drawablesFirst.length;
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				randSwap1 =(int)(Math.random() * drawableLength);
				randSwap2 = (int)(Math.random()*drawableLength);
				randSwap3 =(int)(Math.random() * drawableLength);
				randSwap4 = (int)(Math.random()*drawableLength);
				helper1 = drawablesFirst[randSwap1];
				helper2 = drawablesSecond[randSwap3];
				drawablesFirst[randSwap1] = drawablesFirst[randSwap2];
				drawablesSecond[randSwap3] = drawablesSecond[randSwap4];
				drawablesFirst[randSwap2] = helper1;
				drawablesSecond[randSwap3] = helper2;
			}
		}
		
		ImageAdapter mAdapter = new ImageAdapter(this,gameCase, drawablesFirst,drawablesSecond);
		gameBoard.setAdapter(mAdapter);
	}
		
		class ImageAdapter extends BaseAdapter {
		    private Context mContext;
		    private int mGameCase;
		    private Drawable[] mGamePieces;
		    private Drawable[] mGamePiecesPt2;

		    public ImageAdapter(Context c, int gameCase, Drawable[] pieces,Drawable[] piecesPt2) {
		        mContext = c;
		        mGameCase = gameCase;
		        mGamePieces = pieces;
		        mGamePiecesPt2 = piecesPt2;
		    }

		    public int getCount() {
		        switch(mGameCase){
		        case 0:
		        	return 12;
		        case 1:
		        	return 16;
		        case 2:
		        	return 20;
		        case 3:
		        	return 24;
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
		    public View getView(final int position, View convertView, ViewGroup parent) {
		        final ImageView imageView;
		        DisplayMetrics metrics = new DisplayMetrics();
		        getWindowManager().getDefaultDisplay().getMetrics(metrics);

		        int width = metrics.widthPixels;
		        int height = metrics.heightPixels;
		        if (mGameCase == 0)
		        	height = height/4;
		        else if (mGameCase ==1)
		        	height = height /5;
		        else if (mGameCase ==2)
		        	height = height/6;
		        else
		        	height = height/7;
		        
		        if (convertView == null) {  // if it's not recycled, initialize some attributes
		            imageView = new ImageView(mContext);
		            imageView.setLayoutParams(new GridView.LayoutParams(width/4, height));
//		            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		            imageView.setBackgroundColor(Color.WHITE);
		        } else {
		            imageView = (ImageView) convertView;
		        }
		        
		        imageView.setColorFilter(Color.WHITE);
		        final int uniqueCount = getCount()/2;
		        
		        if (position >= uniqueCount)
		        {
		        	imageView.setImageDrawable(mGamePiecesPt2[position-uniqueCount]);
		        }
		        else
		        {
		        	imageView.setImageDrawable(mGamePieces[position]);
		        }
		        
		        imageView.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(final View v) {
						if (!isSecond)
						{
							((ImageView) v).setColorFilter(null);
							previousView = ((ImageView) v);
							isSecond = true;
							previousLoc = position;
						}
						else if (isSecond)
						{
							((ImageView) v).setColorFilter(null);
							class CameronWait implements Runnable {

								@Override
								public void run() {
									int currentLoc = position - uniqueCount;
									if (currentLoc == previousLoc)
									{
										previousView.setOnClickListener(null);
										previousView = null;
										isSecond = false;
										v.setOnClickListener(null);
									}
									else
									{
										previousView.setColorFilter(Color.WHITE);
										((ImageView)v).setColorFilter(Color.WHITE);
										previousView = null;
										isSecond = false;
									}
								}
								
							}
							CameronWait mWait = new CameronWait();
							v.postDelayed(mWait, 1000);
							
							
						}
					}
		        	
		        });
		        return imageView;
		    }

		    // references to our images
		}
		
}
