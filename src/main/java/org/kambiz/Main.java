package org.kambiz;

import akka.actor.typed.ActorSystem;

public class Main {
    public static void main(String[] args) {
        ActorSystem<ManagerBehavior.CommandI> bigPrimes = ActorSystem.create(ManagerBehavior.create(), "BigPrime");
        bigPrimes.tell(new ManagerBehavior.InstructionCommand("start"));
    }
}