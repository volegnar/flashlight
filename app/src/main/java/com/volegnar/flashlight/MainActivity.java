package com.volegnar.flashlight;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;
import android.graphics.Color;
import android.view.Gravity;

public class MainActivity extends Activity {

    private Camera camera;
    private boolean isOn = false;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.BLACK);

        button = new Button(this);
        button.setText("TURN ON");
        button.setTextSize(24);

        layout.addView(button,
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                120
            )
        );

        setContentView(layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOn) {
                    turnOff();
                } else {
                    turnOn();
                }
            }
        });
    }

    private void turnOn() {
        try {
            camera = Camera.open();

            Parameters params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();

            isOn = true;
            button.setText("TURN OFF");

        } catch (Exception e) {
            turnOff();
        }
    }

    private void turnOff() {
        try {
            if (camera != null) {
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        } catch (Exception e) {
            camera = null;
        }

        isOn = false;

        if (button != null) {
            button.setText("TURN ON");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        turnOff();
    }

    @Override
    protected void onDestroy() {
        turnOff();
        super.onDestroy();
    }
}
