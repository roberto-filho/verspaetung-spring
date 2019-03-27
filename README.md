# verspaetung-spring
Basic spring application.
### Installation
This app requires Java 8 to run.
Uses the gradle wrapper to run tasks.
```sh
$ cd verspaetung-spring
$ ./gradlew bootRun
```
Then type on your browser: `http://localhost:8081/vehicles/find?time=10:10:00&x=2&y=12`
### Dockerfile
This repository also comes with a Dockerfile to dockerize the application:
```sh
$ docker build -t robertofilho/verspaetung:alpha .
```
