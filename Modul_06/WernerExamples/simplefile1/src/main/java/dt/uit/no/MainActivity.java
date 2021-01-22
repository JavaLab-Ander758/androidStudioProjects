package dt.uit.no;

import android.os.Environment;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
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

// Demonstrerer lesing/skriving til og fra filer som ligger  i public eksternt område.
// API-level 29++
public class MainActivity extends AppCompatActivity {

    private String fileNameExternal = "eksternTekstFil.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String externalFilesDirs = getExternalFilesDirs( null).toString();
        TextView tvExtFileDir = (TextView)findViewById(R.id.tvExtFileDir);
        tvExtFileDir.setText("Eksternt filnavn:\n" + externalFilesDirs + "/" + fileNameExternal);
    }

    //SKRIV til EKSTERN fil
    public void doWriteExtFile(View view) {
        try {
            EditText etInput = findViewById(R.id.etExternalInput);
            String content = etInput.getText().toString();

            if (isExternalStorageWritable()) {
                //Oppretter et filobjekt i angitt mappe i eksternt lager:
                /* FRA DOC: getExternalFilesDir(type):

                Returns the absolute path to the directory on the primary shared/external storage device where the application can place persistent files it owns.
                These files are internal to the applications, and not typically visible to the user as media.

                type-parameter:  File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                */

                File externalFilesDir = getExternalFilesDir(null);
                File myFile = new File(externalFilesDir, fileNameExternal);

                // File myFile = new File(dir, fileNameExternal);
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
                Toast.makeText(this, "Ingen tilgang til eksternt område.", Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sjekker om eksternt medie finnes/er tilgjengelig;
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
            return true;
         else
            return false;
    }

    //LES fra EKSTERN fil:
    public void doReadExtFile(View view) {
        File externalFilesDir = getExternalFilesDir(null);
        File file = new File(externalFilesDir, fileNameExternal);
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

    public void doClear(View view) {
        TextView tvOutput = (TextView)findViewById(R.id.tvOutput);
        tvOutput.setText("");
    }
}
