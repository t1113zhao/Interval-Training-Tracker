package com.example.t54zhao.hiittracker.Model;

import com.example.t54zhao.hiittracker.models.Interval;
import com.example.t54zhao.hiittracker.models.Period;
import com.example.t54zhao.hiittracker.models.Workout;
import com.example.t54zhao.hiittracker.models.WorkoutComponent;

import org.junit.Test;

import java.util.ArrayList;

public class TimeSpanTests {

    @Test
    public void testPeriod() {
        //Arrange
        int time = 8;
        Period p = new Period(time);

        //Act

        //Assert
        assert p.getTime() == time;
        assert p.getTimeRemaining() == time;
    }

    @Test
    public void testPeriodDecrementTimeRemaining() {
        //Arrange
        int time = 2;
        Period p = new Period(time);

        //Act
        p.decrementTimeRemaining();

        //Assert
        assert p.getTimeRemaining() == 1;
        assert p.decrementTimeRemaining();
        assert p.getTimeRemaining() == 0;
        assert !p.decrementTimeRemaining();
    }

    private ArrayList<Period> createPeriodsArrayList(int[] ints) {
        ArrayList<Period> periods;
        periods = new ArrayList<Period>();

        for (int i : ints) {
            periods.add(new Period(i));
        }
        return periods;
    }

    @Test
    public void testInterval() {
        Interval.Builder builder = new Interval.Builder(createPeriodsArrayList(new int[]{0, 0, 1, 2, 0}), 2);
        Interval interval = builder.build();

        assert interval.getTime() == 9;
    }

    @Test
    public void testIntervalDecrementTime() {
        Interval.Builder builder = new Interval.Builder(createPeriodsArrayList(new int[]{0, 0, 1, 2, 0}), 2);
        Interval interval = builder.build();

        interval.decrementTimeRemaining();
        interval.decrementTimeRemaining();
        interval.decrementTimeRemaining();
        interval.decrementTimeRemaining();

        assert interval.getTimeRemaining() == 5;
        assert interval.getSets().get(2).getTimeRemaining() == 0;
        assert interval.getSets().get(3).getTimeRemaining() == 2;
        assert interval.getRepsRemaining() == 1;
    }

    @Test
    public void testWorkout() {
        Interval.Builder builder = new Interval.Builder(createPeriodsArrayList(new int[]{0, 0, 1, 2, 0}), 2);
        Interval interval = builder.build();

        ArrayList<WorkoutComponent> components = new ArrayList<WorkoutComponent>();

        components.add(new Period(2));
        components.add(new Period(2));
        components.add(interval);
        components.add(new Period(1));
        Workout.Builder wBuilder = new Workout.Builder(components);
        Workout workout = wBuilder.build();

        assert workout.getComponents().get(2).getTime() == 9;
        assert workout.getComponents().get(3).getTime() == 1;
        assert workout.getTime() == 14;
    }

    @Test
    public void testWorkOutRemoveWorkOutComponent() {
        Interval.Builder builder = new Interval.Builder(createPeriodsArrayList(new int[]{0, 0, 1, 2, 0}), 2);
        Interval interval = builder.build();

        ArrayList<WorkoutComponent> components = new ArrayList<WorkoutComponent>();

        components.add(new Period(2));
        components.add(new Period(2));
        components.add(interval);
        components.add(new Period(1));
        Workout.Builder wBuilder = new Workout.Builder(components);
        Workout workout = wBuilder.build();

        workout.removeWorkOutComponents(0);
        assert workout.getTime() == 12;
        workout.removeWorkOutComponents(0);
        assert workout.getTime() == 10;
        workout.removeWorkOutComponents(1);
        assert workout.getTime() == 9;
        assert !workout.removeWorkOutComponents(-1);
        assert workout.removeWorkOutComponents(0);
        assert !workout.removeWorkOutComponents(0);
    }
    @Test
    public void testWorkoutAppendWorkoutComponents() {
        Interval.Builder builder = new Interval.Builder(createPeriodsArrayList(new int[]{0, 0, 1, 2, 0}), 2);
        Interval interval = builder.build();

        ArrayList<WorkoutComponent> components = new ArrayList<WorkoutComponent>();

        components.add(new Period(2));
        components.add(new Period(2));
        components.add(interval);
        components.add(new Period(1));
        Workout.Builder wBuilder = new Workout.Builder(components);
        Workout workout = wBuilder.build();

        workout.appendWorkoutComponents(new Period(15));

        assert workout.getTime() == 29;
    }

    @Test
    public void testWorkoutInsertComponentAt() {
        Interval.Builder builder = new Interval.Builder(createPeriodsArrayList(new int[]{0, 0, 1, 2, 0}), 2);
        Interval interval = builder.build();

        ArrayList<WorkoutComponent> components = new ArrayList<WorkoutComponent>();

        components.add(new Period(2));
        components.add(new Period(2));
        components.add(interval);
        components.add(new Period(1));
        Workout.Builder wBuilder = new Workout.Builder(components);
        Workout workout = wBuilder.build();

        workout.insertWorkoutComponentAt(new Period(15),2);

        assert workout.getTime() == 29;
        assert workout.getComponentAt(2).getTime()==15;
    }

    @Test
    public void testWorkoutDecrementTime(){
        Interval.Builder builder = new Interval.Builder(createPeriodsArrayList(new int[]{0, 0, 1, 2, 0}), 2);
        Interval interval = builder.build();

        ArrayList<WorkoutComponent> components = new ArrayList<WorkoutComponent>();

        components.add(new Period(2));
        components.add(new Period(2));
        components.add(interval);
        components.add(new Period(1));
        Workout.Builder wBuilder = new Workout.Builder(components);
        Workout workout = wBuilder.build();

        for(int i =0; i<7;i++){
            workout.decrementTimeRemaining();
        }
        assert workout.getComponentAt(2).getTimeRemaining() == 6;

        assert workout.getTime() == 14;
        assert workout.getTimeRemaining() == 7;
    }
}
