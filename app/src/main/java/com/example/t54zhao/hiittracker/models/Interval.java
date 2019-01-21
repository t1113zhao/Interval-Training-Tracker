package com.example.t54zhao.hiittracker.models;

import java.util.ArrayList;

public class Interval extends WorkoutComponent {

    private ArrayList<Period> sets;
    private int currentSetsIndex;
    private int reps;
    private int repsRemaining;

    public Interval(ArrayList<Period> sets,int reps){
        this.sets = sets;
        this.reps = reps;
        this.repsRemaining = reps;
        currentSetsIndex =0;
        calculateTime();
        this.componentType = ComponentType.INTERVAL;
    }

    private void calculateTime(){
        for(Period p: sets){
            time += p.getTime();
        }
        time += time*reps;
        timeRemaining =time;
    }

    @Override
    public void resetTimeRemaining() {
        resetSets();
        timeRemaining = time;
    }

    public boolean removeFromSetAt(int index){
        if(index < sets.size() && index >0){
            time -= sets.get(index).getTime();
            sets.remove(index);
            timeRemaining = time;
            return true;
        }
        return false;
    }

    public boolean appendSets(Period workoutComponent){
        sets.add(workoutComponent);
        time += workoutComponent.getTime();
        timeRemaining =time;
        return true;
    }

    public boolean insertPeriodAt(Period workoutComponent, int index){
        sets.add(index,workoutComponent);
        time += workoutComponent.getTime();
        timeRemaining =time;
        return true;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public void setTime(int newTime){
        return;
    }

    @Override
    public int getTimeRemaining() {
        return timeRemaining;
    }
    private void resetSets(){
        for(Period p:sets){
            p.resetTimeRemaining();
        }
    }

    @Override
    public boolean decrementTimeRemaining() {
        if(timeRemaining >0){
            while(repsRemaining>=0){
                while(currentSetsIndex<sets.size()){
                    if(sets.get(currentSetsIndex).timeRemaining >0){
                        sets.get(currentSetsIndex).decrementTimeRemaining();
                        timeRemaining --;
                        return true;
                    }
                    currentSetsIndex++;
                }
                currentSetsIndex =0;
                resetSets();
                repsRemaining--;
            }
        }
        return false;
    }

    public ArrayList<Period> getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getRepsRemaining() {
        return repsRemaining;
    }

    public static class Builder{
        ArrayList<Period> bSets;
        int reps;

        public Builder( ArrayList<Period> sets,int reps){
            this.bSets=sets;
            this.reps = reps;
        }

        public Interval build(){
            return new Interval(bSets,reps);
        }
    }
}
