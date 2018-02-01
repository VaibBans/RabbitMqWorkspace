# RabbitMqWorkspace

# Procedure for setting up RabbitMq:

1> install otp_win64_20.2.exe :- http://www.erlang.org/download/otp_win64_20.2.exe
2> Set environment varible and system path for Erlang 
             User variable setting : 
             Variable Name: ERLANG_HOME
             Variable Value: C:\Program Files\erl9.2
              
             System variable setting:
             Variable: Path
             Variable Value: C:\Program Files\erl9.2\bin
   (Ensure that these files are there at specified address if not modify these addresses according to file location)

3> install rabbitmq-server-3.7.3.exe 
    a. For windows :- https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.3/rabbitmq-server-3.7.3.exe
    b. For Ubuntu :- https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.3/rabbitmq-server_3.7.3-1_all.deb
4> Set environment varible and system path for Rabbitmq  server.
             User variable setting : 
             Variable Name: RABBITMQ_SERVER
             Variable Value: C:\Program Files\RabbitMQ Server\rabbitmq_server-3.7.2
              
             System variable setting:
             Variable: Path
             Variable Value: %RABBITMQ_SERVER%\sbin
    (Ensure that these files are there at specified address if not modify these addresses according to file location)



# Requirements

Include these jar files while creating the project.

1.RabbitMq Java Client (http://central.maven.org/maven2/com/rabbitmq/amqp-client/4.0.2/amqp-client-4.0.2.jar)
2.SLF4J API (http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.21/slf4j-api-1.7.21.jar)
3.SLF4J Simple (http://central.maven.org/maven2/org/slf4j/slf4j-simple/1.7.22/slf4j-simple-1.7.22.jar)


# Code 

1. RabbitMqBasicQueue
2. RabbitMqConsumerPrioritiesDemo
3. RabbitmqDemoWorkQueues
4. RabbitMqPublishDemoFanout
5. RabbitMqRouting
6. RabbitMqRPCDemo
7. RabbitMqTopicDemo
8. TimeOutDemo

Basic Description for execution:-

There are 2 ways:
1. Through Command Prompt:- use runnable jar of both java files by exporting it as runnable jar
  
  To execute using runnable jar
  - export the java file as runnable jar.
  - extract required libraries into generated jars.
  
  To Run the jar from cmd : java -jar <filename.jar> <arguments>
  
  
2. Through sts :- By clicking on runnning configuration and pass the arguments (messages) and run it.
  
  -> RabbitMqBasicQueue
   Through Command prompt for Publisher :- java -jar Sender.jar "Hello World" 
   Through Command prompt for Consumer  :- java -jar Receiver.jar             (Different Console)
  
  
  -> RabbitMqConsumerPrioritiesDemo
  2 consumers :- ReceiverHighPriority and ReceiverLowPriority have different values of priorities
  Run through STS console 
  Publisher Sends the message as given by user to the high priority consumer
  
  
  -> RabbitmqDemoWorkQueues
  Through Command prompt for Publisher :- java -jar SenderWorkQueues.jar "Hello..." 
  Through Command prompt for Consumer  :- java -jar ReceiverWorkQueues.jar             (Different Console)
  Open  atleast 2 Consumers in different consoles.
  
  -> RabbitMqPublishDemoFanout
  Through Command prompt for Publisher :- java -jar SenderPublish.jar "Hello World" 
  Through Command prompt for Consumer  :- java -jar ReceiverPublish.jar             (Different Console)
  Open  atleast 2 Consumers in different consoles.
  
  -> RabbitMqRouting
  Through Command prompt for Publisher (info) :- java -jar RoutingEmitLog.jar "info" "info message1" 
  Through Command prompt for Publisher (error):- java -jar RoutingEmitLog.jar "info" "info message1" 
  Through Command prompt for Publisher (warn):- java -jar RoutingEmitLog.jar "info" "info message1" 
  
  
  Through Command prompt for Consumer(info)  :- java -jar RoutingReceiverLog.jar "info"             (Different Console)
  Through Command prompt for Consumer(error) :- java -jar RoutingReceiverLog.jar "error"             (Different Console)
  Through Command prompt for Consumer(warn)  :- java -jar RoutingReceiverLog.jar "warn"             (Different Console)
  Through Command prompt for Consumer(for more than one severity) :- java -jar RoutingReceiverLog.jar "info" "error" "warn"   (Different Console)
   
  -> RabbitMqRPCDemo
  To start the server: java -jar RPCServer.jar
  To request a fibonacci number run the client: java -jar RPCClient.jar
  
  
  -> RabbitMqTopicDemo
  To receive all the logs: java -jar ReceiveLogsTopic.jar "#"
  To receive all logs from the facility "kern":java -jar ReceiveLogsTopic.jar "kern.*" 
  To create multiple bindings: java -jar ReceiveLogsTopic.jar "kern.*" "*.critical"
  And to emit a log with a routing key "kern.critical" type: java -jar EmitLogTopic.jar "kern.critical" "A critical kernel     error"
  
  -> TimeOutDemo
  Through Command prompt for Publisher :- java -jar Sender.jar "Hello World" 
  Through Command prompt for Consumer  :- java -jar Receiver.jar             (Different Console)  
  
