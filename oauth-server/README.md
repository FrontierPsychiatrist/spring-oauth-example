OAuth Server
============
This subproject is the OAuth authentication and authorization server.

Starting
--------
Because the server uses JSPs you cannot just start the main class from your IDE. To start the server build it with

    gradle build
    
switch to the oauth-example root folder and run `java -jar oauth-server/build/libs/oauth-server-1.0.jar`.

It is also startable "on-the-fly" by running

    gradle run
    
in the oauth-server directory, but then it won't find the database file (it is expected in the working directory).