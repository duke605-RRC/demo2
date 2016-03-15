package io.github.duke605.qrdemo.contract;

import android.util.SparseArray;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Cole on 3/15/2016.
 */
public interface IDetected {

    /**
     * Called when barcodes are detected from the camera
     *
     * @param barcodes Detected barcodes
     */
    void barcodeDetected(SparseArray<Barcode> barcodes);
}
