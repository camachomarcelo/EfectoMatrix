package com.dranser.efectomatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Dranser on 14/10/2017.
 */

public class EfectoMatrix extends View{

    private static final Random random = new Random();
    private int width, height;
    private Canvas canvas;
    private Bitmap canvasBmp;
    private int tamañoFuente = 15;
    private int tamañoColumna;
    private char[] caracteres = "+-*/!^'([])#0&?,=$|°%0123456789".toCharArray();
    private int[] txtPosPorColumna;
    private Paint paintTxt, paintBg, paintBgBmp, paintIniBg;

    public EfectoMatrix(Context context, AttributeSet attrs){
        super(context, attrs);
        paintTxt = new Paint();
        paintTxt.setStyle(Paint.Style.FILL);
        paintTxt.setColor(Color.GREEN);
        paintTxt.setTextSize(tamañoFuente);

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setAlpha(5);
        paintTxt.setStyle(Paint.Style.FILL);

        paintBgBmp = new Paint();
        paintBg.setColor(Color.BLACK);

        paintIniBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setAlpha(255);
        paintTxt.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        canvasBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBmp);
        canvas.drawRect(0, 0, width, height, paintIniBg);
        tamañoColumna = width / tamañoFuente;

        txtPosPorColumna = new int[tamañoColumna + 1];

        for(int x = 0; x < tamañoColumna; x++){
            txtPosPorColumna[x] = random.nextInt(height / 2) + 1;
        }
    }

    private void dibujarTexto(){
        for (int i = 0; i < txtPosPorColumna.length; i++){
          canvas.drawText("" + caracteres[random.nextInt(caracteres.length)], i * tamañoFuente, txtPosPorColumna[i] * tamañoFuente, paintTxt);

            if (txtPosPorColumna[i] * tamañoFuente > height && Math.random() > 0.975){
                txtPosPorColumna[i] = 0;
            }

            txtPosPorColumna[i]++;
        }
    }

    private void dibujarCanvas(){
        canvas.drawRect(0, 0, width, height, paintBg);
        dibujarTexto();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBmp, 0, 0, paintBgBmp);
        dibujarCanvas();
        invalidate();
    }
}

