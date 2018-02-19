package euanstudy.com.nutrilabelstudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOnClick();

    }


    public void buttonOnClick(){
        Button button= (Button)findViewById(R.id.start);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
//                startActivity(getActivities().get(0));
                startActivity(new Intent(MainActivity.this, OverlayCameraActivityTest.class));
            }
        });
    }

    private ArrayList<Intent> getActivities(){
        ArrayList<Intent> array = new ArrayList<>();
        Intent i = new Intent(MainActivity.this, MainCameraActivity.class);
        i.putExtra("nextClass","OverlayCameraActivity");
        array.add(i);
        Intent j = new Intent(MainActivity.this, OverlayCameraActivity.class);
        i.putExtra("nextClass","MainCameraActivity");
        array.add(j);
        Collections.shuffle(array);
        return array;
    }
}
