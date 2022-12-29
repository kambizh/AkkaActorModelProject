package org.kambiz;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class ManagerBehavior extends AbstractBehavior<String> {

    private ManagerBehavior(ActorContext<String> context) {
        super(context);
    }

    public static Behavior<String> create() {
        return Behaviors.setup(ManagerBehavior::new);
    }

    @Override
    public Receive<String> createReceive() {

        return newReceiveBuilder()
                .onMessageEquals("start", () -> {
                    for (int i = 0; i < 20; i++) {
                        ActorRef<String> worker = getContext().spawn(WorkerBehavior.create(), "worker"+i);
                        worker.tell("start");
                    }
                    return this;
                })
                .build();

    }
}
