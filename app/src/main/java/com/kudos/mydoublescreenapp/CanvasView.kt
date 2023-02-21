package com.kudos.mydoublescreenapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CanvasView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val path = Path()

    init {
        paint.isAntiAlias = true
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                Log.d("CanvasView", "onTouchEvent: ACTION DOWN")
                return true
            }
            MotionEvent.ACTION_UP -> {
                path.moveTo(x, y)
                Log.d("CanvasView", "onTouchEvent: ACTION UP")
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("CanvasView", "onTouchEvent: ACTION MOVE")
                path.lineTo(x, y)
            }
            else -> {
                Log.d("CanvasView", "onTouchEvent: ELSE")
                return false
            }
        }

        invalidate()
        return true
    }

    fun clear() {
        path.reset()
        invalidate()
    }

    fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

}
