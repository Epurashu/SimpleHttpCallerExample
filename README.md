<h2>About</h2>
<h3>Work in progress</h3>
Simple Http client that uses Alfresco V1 Rest API to generate Audit logs.

<h2>Requirements</h2>
* Openjdk 11.0.1 and above

<h2>Build Standalone Jar</h2>
```mvn clean compile assembly:single```

<h2>How to run</h2>
java -jar SimpleHttpRequestSender.jar PARAM=VALUE

Parameter lists:
```
 threads=5
 folders=100
 nodes=10
 updates=15
```

<h2>Notice</h2>
The parameter *threads* will increase the number of parallel execution of creating folder Nodes.

Number of folder Nodes each tread will create is set by the parameter *folders*.