package io.github.duke605.qrdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {

    private SurfaceView preview;
    private TextView text;

    @Override
    @SuppressWarnings("all")
    protected void onCreate(Bundle savedInstanceState) {
        final CameraSource cameraSource;
        BarcodeDetector barcodeDetector;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting controls from view
        this.bind();

        // Building
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .build();

        preview.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                // Starting camera preview
                try {
                    cameraSource.start(holder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                // Stopping camera preview
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                // Final because this can't be accessed in another thread if its not
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {

                    // This has to be posted to the thread it came from because no other thread
                    // (we're in another thread right now) can modify the view except the one
                    // it was created on
                    text.post(new Runnable() {

                        public void run() {
                            text.setText(barcodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });
    }

    private void bind() {
        preview = (SurfaceView) findViewById(R.id.camera_view);
        text = (TextView) findViewById(R.id.text);
    }
}
