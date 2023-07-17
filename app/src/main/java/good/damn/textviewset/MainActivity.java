package good.damn.textviewset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextViewSet textViewSet = new TextViewSet(this);
        textViewSet.setBackgroundColor(0xff000000);
        textViewSet.setSource(new String[]{
                "Night",
                "Fun",
                "Love",
                "Programming",
                "Computer Science"
        });


        setContentView(textViewSet);
        textViewSet.start();
    }
}