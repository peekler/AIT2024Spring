package hu.ait.tictactoe.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    lateinit var paintBackground: Paint
    lateinit var paintLine: Paint

    private var circles = mutableListOf<PointF>()

    private var myX: Float = -1F
    private var myY: Float = -1F


    init {
        paintBackground = Paint()
        paintBackground.setColor(Color.BLACK)
        paintBackground.style = Paint.Style.FILL

        paintLine = Paint()
        paintLine.setColor(Color.WHITE)
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        //if (myX > -1F && myY > -1F) {
        //    canvas.drawCircle(myX, myY, 50f, paintLine)
        //}

        circles.forEach {
            canvas.drawCircle(it.x, it.y, 50f, paintLine)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x
            val tY = event.y
            //myX = tX
            //myY = tY
            circles.add(PointF(tX, tY))

            invalidate()
        }

        return true
    }

}