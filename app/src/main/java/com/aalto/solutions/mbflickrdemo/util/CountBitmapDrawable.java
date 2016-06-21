package com.aalto.solutions.mbflickrdemo.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;

import com.aalto.solutions.mbflickrdemo.R;


public class CountBitmapDrawable extends BitmapDrawable
{
	private static int PADDING = 7;
	private int count = 0;
	private TextPaint textPaint;
	private StaticLayout textLayout;
	private Rect rect;
	private boolean showZero = false;
	private Drawable countDrawable;
	private Rect bounds;
	private boolean centerIcon = true;
	private int maxVal;

	//==============================================================================

	public CountBitmapDrawable(Resources res, Bitmap bitmap)
	{
		super(res, bitmap);

		textPaint = new TextPaint();
		textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, res.getDisplayMetrics()));
		textPaint.setColor(Color.WHITE);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);
		textPaint.setTextAlign(Align.CENTER);
		
		PADDING = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, res.getDisplayMetrics());

		countDrawable = res.getDrawable(R.drawable.count_drawable);

		refreshTextLayout();

		rect = new Rect();
		bounds = new Rect();
	}

	//==============================================================================

	private void refreshTextLayout()
	{
		String str = String.valueOf(maxVal) + "/" + String.valueOf(count);

		int width = (int) StaticLayout.getDesiredWidth(str, textPaint);

		textLayout = new StaticLayout(str, textPaint, width, Alignment.ALIGN_CENTER, 1F, 1F, false);
	}

	//==============================================================================

	@Override
	public void draw(Canvas canvas)
	{
		int size = textLayout.getWidth() > textLayout.getHeight() ? textLayout.getWidth() : textLayout.getHeight();

		bounds.set(getBounds());

		getBounds().right = getBounds().right - size - (2 * PADDING);
		
		if(centerIcon)
			getBounds().left = getBounds().left + size + (2 * PADDING);
		
		super.draw(canvas);

		getBounds().set(bounds);

		// Skip drawing when value is zero
		if (count == 0 && !showZero)
			return;

		rect.left = 0;
		rect.top = 0;

		Log.v("CountDrawable", "Size : " + size);

		rect.right = size + (2 * PADDING);
		rect.bottom = size + (2 * PADDING);

		int x = getBounds().right - (rect.right);
		int y = getBounds().top;

		canvas.translate(x, y);

		countDrawable.setBounds(rect);
		countDrawable.draw(canvas);

		canvas.translate(-x, -y);

		x = getBounds().right - (rect.right / 2);
		y = getBounds().top + ((size - textLayout.getHeight()) / 2) + PADDING;

		canvas.translate(x, y);

		textLayout.draw(canvas);

		canvas.translate(-x, -y);
	}

	//==============================================================================

	public boolean isShowZero() {
		return showZero;
	}

	public void setShowZero(boolean showZero)
	{
		this.showZero = showZero;
		invalidateSelf();
	}

	//==============================================================================

	public boolean isCenterIcon() {
		return centerIcon;
	}

	//==============================================================================

	public void setCenterIcon(boolean centerIcon)
	{
		this.centerIcon = centerIcon;
		invalidateSelf();
	}

	//==============================================================================

	public void setValues( final int aCnt, final int aMax)
	{
		if( aCnt == this.count )
			return;

		this.maxVal = aMax;
		this.count = aCnt;
		refreshTextLayout();
		invalidateSelf();
	}


	//==============================================================================

	public void setCount(int count)
	{
		if (count == this.count)
			return;

		this.count = count;
		refreshTextLayout();
		invalidateSelf();
	}

	//=============================================================================



	//==============================================================================

	public int getCount() {
		return count;
	}

	@Override
	public int getIntrinsicWidth() {
		int size = textLayout.getWidth() > textLayout.getHeight() ? textLayout.getWidth() : textLayout.getHeight();
		size += 2 * PADDING;
		return super.getIntrinsicWidth() + ((centerIcon ? 2 : 1) * size);
	}

	//==============================================================================
}
