package good.damn.textviewsetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import good.damn.textviewset.TextViewSet;
import good.damn.textviewset.interfaces.TextViewSetListener;

public class MainActivity extends AppCompatActivity {

    private byte mRepeats = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        TextViewSet textViewSet = new TextViewSet(this);
        textViewSet.setBackgroundColor(0xff000000);
        textViewSet.setTextInterval(15.0f);
        textViewSet.setTextSize(18.0f * metrics.density);
        textViewSet.setTextColor(0xffffffff);
        textViewSet.setSourceOffset(5);
        textViewSet.setListener(new TextViewSetListener() {
            @Override
            public void onFinish() {
                if (mRepeats >= 2) {
                    return;
                }
                mRepeats++;
                textViewSet.start();
            }
        });

        textViewSet.setAnimation(TextViewSet.ANIMATION_ALPHA);

        textViewSet.setSource(new String[]{
                "Night",
                "Fun",
                "Love",
                "Programming",
                "Computer Science",
                "Day",
                "Cup",
                "Headphones",
                "CPU",
                "GPU",
                "Keyboard",
                "Mice",
                "Notepad"
        });

        setContentView(textViewSet);
        textViewSet.start();
    }
}