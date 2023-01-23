package printer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class Other
{
    public byte[] buf;
    public int index;
    private static final int WIDTH_80 = 576;
    private static final int WIDTH_58 = 384;
    private static int[] p0 = { 128 }; // P0 to P6 were octal?
    private static int[] p1 = { 64 };
    private static int[] p2 = { 32 };
    private static int[] p3 = { 16 };
    private static int[] p4 = { 8 };
    private static int[] p5 = { 4 };
    private static int[] p6 = { 2 };

    public Other(int length)
    {
        buf = new byte[length];
        index = 0;
    }

    public static StringBuilder RemoveChar(String str, char c)
    {
        StringBuilder sb = new StringBuilder();
        int length = str.length();

        for (int i = 0; i < length; i++)
        {
            char tmp = str.charAt(i);
            if (tmp != c)
                sb.append(tmp);
        }
        return sb;
    }


    public static boolean IsHexChar(char c)
    {
        return ((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f')) || (
                (c >= 'A') && (c <= 'F'));
    }

    private static final byte[] chartobyte = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            0, 0, 0, 0, 0, 0, 0, 10, 11, 12, 13, 14, 15 };


    public static byte HexCharsToByte(char ch, char cl)
    {
        byte b = (byte) (chartobyte[(ch - '0')] << 4 & 0xF0 | chartobyte[(cl - '0')] & 0xF);

        return b;
    }

    public static byte[] HexStringToBytes(String str)
    {
        int count = str.length();
        byte[] data = null;
        if (count % 2 == 0)
        {
            data = new byte[count / 2];

            for (int i = 0; i < count; i += 2)
            {
                char ch = str.charAt(i);
                char cl = str.charAt(i + 1);

                if ((IsHexChar(ch)) && (IsHexChar(cl)))
                {
                    if (ch >= 'a')
                        ch = (char) (ch - ' ');
                    if (cl >= 'a')
                        cl = (char) (cl - ' ');
                    data[(i / 2)] = HexCharsToByte(ch, cl);
                }
                else
                {
                    data = null;
                    break;
                }
            }
        }
        return data;
    }

    public void UTF8ToGBK(String Data)
    {
        try
        {
            byte[] bs = Data.getBytes("GBK");
            int DataLength = bs.length;
            for (int i = 0; i < DataLength; i++)
            {
                buf[(index++)] = bs[i];
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }


    public static byte[] StringTOGBK(String data)
    {
        byte[] buffer = null;
        try
        {
            buffer = data.getBytes("GBK");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

    public static Bitmap createAppIconText(Bitmap icon, String txt, float size, boolean is58mm, int hight)
    {
        if (is58mm)
        {
            Bitmap canvasBitmap = Bitmap.createBitmap(384, hight, Bitmap.Config.ARGB_8888);
            int width = canvasBitmap.getWidth();
            Canvas canvas = new Canvas(canvasBitmap);
            canvas.setBitmap(canvasBitmap);
            canvas.drawColor(-1);
            TextPaint paint = new TextPaint();
            paint.setColor(Color.rgb(0, 0, 0));
            paint.setTextSize(size);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setFakeBoldText(false);
            StaticLayout layout = new StaticLayout(txt, 0, txt.length(), paint, width, Layout.Alignment.ALIGN_NORMAL, 1.1F, 0.0F, true, TextUtils.TruncateAt.END, width);
            canvas.translate(0.0F, 5.0F);
            layout.draw(canvas);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            return canvasBitmap;
        }
        Bitmap canvasBitmap = Bitmap.createBitmap(576, hight, Bitmap.Config.ARGB_8888);
        int width = canvasBitmap.getWidth();
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.setBitmap(canvasBitmap);
        canvas.drawColor(-1);
        TextPaint paint = new TextPaint();
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setFakeBoldText(false);
        StaticLayout layout = new StaticLayout(txt, 0, txt.length(), paint, width, Layout.Alignment.ALIGN_NORMAL, 1.1F, 0.0F, true, TextUtils.TruncateAt.END, width);
        canvas.translate(0.0F, 5.0F);
        layout.draw(canvas);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return canvasBitmap;
    }

    public static byte[] byteArraysToBytes(byte[][] data)
    {
        int length = 0;
        for (int i = 0; i < data.length; i++)
            length += data[i].length;
        byte[] send = new byte[length];
        int k = 0;
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                send[(k++)] = data[i][j];
        return send;
    }

    public static Bitmap resizeImage(Bitmap bitmap, int w, int h)
    {
        Bitmap BitmapOrg = bitmap;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;


        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;


        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);


        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);


        return resizedBitmap;
    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int height = bmpOriginal.getHeight();
        int width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.0F);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0.0F, 0.0F, paint);
        return bmpGrayscale;
    }

    public static void saveMyBitmap(Bitmap mBitmap, String name)
    {
        File f = new File(Environment.getExternalStorageDirectory().getPath(),
                name);
        try
        {
            f.createNewFile();
        }
        catch (IOException localIOException)
        {
        }
        FileOutputStream fOut = null;
        try
        {
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
        }
        catch (IOException localIOException1)
        {
        }
    }

    public static byte[] thresholdToBWPic(Bitmap mBitmap)
    {
        int[] pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
        byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight()];

        mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0,
                mBitmap.getWidth(), mBitmap.getHeight());


        format_K_threshold(pixels, mBitmap.getWidth(),
                mBitmap.getHeight(), data);

        return data;
    }

    private static void format_K_threshold(int[] orgpixels, int xsize, int ysize, byte[] despixels)
    {
        int graytotal = 0;
        int grayave = 128;


        int k = 0;
        for (int i = 0; i < ysize; i++)
        {
            for (int j = 0; j < xsize; j++)
            {
                int gray = orgpixels[k] & 0xFF;
                graytotal += gray;
                k++;
            }
        }
        grayave = graytotal / ysize / xsize;


        k = 0;
        for (int i = 0; i < ysize; i++)
        {
            for (int j = 0; j < xsize; j++)
            {
                int gray = orgpixels[k] & 0xFF;

                if (gray > grayave)
                {
                    despixels[k] = 0;
                }
                else
                {
                    despixels[k] = 1;
                }
                k++;
            }
        }
    }

    public static void overWriteBitmap(Bitmap mBitmap, byte[] dithered)
    {
        int ysize = mBitmap.getHeight();
        int xsize = mBitmap.getWidth();

        int k = 0;
        for (int i = 0; i < ysize; i++)
        {
            for (int j = 0; j < xsize; j++)
            {
                if (dithered[k] == 0)
                {
                    mBitmap.setPixel(j, i, -1);
                }
                else
                {
                    mBitmap.setPixel(j, i, Color.rgb(0, 0, 0));
                }
                k++;
            }
        }
    }

    public static byte[] eachLinePixToCmd(byte[] src, int nWidth, int nMode)
    {
        int nHeight = src.length / nWidth;
        int nBytesPerLine = nWidth / 8;
        byte[] data = new byte[nHeight * (8 + nBytesPerLine)];
        int offset = 0;
        int k = 0;
        for (int i = 0; i < nHeight; i++)
        {
            offset = i * (8 + nBytesPerLine);
            data[(offset + 0)] = 29;
            data[(offset + 1)] = 118;
            data[(offset + 2)] = 48;
            data[(offset + 3)] = ((byte) (nMode & 0x1));
            data[(offset + 4)] = ((byte) (nBytesPerLine % 256));
            data[(offset + 5)] = ((byte) (nBytesPerLine / 256));
            data[(offset + 6)] = 1;
            data[(offset + 7)] = 0;
            for (int j = 0; j < nBytesPerLine; j++)
            {
                data[(offset + 8 + j)] =

                        ((byte) (p0[src[k]] + p1[src[(k + 1)]] + p2[src[(k + 2)]] + p3[src[(k + 3)]] + p4[src[(k + 4)]] + p5[src[(k + 5)]] + p6[src[(k + 6)]] + src[(k + 7)]));
                k += 8;
            }
        }

        return data;
    }
}