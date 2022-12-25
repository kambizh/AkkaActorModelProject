package org.kambiz;

import akka.actor.typed.ActorSystem;

public class Main {
    public static void main(String[] args) {

        ActorSystem<String> actorSystem = ActorSystem.create(FirstSimpleBehavior.create(), "InitialActorSystem");
        actorSystem.tell("Hello there!");
        actorSystem.tell("Second message is here.");
    }
}