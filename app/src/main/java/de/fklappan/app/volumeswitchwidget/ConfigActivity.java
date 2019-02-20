package de.fklappan.app.volumeswitchwidget;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.flo.volumeswitchwidget.R;

import de.fklappan.app.volumeswitchwidget.util.VolumeSwitchWidgetPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This activity only is used as an informational activity. It notifies the user about a change to his
 * media volume control. It switches between two states: no sound and last set volume by the user.
 * The last set volume is saved to the android preferences.
 */
public class ConfigActivity extends AppCompatActivity {

    @BindView(R.id.button)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);
        setTitle("");
//        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//
//        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//        if (prefs.contains(SHARED_PREF_LAST_VOLUME)) {
//            restoreLastVolume(audioManager, prefs);
//        } else {
//            muteSound(audioManager, prefs);
//        }
//
//        Intent intent = new Intent(this, MusicVolumeWidgetProvider.class);
//        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), MusicVolumeWidgetProvider.class));
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
//        sendBroadcast(intent);
//
//        // close activity after x second
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                doFinish();
//                finish();
//            }
//        }, 50);
    }

    private void doFinish() {
        finish();
//        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void muteSound(AudioManager audioManager, SharedPreferences prefs) {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        prefs.edit().putInt(VolumeSwitchWidgetPreferences.SHARED_PREF_LAST_VOLUME, currentVolume).commit();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
//        imageView.setBackgroundResource(R.drawable.volume_off);
//        Toast.makeText(this, R.string.sound_muted, Toast.LENGTH_LONG).show();
    }

    private void restoreLastVolume(AudioManager audioManager, SharedPreferences prefs) {
        int volume = prefs.getInt(VolumeSwitchWidgetPreferences.SHARED_PREF_LAST_VOLUME, 10);
        prefs.edit().remove(VolumeSwitchWidgetPreferences.SHARED_PREF_LAST_VOLUME).commit();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        // remove saved value to mute sound the next time the user clicks
//        imageView.setBackgroundResource(R.drawable.volume);
        String text = getString(R.string.sound_value, volume);
//        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void updateButton() {
        if (VolumeSwitchWidgetPreferences.isMuted(this)){
            imageView.setImageResource(R.drawable.volume_off);
        } else {
            imageView.setImageResource(R.drawable.volume);
        }
    }

    @OnClick(R.id.button)
    public void onClickButton(View v) {
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        if (VolumeSwitchWidgetPreferences.isMuted(this)){
//            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, AudioManager.FLAG_SHOW_UI);
        } else {
//            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);

        }

        updateButton();
//        doFinish();
    }


}