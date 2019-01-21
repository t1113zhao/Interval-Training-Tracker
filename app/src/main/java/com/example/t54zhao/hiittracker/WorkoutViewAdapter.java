package com.example.t54zhao.hiittracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.t54zhao.hiittracker.models.ComponentType;
import com.example.t54zhao.hiittracker.models.Interval;
import com.example.t54zhao.hiittracker.models.Period;
import com.example.t54zhao.hiittracker.models.Workout;
import com.example.t54zhao.hiittracker.models.WorkoutComponent;

import java.util.ArrayList;

public class WorkoutViewAdapter extends RecyclerView.Adapter {

    private Workout workout;

    private int IS_INTERVAL = 0;
    private int IS_PERIOD = 1;

    private Context context;

    public WorkoutViewAdapter(Workout workout, Context context) {
        this.workout = workout;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == IS_INTERVAL) {
            return new IntervalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_interval_row, parent, false));
        } else {
            return new PeriodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_period_row, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == IS_INTERVAL) {
            setIntervalView(holder, position);
        } else {
            setPeriodView(holder, position);
        }
    }

    private void setPeriodView(RecyclerView.ViewHolder holder, int position) {
        Period period = (Period) workout.getComponentAt(position);
        PeriodViewHolder periodViewHolder = (PeriodViewHolder) holder;
        periodViewHolder.bindData(period);
    }

    private void setIntervalView(RecyclerView.ViewHolder holder, int position) {
        Interval interval = (Interval) workout.getComponentAt(position);
        IntervalViewHolder intervalViewHolder = (IntervalViewHolder) holder;
        intervalViewHolder.bindData(interval);
    }

    @Override
    public int getItemViewType(int position) {
        WorkoutComponent component = workout.getComponentAt(position);
        if (component.componentType == ComponentType.INTERVAL) {
            return IS_INTERVAL;
        }
        return IS_PERIOD;
    }

    @Override
    public int getItemCount() {
        return workout.getComponents().size();
    }

    public class PeriodViewHolder extends RecyclerView.ViewHolder {
        public TextView periodNameTextView;
        public TextView periodTimeTextView;

        public PeriodViewHolder(View itemView) {
            super(itemView);
            this.periodNameTextView = itemView.findViewById(R.id.period_row_name_textView);
            this.periodTimeTextView = itemView.findViewById(R.id.period_row_time_textView);
        }

        public void bindData(Period period) {
            switch (period.componentType) {
                case WARMUP:
                    this.periodNameTextView.setText(context.getString(R.string.warmupRowString));
                case COOLDOWN:
                    this.periodNameTextView.setText(context.getString(R.string.cooldownRowString));
                case HIGH:
                    this.periodNameTextView.setText(context.getString(R.string.highRowString));
                case LOW:
                    this.periodNameTextView.setText(context.getString(R.string.lowRowString));
            }
            this.periodTimeTextView.setText(new StringBuilder().append(period.getTime()).append("s").toString());
        }
    }

    public class IntervalViewHolder extends RecyclerView.ViewHolder {
        public TextView intervalNameTextView;
        public RecyclerView intervalPeriodListView;
        public TextView intervalTimeTextView;
        public TextView intervalRepsTextView;

        public IntervalViewHolder(View itemView) {
            super(itemView);
            this.intervalNameTextView = itemView.findViewById(R.id.intervalNameTextView);
            this.intervalPeriodListView = itemView.findViewById(R.id.intervalPeriodListView);
            this.intervalTimeTextView = itemView.findViewById(R.id.intervalTimeTextView);
            this.intervalRepsTextView = itemView.findViewById(R.id.intervalRepsTextView);
        }

        public void bindData(Interval interval) {
            this.intervalNameTextView.setText(R.string.intervalNameText);
            this.intervalTimeTextView.setText(new StringBuilder().append(interval.getTime()).append("s").toString());
            this.intervalRepsTextView.setText(new StringBuilder().append(interval.getReps()).toString());
            intervalPeriodListView.setAdapter(new IntervalRowViewAdapter(interval.getSets()));
            intervalPeriodListView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            intervalPeriodListView.setLayoutManager(layoutManager);
        }


        private class IntervalRowViewAdapter extends RecyclerView.Adapter {

            public ArrayList<Period> periods;

            public IntervalRowViewAdapter(ArrayList<Period> periods) {
                this.periods = periods;
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View periodView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_period_row, parent,false);
                return new PeriodViewHolder(periodView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                Period period = (Period) periods.get(position);
                PeriodViewHolder periodViewHolder = (PeriodViewHolder) holder;
                periodViewHolder.bindData(period);

            }

            @Override
            public int getItemCount() {
                return periods.size();
            }
        }
    }
}
