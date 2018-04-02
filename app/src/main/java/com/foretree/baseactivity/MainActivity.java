package com.foretree.baseactivity;

import android.support.v7.widget.LinearLayoutManager;

import com.foretree.base.DataBindingActivity;
import com.foretree.base.adapter.DataBindingRecyclerAdapter;
import com.foretree.base.adapter.DataBindingRecyclerHolder;
import com.foretree.baseactivity.databinding.ActivityMainBinding;
import com.foretree.baseactivity.databinding.ItemMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends DataBindingActivity<ActivityMainBinding> {
    private static List<Model> models = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onAfterViews() {
        getBinding().text.setText("hahahahahahha");
        getBinding().list.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 100; i++) {
            models.add(new Model("item" + i));
        }
        getBinding().list.setAdapter(new Adapter(models));

    }


    public static class Adapter extends DataBindingRecyclerAdapter<Model, ItemMainBinding> {

        public Adapter(List<Model> data) {
            super(data);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_main;
        }

        @Override
        public void onBindViewHolder(DataBindingRecyclerHolder<ItemMainBinding> holder, int position, Model item) {
            holder.binding.setModel(item);
        }
    }

    public static class Model {
        private String text;

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public Model(String s) {
            this.text = s;
        }
    }
}
