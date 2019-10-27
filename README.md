# Axon Using Kotlin

Using the [Axon Food Ordering example](https://github.com/AxonIQ/food-ordering-demo) as a starting point, 
this implements an Axon application with idiomatic Kotlin and Gradle.

# To Run

 - Have `docker` up and running
 - Run axon server `docker run -d --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver`
 - Then `./gradlew clean bootRun`
 
