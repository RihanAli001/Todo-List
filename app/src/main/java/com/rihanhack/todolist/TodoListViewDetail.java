package com.rihanhack.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class TodoListViewDetail extends AppCompatActivity {
    private TextView title,desc;
    private CardView toSpeak;
    private TextToSpeech tTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_view_detail);

        title = findViewById(R.id.titleDetail);
        desc = findViewById(R.id.descDetail);
        toSpeak = findViewById(R.id.toSpeak);

        Bundle extras = getIntent().getExtras();
        String t = extras.getString("title");
        String d = extras.getString("desc");

        title.setText(t);
        desc.setText(d);


        tTS = new TextToSpeech(this, status -> {
            if(status == TextToSpeech.SUCCESS){
                int result = tTS.setLanguage(Locale.getDefault());

                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("TTS","Language not supported");
                }
            }else {
                Log.e("TTS","Initialization failed");
            }
        });

        toSpeak.setOnClickListener(v -> {
            String s =": : Title : ";
            if(t.equals("")){
                s+="null :";
            }else{
                s+=t+" : ";
            }

            s+="description : ";

            if(d.equals("")){
                s+="Nahi hai";
            }else{
                s+=d;
            }

            tTS.setPitch(1.3f);
            tTS.setSpeechRate(0.5f);
            try {
                tTS.speak(s, TextToSpeech.QUEUE_ADD, null);
            } catch (Exception e) {
                Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(tTS != null){
            tTS.stop();
            tTS.shutdown();
        }
    }
}