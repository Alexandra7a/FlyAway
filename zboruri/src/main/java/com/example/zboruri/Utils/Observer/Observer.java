package com.example.zboruri.Utils.Observer;

import com.example.zboruri.Utils.Events.Event;

public interface Observer <E extends Event> {
    void update(E eventUpdate);
}
