package ipca.test.interruptor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class VerticalSlider  : View {

    private var touchY = 0F
    private var _percent = 0F
    var percent : Float
        get() {
            return _percent
        }
        set(value) {
            _percent = value
            touchY = height * (1 - _percent/100f)
            invalidate()
        }

    private var onValueChange : ((Float)->Unit)? = null

    fun setOnValueChangeListener (callback :(Float)->Unit ) {
        onValueChange = callback
    }


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = Color.RED
        val outerRect = Rect( 0, 0, width, height)
        canvas?.drawRect(outerRect, paint)

        paint.color = Color.BLUE
        //if (_percent > 50) {
            val innerRect = Rect(15, (touchY.toInt() + 15), width - 15, (touchY.toInt() - 15) + height/2)
            canvas?.drawRect(innerRect, paint)
        /*} else{
            val innerRect = Rect(15, height - 15, width - 15,  (height / 2) - 15)
            canvas?.drawRect(innerRect, paint)
        }*/

        paint.color = Color.WHITE
        paint.textSize = 40.0F

        canvas?.drawText("ON", (width/2).toFloat(), 40f, paint)
        canvas?.drawText("OFF", (width/2).toFloat(), height.toFloat() - 20f, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        val y = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE
            -> {
                y?.let {
                    var v = it
                    var b = it

                    /*if (v <= 0f)  v = 0f
                    if (v >= height) v = height.toFloat()
                    touchY = v*/

                    if (b >= height/2) b = height.toFloat()
                    if (b >= height) b = (height/2).toFloat()
                    if (b <= 0f) b = 0f
                    if (b >= height) b = height.toFloat()
                    touchY = b

                    _percent = ((height - touchY)/height)*100f
                    onValueChange?.invoke(_percent)
                    invalidate()

                }
                return true
            }

        }

        return false

    }
}