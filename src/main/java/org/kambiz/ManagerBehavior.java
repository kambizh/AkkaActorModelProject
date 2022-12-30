package org.kambiz;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class ManagerBehavior extends AbstractBehavior<ManagerBehavior.CommandI> {

    private SortedSet<BigInteger> primes = new TreeSet<>();

    //Manager should be able to receive two different data types in the message. BigInteger and String
    public interface CommandI extends Serializable {
    }

    public static class InstructionCommand implements CommandI {
        public static final long serialVersionUID = 1L;
        private String message;

        public InstructionCommand(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ResultCommand implements CommandI {
        public static final long serialVersionUID = 1L;
        private BigInteger prime;

        public ResultCommand(BigInteger prime) {
            this.prime = prime;
        }

        public  BigInteger getPrime() {
            return prime;
        }
    }

    private ManagerBehavior(ActorContext<CommandI> context) {
        super(context);
    }

    public static Behavior<CommandI> create() {
        return Behaviors.setup(ManagerBehavior::new);
    }

    @Override
    public Receive<CommandI> createReceive() {

        return newReceiveBuilder()
                .onMessage(InstructionCommand.class, command -> {
                    if (command.getMessage().equals("start")) {
                        for (int i = 0; i < 20; i++) {
                            ActorRef<WorkerBehavior.Command> worker = getContext().spawn(WorkerBehavior.create(), "worker" + i);
                            worker.tell(new WorkerBehavior.Command("start", getContext().getSelf()));
                        }
                    }
                    return this;
                })
                .onMessage(ResultCommand.class, command -> {
                    primes.add(command.getPrime());
                    System.out.println(primes.size() + " is added into set.");
                    if (primes.size() == 20) {
                        primes.forEach(System.out::println);
                    }
                    return this;
                })
                .build();

    }
}
