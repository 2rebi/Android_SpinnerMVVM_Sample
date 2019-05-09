package io.github.rebirthlee.spinnermvvm.kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner

import java.lang.ref.WeakReference

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class SimpleSpinnerAdapter(context: Context,
                                                             @param:LayoutRes @field:LayoutRes
                                                             private val layoutId: Int,
                                                             private val viewModelVariable: Int,
                                                             objects: List<Any>) : ArrayAdapter<Any>(context, layoutId, objects) {
    private val bindings = SparseArray<ViewDataBinding>()
    private val getViewTouchListener = { view: View, t: MotionEvent ->
        if (t.getAction() == MotionEvent.ACTION_UP && t.getPointerCount() == 1) {
            this.parent.get()?.let {
                val r = Rect()
                it.getLocalVisibleRect(r)
                if (r.contains(t.getX().toInt(), t.getY().toInt())) {
                    it.performClick()
                }
            }
        }
        false
    }


    private var parent = WeakReference<Spinner>(null)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        this.parent = WeakReference(parent as Spinner)
        var viewBining  = convertView?.let {
            DataBindingUtil.bind<ViewDataBinding>(it)
        } ?: DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false)
        viewBining.root.setOnTouchListener(getViewTouchListener)
        viewBining.setVariable(viewModelVariable, getItem(position))
        viewBining.executePendingBindings()
        return viewBining.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val holder: Holder<D> = viewHolders.get(position, null)
//                ?: {
//                    val inst: Holder<D> = Holder(LayoutInflater.from(context).inflate(layoutId, parent, false))
//                    viewHolders.put(position, inst)
//                    inst
//                }()
        val viewBinding = bindings.get(position, null)
                ?: DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false)
        viewBinding.root.setOnTouchListener { view, t ->
            if (t.action == MotionEvent.ACTION_UP && t.pointerCount == 1) {
                this.parent.get()?.let {
                    it.setSelection(position, true)
                    try {
                        val method = Spinner::class.java.getDeclaredMethod("onDetachedFromWindow")
                        method.setAccessible(true)
                        method.invoke(it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            false
        }
        viewBinding.setVariable(viewModelVariable, getItem(position))
        viewBinding.executePendingBindings()
        return viewBinding.root
    }


//    private class Holder<D : ViewDataBinding> internal constructor(internal var item: View?) {
//        internal var dataBinding: D? = null
//
//        init {
//            dataBinding = DataBindingUtil.bind(item ?: throw IllegalArgumentException("itemView must not be null"))
//        }
//    }
}