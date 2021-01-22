package com.example.simplefile0;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

//Demonstrerer lesing/skriving til og fra filer som ligger  i public eksternt område.
//Det kan være lurt å "wipe" emulatoren før du kjører appen (gjøres fra AVD Manager).
//
// NB! TARGET API = 28 (og ikke 29+)
//
public class MainActivity extends AppCompatActivity {

    private String fileNameExternal = "eksternfil.txt";

    // Storage Permissions
    private static final int CALLBACK_REQUEST_WRITE_EXTERNAL = 1;
    private static final int CALLBACK_REQUEST_READ_EXTERNAL = 2;

    private static String[] requiredPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String extFilePath = Environment.getExternalStorageDirectory().toString();

        TextView tvExtFileDir = (TextView)findViewById(R.id.tvExtFileDir);
        tvExtFileDir.setText("Externt fil (SDCard): " + extFilePath + "/" + fileNameExternal);
    }

    //SKRIV til EKSTERN fil:
    public void doWriteExtFile(View view) {
        //Sjekker først rettigheter:
        verifyWritePermissions();
    }

    /**
     * SE: http://developer.android.com/training/permissions/requesting.html#handle-response
     * TIPS: sørg for å "wipe" emulatoren før kjøring (fikk problemer med skriverettigheter før jeg wipa emulatoren (gjøres fra AVD Manager).
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public void verifyWritePermissions() {
        // Kontrollerer om vi har tilgang til eksternt område:
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Dersom vi ikke har nøfvendige tilganger spør bruker om tilgang.
            // Fortsetter i metoden onRequestPermissionsResult() ...\
            ActivityCompat.requestPermissions(this, requiredPermissions, CALLBACK_REQUEST_WRITE_EXTERNAL);
        } else {
            //Skriver til ekstern fil dersom alt ok:
            writeToExternalFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CALLBACK_REQUEST_WRITE_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                    //Skriver til ekstern fil:
                    this.writeToExternalFile();
                }
                return;

            case CALLBACK_REQUEST_READ_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                    //Skriver til ekstern fil:
                    this.readFromExternalFile();
                }
                return;

            default:
                Toast.makeText(this, "Feil ...! Ingen tilgang!!", Toast.LENGTH_SHORT).show();
        }
    }

    //Skriver til ekstern fil etter at evt. tillatelser er gitt:
    private void writeToExternalFile() {
        try {
            //Fortsett her dersom rettigheter gitt:
            EditText etInput = (EditText)findViewById(R.id.etExternalInput);
            String content = etInput.getText().toString();

            if (isExternalStorageWritable()) {
                //Oppretter et filobjekt i angitt mappe i eksternt lager:
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                if (dir.mkdir() || dir.isDirectory()) {    //NB! Må gjøres. Se doc til getExternalStoragePublicDirectory().

                    File myFile = new File(dir, fileNameExternal);
                    FileOutputStream outputStream;
                    outputStream = new FileOutputStream(myFile);
                    outputStream.write(content.getBytes());
                    outputStream.close();

                    //Lager ny mappe:
                    //File myDir = new File(dir.getAbsolutePath() + "/MyAppFolder");
                    //myDir.mkdir();  //NB!

                    /* Alt.:
                    FileWriter writer = new FileWriter(myFile, true);  //true = append
                    BufferedWriter output = new BufferedWriter(writer);
                    output.write(content);
                    output.close();
                    writer.close();
                    */
                } else {
                    Toast.makeText(this, "Har ikke tilgang eller fikk ikke opprettet mappe.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Ingen tilgang til eksternt område.", Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyReadPermissions(AppCompatActivity activity) {
        // Kontrollerer om vi har tilgang til eksternt område:
        int permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Dersom vi ikke har nøfvendige tilganger spør bruker om tilgang.
            // Fortsetter i metoden onRequestPermissionsResult() ...
            ActivityCompat.requestPermissions(activity, requiredPermissions, CALLBACK_REQUEST_READ_EXTERNAL);
        } else {
            //Skriver til ekstern fil dersom alt ok:
            readFromExternalFile();
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
            return true;
        else
            return false;
    }

    //LES fra EKSTERN fil:
    public void doReadExtFile(View view) {
        //Sjekker først rettigheter:
        verifyReadPermissions(this);
    }

    public void readFromExternalFile() {
        //File-objekt som representerer angitt mappe i eksternt lager:
        File externalDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        //File-objekt for navngitt fili angitt mappe:
        File file = new File(externalDirectory, fileNameExternal);
        String line="";
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            //Leser linje for linje, legger i stringbuilder:
            while ((line = br.readLine()) != null) {
                text.append(line + '\n');
            }
            br.close();
            TextView tvOutput = (TextView)findViewById(R.id.tvOutput);
            tvOutput.setText(text.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doClear(View view) {
        TextView tvOutput = (TextView)findViewById(R.id.tvOutput);
        tvOutput.setText("");
    }
}
