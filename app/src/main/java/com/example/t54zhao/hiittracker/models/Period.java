package com.example.t54zhao.hiittracker.models;

public class Period extends WorkoutComponent {

    public Period(int time, ComponentType componentType){
        this.time =time;
        this.timeRemaining = time;
        this.componentType = componentType;
    }

    public Period(int time){
        this.time =time;
        this.timeRemaining = time;
        this.componentType = ComponentType.HIGH;
    }

    @Override
    public void setTime(int newTime){
        this.time = newTime;
        this.timeRemaining = time;
    }

    @Override
    public void resetTimeRemaining(){
        this.timeRemaining = time;
    }

    @Override
    public boolean decrementTimeRemaining(){
        if(this.timeRemaining >0){
            this.timeRemaining --;
            return true;
        }
        return false;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public int getTimeRemaining() {
        return timeRemaining;
    }
}

