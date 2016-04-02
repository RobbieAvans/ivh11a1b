package edu.avans.hartigehap.domain.handler;

public abstract class Handler<T, R> {
    protected Handler<T, R> successor;

    public void setSuccessor(Handler<T, R> successor) {
        this.successor = successor;
    }

    public abstract R handleRequest(T request);
}
