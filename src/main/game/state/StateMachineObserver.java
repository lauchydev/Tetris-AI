package main.game.state;

public interface StateMachineObserver {
    void onStateChanged(State state, Event triggerEvent);
}
