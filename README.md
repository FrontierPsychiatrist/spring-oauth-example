Spring Boot OAuth Authorization & Resource server
=================================================
I present to you an example on how to use Spring Boot together with Spring Security OAuth2 to implement an authorization server and
a resource server.

Also included are some example client applications for the resource server.

It's a pretty modern application, using Spring Boot, gradle, thymeleaf and only JavaConfig. In my opinion tt's also a good example of how Java applications
aren't big bloated "enterprisy" things anymore. The current sloccount is 519. 178 of that are just the SQLite dialect for hibernate which
I had to include because it's not in the official packages.

Just tell me how to run it
--------------------------
* Clone the repository
* If you have gradle installed, run

        gradle build
    
    in the main directory. Otherwise run

        ./gradlew build
        
    It will download a local gradle. On Windows use `gradlew.bat`.
* Start the authorization server with

        java -jar oauth-server/build/libs/oauth-server-1.0.jar
        
    And the resource server with
  
        java -jar resource-server/build/libs/resource-server-1.0.jar
        
    The authorization server runs under [http://localhost:8081](http://localhost:8081) and the resource server under
    [http://localhost:8080](http://localhost:8080).
* Additionally you can start a http server in example-clients/html, e.g. like this

        cd example-clients/html
        ruby -run -e httpd . -p 9090
        
    It will be reachable under [http://localhost:9090](http://localhost:9090).
  
Starting from within a IDE
--------------------------
If you want to play around with the java code it's more practicable to start from within your IDE. Just run either `OAuthServerMain` or
`ResourceServerMain`. The working directory to execute in should be the directory in which you cloned into because the database files are
expected there.

What to do when it is running?
------------------------------
The OAuth server is fairly self explanatory. Just open [http://localhost:8081](http://localhost:8081) in a browser. You can login as
* an OAuth admin to administrate clients
* an resource admin or normal user to see what clients you have granted access.
The login credentials should be displayed on the login page.

The URL to get a new access token for a client is

    http://localhost:8081/oauth/authorize?client_id=$client_id&return_type=token&redirect_uri=some_uri
    
If the call to this URL is valid and you are logged in it will redirect to `some_uri` with an access token attached to the location hash. If
you want to call this with cURL you have to set the cookie header to include the session id.

    curl .../oauth/authorize?... -H "Cookie: JSESSIONID=..."
    
which you can find in your browser development console.

The resource server exposes a (very simple) REST API. You can use the example clients to access them or cURL after receiving an access token.

    curl -v localhost:8080/todos -H "Authorization: Bearer $token"
    curl -v localhost:8080/todos/1 -H "Authorization: Bearer $token"
    curl -v -X DELETE localhost:8080/todos/1 -H "Authorization: Bearer $token"
    curl -v -X POST localhost:8080/tokens localhost:8080/todos/1 -H "Authorization: Bearer $token" -d "{ \"message\": \"Do stuff\", \"done\": false }"
    
Why?
----
I wrote this because I had to get into OAuth with Spring and found it actually quite hard to find good examples and documentation. I hope
others can learn from this.

Caveats & Disclaimer
--------------------
I am not a security expert, far from it. I implemented this with my best knowledge on OAuth and Spring Security but I take no guarantee
that it is usable in a productive application.

I used sqlite for the database because most people will have sqlite on their system and can easily look into the database like this.

It goes without saying that in any production environment all HTTP traffic must be HTTPS, otherwise your tokens and client secrets are sniffable.

License
-------
See LICENSE.txt
