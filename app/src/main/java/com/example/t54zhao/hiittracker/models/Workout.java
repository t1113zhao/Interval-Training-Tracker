package com.example.t54zhao.hiittracker.models;

import java.util.ArrayList;

public class Workout extends TimeSpan {

    private ArrayList<WorkoutComponent> components;

    private int currentComponentIndex;

    private String name;

    public Workout(){
        if (components == null){
            components = new ArrayList<>();
        }
        currentComponentIndex =0;
    };

    private Workout ( ArrayList<WorkoutComponent> components){
        this.components = components;
        currentComponentIndex =0;
        calculateTime();
    }

    private void calculateTime(){
        for(WorkoutComponent w: components){
            time += w.getTime();
        }
        timeRemaining = time;
    }

    /**
     * Remove the workoutComponent at given index
     * @param index
     * @return
     */
    public boolean removeWorkOutComponents(int index){
        if(index < components.size() && index >=0){
            time -= components.get(index).getTime();
            components.remove(index);
            timeRemaining = time;
            return true;
        }
        return false;
    }

    /**
     * Add a workoutComponent to the end of the list
     * @param workoutComponent
     * @return
     */
    public boolean appendWorkoutComponents(WorkoutComponent workoutComponent){
        components.add(workoutComponent);
        time += workoutComponent.getTime();
        timeRemaining =time;
        return true;
    }

    /**
     * Insert a workOutComponent at a given index
     * @param workoutComponent
     * @param index
     * @return
     */
    public boolean insertWorkoutComponentAt(WorkoutComponent workoutComponent, int index){
        components.add(index,workoutComponent);
        time += workoutComponent.getTime();
        timeRemaining =time;
        return true;
    }

    /**
     * Move a workoutComponent from an index to another
     * Return false if not possible
     * @param from
     * @param to
     * @return
     */
    //TODO test this
    public boolean moveWorkoutComponent(int from, int to){
        if(from>=components.size()|| to < 0){
            return false;
        }
        WorkoutComponent component = components.get(from);
        removeWorkOutComponents(from);
        insertWorkoutComponentAt(component,to);
        return true;
    }

    /**
     * Get the component at a given index
     * @param index
     * @return
     */
    public WorkoutComponent getComponentAt(int index){
        if(index < components.size() && index >=0){
            return components.get(index);
        }
        return null;
    }


    @Override
    public int getTime() {
        return time;
    }

    @Override
    public void setTime(int newTime){
        time = newTime;
        timeRemaining = time;
    }

    @Override
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Decrement Time
     * @return
     * True if operation succeeded
     */
    @Override
    public boolean decrementTimeRemaining() {
        if(timeRemaining >0){
                while(currentComponentIndex<components.size()){
                    if(components.get(currentComponentIndex).timeRemaining >0){
                        components.get(currentComponentIndex).decrementTimeRemaining();
                        timeRemaining --;
                        return true;
                    }
                    currentComponentIndex++;
                }
            }
        return false;
    }

    @Override
    public void resetTimeRemaining() {
        for(WorkoutComponent component :components){
            component.resetTimeRemaining();
        }
        timeRemaining =time;
        currentComponentIndex =0;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName){
        this.name = mName;
    }

    public ArrayList<WorkoutComponent> getComponents() {
        return components;
    }

    public static class Builder{

        private ArrayList<WorkoutComponent> bComponents;

        public Builder(ArrayList<WorkoutComponent> components){
            bComponents = components;
        }

        public Workout build(){
            Workout newWorkout = new Workout(bComponents);
            newWorkout.setName("Workout");
            return newWorkout;
        }
    }
}
