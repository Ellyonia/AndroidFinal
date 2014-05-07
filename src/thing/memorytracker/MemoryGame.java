package thing.memorytracker;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class MemoryGame extends Activity implements AnimationListener {

//	private CameronWait mWait = new CameronWait();
	public static final String GAME_KEY = "iliekchocolatemilk";
	int gameCase=0;
	boolean isSecond = false;
	ImageView previousView;
	int previousLoc;
	Drawable[] drawablesFirst;
	Drawable[] drawablesSecond;
	int[] pictureOrder;
	int pairsMatched = 0;
	int uniqueCount;
	Context mContext = this;
	private Menu menu;
	private boolean mShowingBack = false;
	private Animation animation1;
    private Animation animation2;
    ImageView viewToAnimate;
    int currentPosition;
	boolean resetViews = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memory_game_board);
		
		 animation1 = AnimationUtils.loadAnimation(this, R.animator.flip_left_start);
         animation1.setAnimationListener(this);
         animation2 = AnimationUtils.loadAnimation(this, R.animator.flip_left_end);
         animation2.setAnimationListener(this);
		
		
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
		
		TypedArray iconsPt2 = getResources().obtainTypedArray(R.array.gamePieces);
		
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
			pictureOrder = new int[]{0,1,2,3,4,5,0,1,2,3,4,5};
			break;
		case "4x4":
			gameBoard.setNumColumns(4);
			gameCase = 1;
			drawablesFirst = Arrays.copyOfRange(drawables, 0, 8);
			drawablesSecond = Arrays.copyOfRange(drawablesPt2, 0, 8);
			pictureOrder = new int[]{0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7};
			break;
		case "4x5":
			gameBoard.setNumColumns(4);
			gameCase = 2;
			drawablesFirst = Arrays.copyOfRange(drawables, 0, 10);
			drawablesSecond = Arrays.copyOfRange(drawablesPt2, 0, 10);
			pictureOrder = new int[]{0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9};
			break;
		case "4x6":
			gameBoard.setNumColumns(4);
			gameCase = 3;
			drawablesFirst = drawables;
			drawablesSecond = drawablesPt2;
			pictureOrder = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,0,1,2,3,4,5,6,7,8,9,10,11};
			break;
		}//End of Switch Case
		
		int randSwap1;
		int randSwap2;
		int helper1;
		int drawableLength = drawablesFirst.length;
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				randSwap1 =(int)(Math.random() * drawableLength);
				randSwap2 = (int)(Math.random()*drawableLength);
				helper1 = pictureOrder[randSwap1];
				pictureOrder[randSwap1] = pictureOrder[randSwap2];
				pictureOrder[randSwap2] = helper1;
			}
		}
		
		ImageAdapter mAdapter = new ImageAdapter(this,gameCase, drawablesFirst,drawablesSecond,pictureOrder);
		gameBoard.setAdapter(mAdapter);
		
	}
		
		class ImageAdapter extends BaseAdapter {
		    private Context mContext;
		    private int mGameCase;
		    private Drawable[] mGamePieces;
		    private Drawable[] mGamePiecesPt2;
		    private int[] mPictureOrder;

		    public ImageAdapter(Context c, int gameCase, Drawable[] pieces,Drawable[] piecesPt2,int[] orderForPictures) {
		        mContext = c;
		        mGameCase = gameCase;
		        mGamePieces = pieces;
		        mGamePiecesPt2 = piecesPt2;
		        mPictureOrder = orderForPictures;
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
		        //currentPosition = position;
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

		            imageView.setBackgroundColor(Color.WHITE);
		        } else {
		            imageView = (ImageView) convertView;
		        }
		        
		        //imageView.setColorFilter(Color.WHITE);
		        uniqueCount = getCount()/2;
//		        if (position >= uniqueCount)
//		        {
//		        	//imageView.setImageDrawable(mGamePiecesPt2[mPictureOrder[position]]);
//		        }
//		        else
//		        {
//		        	//imageView.setImageDrawable(mGamePieces[mPictureOrder[position]]);
//		        }

		        imageView.setOnClickListener(myListner);
		        return imageView;
		    }

		}		
		
		private class CameronWait implements Runnable {
			
			private int mPosition;
			private ImageView mView;
			public void start(ImageView v, int position) {
				mPosition = position;
				mView = v;
				mView.postDelayed(this, 1500);
			}

			@Override
			public void run() {
				int currentPosition = mPosition;
				if (pictureOrder[currentPosition] == pictureOrder[previousLoc])
				{
					previousView.setOnClickListener(null);
					previousView = null;
					isSecond = false;
					mView.setOnClickListener(null);
					pairsMatched++;
					if (pairsMatched == uniqueCount)
					{
						Toast toast = Toast.makeText(mContext, "You Win!",Toast.LENGTH_SHORT);
						toast.show();
					}
				}
				else
				{
					resetViews = true;
//					SecondWait wait = new SecondWait();
//					wait.start();
					 Log.v("CAK",""+resetViews);
					previousView.setOnClickListener(null);
					previousView.setOnClickListener(myListner);
					//previousView.setColorFilter(Color.WHITE);
					//mView.setColorFilter(Color.WHITE);
					viewToAnimate = previousView;
//					previousView.setImageDrawable(null);
					viewToAnimate.clearAnimation();
					viewToAnimate.setAnimation(animation1);
					viewToAnimate.startAnimation(animation1);
					previousView.setImageDrawable(null);
					
					
		            viewToAnimate = mView;
		            viewToAnimate.clearAnimation();
		            viewToAnimate.setAnimation(animation1);
		            viewToAnimate.startAnimation(animation1);
//		            mView.setImageDrawable(null);
					previousView = null;
					isSecond = false;
				}
			}
			
		}
		
        private final View.OnClickListener myListner = new OnClickListener(){
			@Override
			public void onClick(final View v) {
				ViewGroup parent = (ViewGroup) v.getParent();
				final int position = parent.indexOfChild(v);
				if (!isSecond)
				{
					resetViews = false;
					currentPosition = position;
					v.setOnClickListener(null);
					viewToAnimate = (ImageView)v;
					((ImageView)v).clearAnimation();
		            ((ImageView)v).setAnimation(animation1);
		            ((ImageView)v).startAnimation(animation1);
		            
					previousView = ((ImageView) v);
					isSecond = true;
					previousLoc = position;
				}
				else if (isSecond)
				{
					currentPosition = position;
					viewToAnimate = (ImageView)v;
					((ImageView)v).clearAnimation();
		            ((ImageView)v).setAnimation(animation1);
		            ((ImageView)v).startAnimation(animation1);
					CameronWait mWait = new CameronWait();
					
					mWait.start((ImageView)v, position);
					
					
				}	
			}
        	
        };
       
       
      @Override
      public void onAnimationEnd(Animation animation) {
    	  Log.v("CAK",""+resetViews);
            if (animation==animation1) {
                   if (resetViews) 
                   {
                	   Log.v("CAK","Resetting");
                	   viewToAnimate.setImageDrawable(null);
                   } 
                   else 
                   {
                    	 if (currentPosition >= uniqueCount)
         		        {
         		        	viewToAnimate.setImageDrawable(drawablesSecond[pictureOrder[currentPosition]]);
         		        }
         		        else
         		        {
         		        	viewToAnimate.setImageDrawable(drawablesFirst[pictureOrder[currentPosition]]);
         		        }
                   }
                     viewToAnimate.clearAnimation();
                     viewToAnimate.setAnimation(animation2);
                     viewToAnimate.startAnimation(animation2);
               } else 
               {
                      mShowingBack=!mShowingBack;
               }
      }
      @Override
      public void onAnimationRepeat(Animation animation) {
             // TODO Auto-generated method stub
      }
      @Override
      public void onAnimationStart(Animation animation) {
    	  
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
      private class SecondWait implements Runnable
      {
    	  public void start()
    	  {
    		View v = findViewById(R.id.gameBoard);
    		v.postDelayed(this, 1000);
    	  }
    	  
    	  
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
    	  
      }
     
}
