package io.github.rebirthlee.spinnermvvm;

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

public abstract class SimpleSpinnerAdapter<D extends ViewDataBinding, VM> extends ArrayAdapter<VM> {

    @LayoutRes
    private int layoutId;
    private SparseArray<Holder> viewHolders = new SparseArray<>();
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
                                @NonNull List<VM> objects) {
        super(context, resource, objects);
        layoutId = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.parent = new WeakReference<>((Spinner) parent);
        View ret = convertView;
        if (ret == null) {
            ret = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
            ret.setOnTouchListener(getViewTouchListener);
        }
        Holder holder = new Holder(ret);
        holder.dataBinding.setVariable(getViewModelVariable(), getItem(position));
        holder.dataBinding.executePendingBindings();
        return holder.item;
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder = viewHolders.size() > position ? viewHolders.get(position) : null;

        if (holder == null) {
            holder = new Holder(LayoutInflater.from(getContext()).inflate(layoutId, parent, false));
            holder.item.setOnTouchListener((view, t) -> {
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
            holder.dataBinding.setVariable(getViewModelVariable(), getItem(position));
            holder.dataBinding.executePendingBindings();
            viewHolders.put(position, holder);
        }

        return holder.item;
    }

    protected abstract int getViewModelVariable();

    private static class Holder<D extends ViewDataBinding> {
        View item;
        D dataBinding;

        Holder(View v) {
            if (v == null) {
                throw new IllegalArgumentException("itemView must not be null");
            }
            item = v;
            dataBinding = DataBindingUtil.bind(item);
        }
    }
}