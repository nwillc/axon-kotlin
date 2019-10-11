# Axon Spike Using Gradle, Jib and Skaffold

 - Have `docker` up and running
 - Have `minikube` set up running
 - Install skaffold: `brew install skaffold`
 - Set up an image repository
   - Install cred helper: `brew install docker-credential-helper`
   - docker login
 - Run axon server on LOCAL.IP.ADDRESS and expose ports: `docker run -d --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver`
 - set `axon.axonserver.servers=LOCAL.IP.ADDRESS` in application.properties
 - skaffold dev --port-forward
 
The above will start an axon server in a docker image, run your app in minikube, expose it's port back to the local machine
allowing you to target it as http://localhost:8080.
 
