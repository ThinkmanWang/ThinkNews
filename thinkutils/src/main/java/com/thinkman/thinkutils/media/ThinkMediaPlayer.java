package com.thinkman.thinkutils.media;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

/**
 * Created by wangx on 2016/5/19.
 */
public class ThinkMediaPlayer extends AsyncTask<String, Void, Boolean> {
    private Activity mActivity = null;
    private ProgressDialog progress;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = null;

    public ThinkMediaPlayer(Activity activity) {
        mActivity = activity;

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        progress = new ProgressDialog(mActivity);
        mHandler = new Handler();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        // TODO Auto-generated method stub
        Boolean prepared;
        try {

            mMediaPlayer.setDataSource(params[0]);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mMediaPlayer.stop();
                    mMediaPlayer.reset();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (progress.isShowing()) {
                                progress.cancel();
                            }
                        }
                    });
                }
            });
            mMediaPlayer.prepare();
            prepared = true;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            Log.d("IllegarArgument", e.getMessage());
            prepared = false;
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        }
        return prepared;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        Log.d("Prepared", "//" + result);
        mMediaPlayer.start();

    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        this.progress.setMessage("Buffering...");
        this.progress.show();

    }
}

