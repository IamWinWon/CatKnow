package ru.mrwinwon.catknowledge.ui.main.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mrwinwon.catknowledge.databinding.CatItemBinding;
import ru.mrwinwon.catknowledge.model.local.CatEntity;
import ru.mrwinwon.catknowledge.ui.main.base.BaseAdapter;
import ru.mrwinwon.catknowledge.viewModel.CatsViewModel;

//public class CatsListAdapter extends BaseAdapter<CatsListAdapter.CatsViewHolder, CatEntity> {
public class CatsListAdapter extends RecyclerView.Adapter<CatsListAdapter.CatsViewHolder> implements BindableAdapter<List<CatEntity>> {

    private List<CatEntity> cats;
    private CatsViewModel catsViewModel;

    public CatsListAdapter(CatsViewModel catsViewModel) {
        this.catsViewModel = catsViewModel;
    }

    @Override
    public void setData(List<CatEntity> data) {
        this.cats = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CatsViewHolder.create(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CatsViewHolder holder, final int position) {
        holder.onBind(cats.get(position));
        holder.itemView.setOnLongClickListener(v -> {
            catsViewModel.deleteCat(cats.get(position));
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return cats != null ? cats.size() : 0;
    }

    static class CatsViewHolder extends RecyclerView.ViewHolder {

        private final CatItemBinding catItemBinding;

        private static CatsViewHolder create(LayoutInflater inflater, ViewGroup viewGroup) {
            CatItemBinding catItemBinding = CatItemBinding.inflate(inflater, viewGroup, false);
            return new CatsViewHolder(catItemBinding);
        }

        private CatsViewHolder(final CatItemBinding catItemBinding) {
            super(catItemBinding.getRoot());
            this.catItemBinding = catItemBinding;
        }

        private void onBind(CatEntity catEntity) {
            catItemBinding.setCat(catEntity);
            catItemBinding.executePendingBindings();
        }
    }
}
