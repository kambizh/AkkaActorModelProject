package org.kambiz;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class WorkerBehavior extends AbstractBehavior<WorkerBehavior.Command> {

    public static class Command implements Serializable {

        private static final Long serialVersionUID = 1L;
        private String message;
        private ActorRef<ManagerBehavior.CommandI> sender;

        public Command(String message, ActorRef<ManagerBehavior.CommandI> sender) {
            this.message = message;
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public ActorRef<ManagerBehavior.CommandI> getSender() {
            return sender;
        }
    }


    private WorkerBehavior(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(WorkerBehavior::new);
    }

    private BigInteger prime;

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onAnyMessage(command -> {
                    if (prime == null) {
                        if (command.getMessage().equals("start")) {
                            BigInteger bigInteger = new BigInteger(2000, new Random());
                            prime = bigInteger.nextProbablePrime();
                        }
                        command.getSender().tell(new ManagerBehavior.ResultCommand(prime));
                    }
                    return this;
                })
                .build();
    }
}
