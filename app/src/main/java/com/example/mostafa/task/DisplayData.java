package com.example.mostafa.task;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DisplayData extends AppCompatActivity implements View.OnClickListener {

    TextView email;
    Button signOut;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseAuth auth;
    public List<Country> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(DisplayData.this, MainActivity.class));
                    finish();
                }
            }
        };


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CountryAdapter(list, getApplicationContext(),this);


        signOut = (Button) findViewById(R.id.button3);
        signOut.setOnClickListener(this);
        email = (TextView) findViewById(R.id.textView2);

        email.setText("Hi: "+user.getEmail());


        new FetchData().execute();

        Log.d("FFF", String.valueOf(list.size()));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.button3:
                auth.signOut();
                auth.removeAuthStateListener(authListener);
                Intent intent=new Intent(DisplayData.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }



    public class FetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list.clear();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... strings) {

            HttpURLConnection connection = null;
            Uri uri1 = Uri.parse("https://restcountries.eu/rest/v2/all");
            URL url1 = null;
            try {
                url1 = new URL(uri1.toString());
                connection = (HttpURLConnection) url1.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                Scanner scanner = new Scanner(stream);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    JSONArray parentArray = new JSONArray(scanner.next());
                    for(int i=0;i<parentArray.length();i++)
                    {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        Country country=new Country(
                                finalObject.getString("name"),
                                finalObject.getString("capital"),
                                finalObject.getString("region"),
                                finalObject.getString("nativeName"),
                                finalObject.getString("flag")
                        );
                        list.add(country);

                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return null;
        }

    }
}