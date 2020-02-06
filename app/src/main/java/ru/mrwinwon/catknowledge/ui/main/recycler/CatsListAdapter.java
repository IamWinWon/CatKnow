package ru.mrwinwon.catknowledge.ui.main.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.mrwinwon.catknowledge.databinding.CatItemBinding;
import ru.mrwinwon.catknowledge.viewModel.CatsViewModel;

//public class CatsListAdapter extends BaseAdapter<CatsListAdapter.CatsViewHolder, CatEntity> {
public class CatsListAdapter extends RecyclerView.Adapter<CatsListAdapter.CatsViewHolder> implements BindableAdapter {

    private List<Cat> cats;
    private CatsViewModel catsViewModel;
//    CatItemBinding binding;

    public CatsListAdapter(CatsViewModel catsViewModel) {
        this.catsViewModel = catsViewModel;
    }

    @Override
    public void setData(List<Cat> data) {
        this.cats = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CatItemBinding binding = CatItemBinding.inflate(inflater, parent, false);
        return new CatsViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CatsViewHolder holder, final int position) {
        holder.onBind(cats.get(position));
        holder.itemView.setOnLongClickListener(v -> {
            catsViewModel.deleteCat(cats.get(position).getId());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return cats != null ? cats.size() : 0;
    }

    static class CatsViewHolder extends RecyclerView.ViewHolder {

        private final CatItemBinding catItemBinding;

        public CatsViewHolder(@NonNull View itemView) {
            super(itemView);
            catItemBinding = DataBindingUtil.bind(itemView);
        }

        void onBind(Cat cat) {
            catItemBinding.setCat(cat);
        }
    }


    @SuppressWarnings("unchecked")
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String v) {
        Glide.with(imageView.getContext())
                .load(v)
                .into(imageView);
    }
}
