package com.tadi.lekovizdravstvomk.helpers;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tadi.lekovizdravstvomk.R;

import java.util.ArrayList;
import java.util.List;




public class ExpandableFAB extends FloatingActionButton {

    private static final String TAG = "ExpandableFAB";
    private static final int ROTATION_DEGREES = -135;

    private boolean expanded = false;
    private boolean ignoreToggle = false;
    private List<View> subViews = new ArrayList<>();

    public ExpandableFAB(Context context) {
        super(context);
    }

    public ExpandableFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addSubView(final View view) {
        if (!subViews.contains(view)) {
            subViews.add(view);
        } else {
            Log.w(TAG, "addSubView: view already added, ignoring");
        }
    }

    public void expand() {
        if (!expanded) {
            this.animate().setListener(new RotationListener(this)).rotation(ROTATION_DEGREES);
            for (int i = 0; i < subViews.size(); i++) {
                View subView = subViews.get(i);
                subView.setVisibility(VISIBLE);
                subView.animate().translationY(-(getResources().getDimension(R.dimen.fab_expand_base_margin)
                        + (i + 1) * getResources().getDimension(R.dimen.fab_expand_additive_margin)))
                        .alpha(1);
            }
            expanded = true;
        } else {
            Log.w(TAG, "expand: already expanded");
        }
    }

    public void collapse() {
        if (expanded) {
            this.animate().setListener(new RotationListener(this)).rotation(0);
            for (int i = subViews.size() - 1; i >= 0; i--) {
                final View subView = subViews.get(i);
                subView.animate().translationY(0).alpha(0).setListener(new CollapseListener(subView));
            }
            expanded = false;
        } else {
            Log.w(TAG, "collapse: already collapsed");
        }
    }

    public void toggle() {
        if (ignoreToggle) {
            Log.d(TAG, "Ignoring toggle because animation is ongoing");
            return;
        }
        if (expanded) {
            collapse();
        } else {
            expand();
        }
    }

    public List<View> getSubViews() {
        return subViews;
    }

    public void setSubViews(List<View> subViews) {
        this.subViews = subViews;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isIgnoreToggle() {
        return ignoreToggle;
    }

    public void setIgnoreToggle(boolean ignoreToggle) {
        this.ignoreToggle = ignoreToggle;
    }

    private static class CollapseListener implements Animator.AnimatorListener {

        final View view;

        public CollapseListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            view.setVisibility(GONE);
            view.animate().setListener(null);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    private static class RotationListener implements Animator.AnimatorListener {

        private ExpandableFAB fab;

        public RotationListener(ExpandableFAB fab) {
            this.fab = fab;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            fab.setIgnoreToggle(true);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            fab.setIgnoreToggle(false);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            fab.setIgnoreToggle(false);
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}

