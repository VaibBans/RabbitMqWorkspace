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
