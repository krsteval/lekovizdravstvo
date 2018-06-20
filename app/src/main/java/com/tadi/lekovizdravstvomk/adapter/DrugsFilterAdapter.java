package com.tadi.lekovizdravstvomk.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.support.v4.content.ContextCompat;

import com.tadi.lekovizdravstvomk.MainActivity;
import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.model.WayOfPublishing;
import com.yalantis.filter.adapter.FilterAdapter;
import com.yalantis.filter.widget.FilterItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DrugsFilterAdapter  extends FilterAdapter<WayOfPublishing> {
    Context context;

    public DrugsFilterAdapter(@NotNull List<? extends WayOfPublishing> items, Context context) {
        super(items);
        this.context = context;

    }

    @NotNull
    @Override
    public FilterItem createView(int position, WayOfPublishing item) {
        FilterItem filterItem = new FilterItem(context);

        filterItem.setStrokeColor(R.color.colorPrimary);
        filterItem.setTextColor(R.color.colorPrimaryDark);
        filterItem.setCheckedTextColor(ContextCompat.getColor(context, R.color.white));
        filterItem.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        filterItem.setCheckedColor(R.color.input_register);
        filterItem.setText(item.getTip());
        filterItem.deselect();

        return filterItem;
    }
}
