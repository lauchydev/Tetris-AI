package main.game.state;

public class StateMachine {
    private volatile State state;

    private StateMachineObserver observer = null;

    public StateMachine() { state = State.INIT; }

    public State getState() { return state; }

    public void setObserver(StateMachineObserver observer) { this.observer = observer; }

    public synchronized void handleEvent(Event event) {
        if (state != State.FINISHED && (event == Event.LOSE || event == Event.STOP)) {
            transitionTo(State.FINISHED, event);
            return;
        }

        switch (state) {
            case INIT:
                if (event == Event.START) {
                    transitionTo(State.RUNNING, event);
                }
                break;

            case RUNNING:
                if (event == Event.PAUSE) {
                    transitionTo(State.PAUSED, event);
                }
                break;

            case PAUSED:
                if (event == Event.UNPAUSE) {
                    transitionTo(State.RUNNING, event);
                }
                break;
        }
    }

    private synchronized void transitionTo(State newState, Event triggerEvent) {
        state = newState;
        if (observer != null) {
            observer.onStateChanged(newState, triggerEvent);
        }
    }
}
