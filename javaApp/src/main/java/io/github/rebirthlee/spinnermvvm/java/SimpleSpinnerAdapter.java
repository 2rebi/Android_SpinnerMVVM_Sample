package io.github.rebirthlee.spinnermvvm.java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class SimpleSpinnerAdapter extends ArrayAdapter {

    @LayoutRes
    private int layoutId;
    private int viewModelId;
    private SparseArray<ViewDataBinding> bindings = new SparseArray<>();
    private WeakReference<Spinner> parent = new WeakReference<>(null);

    private View.OnTouchListener getViewTouchListener = (view, t) -> {
        if (t.getAction() == MotionEvent.ACTION_UP && t.getPointerCount() == 1) {
            Spinner s = this.parent.get();
            if (s == null) return false;
            Rect r = new Rect();
            s.getLocalVisibleRect(r);
            if (r.contains((int)t.getX(), (int)t.getY())) {
                s.performClick();
            }
        }
        return false;
    };

    public SimpleSpinnerAdapter(@NonNull Context context,
                                @LayoutRes int resource,
                                int viewModelId,
                                @NonNull List objects) {
        super(context, resource, objects);
        layoutId = resource;
        this.viewModelId = viewModelId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.parent = new WeakReference<>((Spinner) parent);
        final ViewDataBinding viewBinding;
        if (convertView == null) {
            viewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), layoutId, parent, false);
            viewBinding.getRoot().setOnTouchListener(getViewTouchListener);
        } else {
            viewBinding = DataBindingUtil.bind(convertView);
        }
        viewBinding.setVariable(viewModelId, getItem(position));
        viewBinding.executePendingBindings();
        return viewBinding.getRoot();
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Holder holder = viewHolders.size() > position ? viewHolders.get(position) : null;
//
//        if (holder == null) {
//            holder = new Holder(LayoutInflater.from(getContext()).inflate(layoutId, parent, false));
//            holder.item.setOnTouchListener((view, t) -> {
//                if (t.getAction() == MotionEvent.ACTION_UP && t.getPointerCount() == 1) {
//                    Spinner s = this.parent.get();
//                    if (s == null) return false;
//
//                    s.setSelection(position, true);
//                    try {
//                        Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
//                        method.setAccessible(true);
//                        method.invoke(s);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                return false;
//            });
//            holder.dataBinding.setVariable(getViewModelVariable(), getItem(position));
//            holder.dataBinding.executePendingBindings();
//            viewHolders.put(position, holder);
//        }
//
//        return holder.item;

        ViewDataBinding viewBinding = bindings.size() > position ? bindings.get(position) : null;
        if (viewBinding == null) {
            viewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), layoutId, parent, false);
            viewBinding.getRoot().setOnTouchListener((view, t) -> {
                if (t.getAction() == MotionEvent.ACTION_UP && t.getPointerCount() == 1) {
                    Spinner s = this.parent.get();
                    if (s == null) return false;

                    s.setSelection(position, true);
                    try {
                        Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                        method.setAccessible(true);
                        method.invoke(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            });
            viewBinding.setVariable(viewModelId, getItem(position));
            viewBinding.executePendingBindings();
            bindings.put(position, viewBinding);
        }

        return viewBinding.getRoot();
    }

//    protected abstract int getViewModelVariable();

//    private static class Holder<D extends ViewDataBinding> {
//        View item;
//        D dataBinding;
//
//        Holder(View v) {
//            if (v == null) {
//                throw new IllegalArgumentException("itemView must not be null");
//            }
//            item = v;
//            dataBinding = DataBindingUtil.bind(item);
//        }
//    }
}