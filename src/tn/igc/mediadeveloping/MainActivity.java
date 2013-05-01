package tn.igc.mediadeveloping;

import java.io.File;
import java.io.FileInputStream;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {

	//Specify the path to the mp3 file that you have already put in the SD card
	String MUSIC_FILE = "/Music/Angry Birds.mp3";
	
	MediaPlayer mediaPlayer;
	Button button;
	SeekBar seekBar;
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mediaPlayer = new MediaPlayer();
		
		try{
			File track = new File(Environment.getExternalStorageDirectory().getPath()+ MUSIC_FILE);
			FileInputStream stream = new FileInputStream(track);
			
			mediaPlayer.setDataSource(stream.getFD());
			mediaPlayer.prepare();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		button = (Button) findViewById(R.id.button);  
		button.setOnClickListener(this);
		
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		seekBar.setMax(99);
		seekBar.setOnSeekBarChangeListener(this);
		
		handler = new Handler();
	}

	@Override
	public void onClick(View arg0) {
		if(mediaPlayer.isPlaying()){
			mediaPlayer.pause();
			button.setText("Play");
		}else{
			mediaPlayer.start();
			button.setText("Pause");
		}
		updateSeekBar();
	}

	private void updateSeekBar() {
		seekBar.setProgress((int)((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration()*100));
		if(mediaPlayer.isPlaying()){
			Runnable notification = new Runnable() {
				
				@Override
				public void run() {
					updateSeekBar();
				}
			};
			handler.postDelayed(notification, 1000);
			
		}
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int position, boolean user) {
		if(user){
			mediaPlayer.seekTo(mediaPlayer.getDuration()/100*position);
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}


}
