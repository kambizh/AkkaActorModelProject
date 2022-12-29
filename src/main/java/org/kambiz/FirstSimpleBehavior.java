package org.kambiz;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class FirstSimpleBehavior extends AbstractBehavior<String> {

    private FirstSimpleBehavior(ActorContext<String> context) {
        super(context);
    }

    //Whenever we are calling FirstSimpleBehaviour instead we call create helper method to instantiate it.
    public static Behavior<String> create() {
        return Behaviors.setup(FirstSimpleBehavior::new);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                //This is how to digest a message and create a child actor
                .onMessageEquals("create a child", () ->{
                    ActorRef<String> second_actor = getContext().spawn(FirstSimpleBehavior.create(), "secondActor");
                    second_actor.tell("Child is created.");
                    return this;
                })
                .onMessageEquals("Other message", () ->{
                    System.out.println("Other message : " + getContext().getSelf().path());
                    return this;
                })
                .onAnyMessage(message -> {
                    System.out.println("The received message : " + message);
                    return this;
                })
                .build();
    }
}
