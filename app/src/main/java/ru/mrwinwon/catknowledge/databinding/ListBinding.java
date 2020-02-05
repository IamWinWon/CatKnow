package ru.mrwinwon.catknowledge.databinding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mrwinwon.catknowledge.model.remote.Resource;
import ru.mrwinwon.catknowledge.ui.main.base.BaseAdapter;
import ru.mrwinwon.catknowledge.ui.main.recycler.BindableAdapter;

public class ListBinding {

    private ListBinding() {
        // Private Constructor to hide the implicit one
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = "resource")
    public static void setResource(RecyclerView recyclerView, Resource resource) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null)
            return;

        if (resource == null || resource.data == null)
            return;

        if (adapter instanceof BindableAdapter) {
            ((BaseAdapter) adapter).setData((List) resource.data);
        }
    }
}
