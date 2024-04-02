package com.example.zboruri.Utils.Observer;

import com.example.zboruri.Utils.Events.Event;

import java.util.ArrayList;

public interface Observable <E extends Event>{
    ArrayList<Observer> observers = new ArrayList<>();
    default void addObserver(Observer<E> observer){
        observers.add(observer);
    }
    default void removeObserver(Observer<E> observer){
        observers.remove(observer);
    }
    default void notifyAllObservers(E eventUpdate){
        observers.forEach(x->x.update(eventUpdate));
    }
}
