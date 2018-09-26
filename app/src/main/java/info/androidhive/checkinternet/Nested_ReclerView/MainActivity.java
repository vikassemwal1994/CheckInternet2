package info.androidhive.checkinternet.Nested_ReclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import info.androidhive.checkinternet.R;

public class MainActivity extends AppCompatActivity {

    int j=0;
    int[] array = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainxml);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new CategoriesAdapter());
    }

    private class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

        @Override
        public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.category, parent, false);
            return new CategoryViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CategoryViewHolder holder, final int position) {
            holder.title.setText("category: " + position);
//            holder.recycler.setAdapter(new ItemsAdapter());
            final ItemsAdapter adapter = new ItemsAdapter(position);
            holder.recycler.setAdapter(adapter);

            holder.add_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("first value",""+j);
                    array[position] = array[position]+1;
                    adapter.addItem(position);
            }
            });
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final RecyclerView recycler;
        private final ImageView add_list_item;

        private CategoryViewHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.title);
            this.recycler = (RecyclerView) itemView.findViewById(R.id.list);
            this.add_list_item = (ImageView) itemView.findViewById(R.id.add_list_item);

            recycler.setLayoutManager(new LinearLayoutManager(itemView.getContext()
                    , LinearLayoutManager.HORIZONTAL, false));

        }
    }

    private class ItemsAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        int i=0;
        int position=0;

        private ItemsAdapter(int position) {
            Log.e("second value",""+i);
            this.i = i;
            this.position = position;
        }

        private void addItem(int position) {
            notifyDataSetChanged();
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item, parent, false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.title.setText("item: " + position);

            Glide.with(MainActivity.this)
                    .load(R.drawable.images)
                    .into(holder.image);

        }

        @Override
        public int getItemCount() {
            return array[position];
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageView image;
        private final EditText et_name;

        private ItemViewHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.title);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.et_name = (EditText) itemView.findViewById(R.id.et_name);


        }
    }
}