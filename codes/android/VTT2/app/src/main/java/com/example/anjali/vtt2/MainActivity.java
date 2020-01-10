package com.example.anjali.vtt2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.anjali.vtt2.R.layout.activity_main;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity
{
    protected static final int RESULT_SPEECH = 1;
    private ImageButton btnSpeak;
    private TextView txtText;
    @Override
    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState);
        setContentView(activity_main);
        txtText = (TextView) findViewById(R.id.txtText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { Intent intent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try { startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");
                    txtText.setVisibility(View.GONE);




                } catch (ActivityNotFoundException a)
                { Toast t = Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    protected void
    onActivityResult(int requestCode, int resultCode, Intent data)
    {
        ArrayList<String> text = null;
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case RESULT_SPEECH:
            {
                if (resultCode == RESULT_OK && null != data)
                {  text = data .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtText.setText(text.get(0));
                    txtText.setVisibility(View.GONE);

                    String Text1;
                    Text1 = txtText.getText().toString();
                    if((Text1.equals(getString(R.string.D1_o1)))||(Text1.equals(getString(R.string.D1_o2)))||(Text1.equals(getString(R.string.D1_o3)))||(Text1.equals(getString(R.string.D1_o4))||(Text1.equals(getString(R.string.D1_o5))))||(Text1.equals(getString(R.string.D1_o6)))||(Text1.equals(getString(R.string.D1_o7)))||(Text1.equals(getString(R.string.D1_o8)))||(Text1.equals(getString(R.string.D1_o9))))
                    {
                        new JSONTask().execute("http://192.168.1.11/a");
                    }
                    else //noinspection deprecation
                        if((Text1.equals(getString(R.string.D1_c1)))||(Text1.equals(getString(R.string.D1_c2)))||(Text1.equals(getString(R.string.D1_c3)))||(Text1.equals(getString(R.string.D1_c4))))
                        {
                            new JSONTask().execute("http://192.168.1.11/b");
                        }
                        else if((Text1.equals(getString(R.string.D2_o1)))||(Text1.equals(getString(R.string.D2_o2)))||(Text1.equals(getString(R.string.D2_o3)))||(Text1.equals(getString(R.string.D2_o4)))||(Text1.equals(getString(R.string.D2_o5)))||(Text1.equals(getString(R.string.D2_o6)))||(Text1.equals(getString(R.string.D2_o7))))
                        {
                            new JSONTask().execute("http://192.168.1.11/c");
                        }
                        else if((Text1.equals(getString(R.string.D2_c1)))||(Text1.equals(getString(R.string.D2_c2)))||(Text1.equals(getString(R.string.D2_c3)))||(Text1.equals(getString(R.string.D2_c4))))
                        {
                            new JSONTask().execute("http://192.168.1.11/d");
                        }
                        else if((Text1.equals(getString(R.string.D3_o1)))||(Text1.equals(getString(R.string.D3_o2)))||(Text1.equals(getString(R.string.D3_o3)))||(Text1.equals(getString(R.string.D3_o4)))||Text1.equals(getString(R.string.D3_o5))||Text1.equals(getString(R.string.D3_o6))||Text1.equals(getString(R.string.D3_o6))||Text1.equals(getString(R.string.D3_o7))||Text1.equals(getString(R.string.D3_o8))||Text1.equals(getString(R.string.D3_o9))||Text1.equals(getString(R.string.D3_o10)))
                        {
                            new JSONTask().execute("http://192.168.1.11/e");
                        }
                        else if((Text1.equals(getString(R.string.D3_c1)))||(Text1.equals(getString(R.string.D3_c2)))||(Text1.equals(getString(R.string.D3_c3)))||(Text1.equals(getString(R.string.D3_c4))))
                        {
                            new JSONTask().execute("http://192.168.1.11/f");
                        }
                        else if((Text1.equals(getString(R.string.ss))))
                        {
                            new JSONTask().execute("http://192.168.1.11/g");
                        }
                }
            } break;
        }
    }

}



class JSONTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
