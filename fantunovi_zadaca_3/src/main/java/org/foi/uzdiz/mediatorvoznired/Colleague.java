package org.foi.uzdiz.mediatorvoznired;

public abstract class Colleague {
	protected VozniRedMediator mediator;

    public Colleague(VozniRedMediator mediator) {
        this.mediator = mediator;
    }

    public abstract void posaljiPoruku(String message);
}
