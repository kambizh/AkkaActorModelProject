# AkkaActorModelProject

Sample Akka Actor Model project to show the concurrent process in Actor Model. 

Actor Manager delegate the task of creating 20 probable prime into 20 Actor Worker to generate all 20 concurrently. Actor Manager receive the result and add into a TreeSet. After all 20 results are collected the Set will be print in Console. 
