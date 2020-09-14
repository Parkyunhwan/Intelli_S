package es.situm.wayfinding.sample.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.situm.wayfinding.sample.R;

/**
 * Just another RecyclerView.Adapter helper class.
 *
 * @author Rodrigo Lago.
 */
class ViewHolderHelper {

    private Context context;
    private SamplesAdapter adapter;

    void init(Context context, RecyclerView recyclerView, OnSampleClickListener listener) {
        this.context = context;
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SamplesAdapter(listener);
        recyclerView.setAdapter(adapter);
    }

    void addItem(@StringRes int sampleName, Class<? extends FragmentActivity> sampleClass) {
        String sampleNameStr = context.getString(sampleName);
        DataHolder item = new DataHolder(sampleNameStr, sampleClass);
        adapter.data.add(item);
        adapter.notifyDataSetChanged();
    }

    // =============================================================================================
    // Yet another RecyclerView.Adapter.
    // =============================================================================================

    class SamplesAdapter extends RecyclerView.Adapter<SamplesViewHolder> {

        private List<DataHolder> data;
        private OnSampleClickListener listener;

        SamplesAdapter(OnSampleClickListener pListener) {
            data = new ArrayList<>();
            listener = pListener;
        }

        @NonNull
        @Override
        public SamplesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.samples_list_item, parent, false);
            return new SamplesViewHolder(this, v);
        }

        @Override
        public void onBindViewHolder(@NonNull SamplesViewHolder holder, int position) {
            DataHolder item = data.get(position);
            holder.sampleName.setText(item.sampleName);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        void onPositionClick(int position) {
            DataHolder item = data.get(position);
            listener.onSampleClick(item);
        }
    }

    interface OnSampleClickListener {
        void onSampleClick(DataHolder item);
    }

    private class SamplesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SamplesAdapter parent;
        private TextView sampleName;

        SamplesViewHolder(SamplesAdapter pParent, @NonNull TextView v) {
            super(v);
            parent = pParent;
            sampleName = v.findViewById(R.id.sample_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                parent.onPositionClick(pos);
            }
        }
    }

    // Could be parameterized, but enough for this purpose.
    class DataHolder {
        private String sampleName;
        private Class<? extends FragmentActivity> sampleClass;

        DataHolder(String sampleName, Class<? extends FragmentActivity> sampleClass) {
            this.sampleName = sampleName;
            this.sampleClass = sampleClass;
        }

        public String getSampleName() {
            return sampleName;
        }

        public Class<? extends FragmentActivity> getSampleClass() {
            return sampleClass;
        }
    }
}
