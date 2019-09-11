package com.mehmettas.autocleartextview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class CustomEdittext
    @JvmOverloads constructor(context: Context, attrs:AttributeSet?=null,defStyle:Int=0)
    : AppCompatEditText(context,attrs,defStyle)
{
    private var clearButton:Drawable?=null

    init {

        hint = "Hi there"

        setShadowLayer(0f,0f,0f,R.color.colorAccent)

        setBackgroundResource(R.drawable.edittext_background)
        gravity = Gravity.CENTER_VERTICAL

        val scale = resources.displayMetrics.density
        val dpAsPixels = (10 * scale + 0.5f).toInt()
        setPadding(dpAsPixels,0,dpAsPixels,0)

        clearButton = context.scaledDrawableResources(R.drawable.ic_clear,48,48)

        highlightColor = resources.getColor(R.color.transperant)

        hasFocus()

        setOnTouchListener(object: OnTouchListener{

            override fun onTouch(view: View?, event: MotionEvent?): Boolean {

                background = resources.getDrawable(R.drawable.edittext_background,null)

                if(compoundDrawablesRelative[2]!=null){
                    var clearButtonStart = 0.0
                    var clearButtonEnd = 0.0
                    var isClearButtonClicked = false

                    if(layoutDirection == View.LAYOUT_DIRECTION_LTR)
                    {
                        clearButtonStart = (width - paddingEnd - clearButton!!.intrinsicWidth).toDouble()
                        if (event!!.getX()>clearButtonStart)
                            isClearButtonClicked = true
                    }
                    else
                    {
                        clearButtonEnd = (clearButton!!.intrinsicWidth + paddingEnd).toDouble()
                        if (event!!.getX()<clearButtonEnd)
                            isClearButtonClicked = true
                    }

                    if(isClearButtonClicked)
                    {
                        if (event.action == MotionEvent.ACTION_DOWN)
                        {
                            clearButton = context.scaledDrawableResources(R.drawable.ic_clear,48,48)
                            showClearButton()
                        }
                        if (event.action == MotionEvent.ACTION_UP)
                        {
                            clearButton = context.scaledDrawableResources(R.drawable.ic_clear_disabled,48,48)
                            text?.clear()
                            hideClearButton()
                            return true
                        }
                    }
                    else
                        return false

                }
                return false
            }
        })


        addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                showClearButton()
            }
        })

    }


    fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,clearButton,null)
    }

    fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null)
    }
}