package io.github.duke605.qrdemo;

import android.view.SurfaceHolder;

import com.google.android.gms.vision.CameraSource;

/**
 * Created by Cole on 3/15/2016.
 */
public class SurfaceHandler implements SurfaceHolder.Callback {

    private CameraSource source;

    public SurfaceHandler(CameraSource source) {
        this.source = source;
    }

    @Override
    @SuppressWarnings("all")
    public void surfaceCreated(SurfaceHolder holder) {

        // Starting camera preview
        try {
            source.start(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        source.stop();
    }
}
