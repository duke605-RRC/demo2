package io.github.duke605.qrdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.widget.TextView;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import io.github.duke605.qrdemo.contract.IDetected;

public class MainActivity extends AppCompatActivity implements IDetected {

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
                .setRequestedPreviewSize(700, 700)
                .setAutoFocusEnabled(true)
                .build();

        preview.getHolder().addCallback(new SurfaceHandler(cameraSource));
        barcodeDetector.setProcessor(new BarcodeProcessor(this));
    }

    @Override
    public void barcodeDetected(final SparseArray<Barcode> barcodes) {
        text.post(new Runnable() {

            @Override
            public void run() {
                System.out.println("" + barcodes.valueAt(0).displayValue);
            }
        });
    }

    private void bind() {
        preview = (SurfaceView) findViewById(R.id.camera_view);
        text = (TextView) findViewById(R.id.text);
    }
}
