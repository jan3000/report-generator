package de.zalando.backlog.reportgenerator.streamer.domain;

public class Event<T> {

    T event;


    public T getEvent() {
        return event;
    }

    public void setEvent(final T event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event=" + event +
                '}';
    }
}
