package io.github.duke605.qrdemo;

import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import io.github.duke605.qrdemo.contract.IDetected;

/**
 * Created by Cole on 3/15/2016.
 */
public class BarcodeProcessor implements BarcodeDetector.Processor<Barcode> {

    private IDetected callback;

    public BarcodeProcessor(IDetected callback) {
        this.callback = callback;
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {
        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

        if (barcodes.size() > 0)
            callback.barcodeDetected(barcodes);
    }
}
