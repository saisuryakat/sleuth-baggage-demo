If you pass the header user_name Sleuth will automatically propagate it to the backend!
```curl -s localhost:8081 -H'X-Correlation-Id: 202003041030123SY6D3'```

Or if you don't pass any header, interceptor will generate a new correlation id at Front end controller.

try ```curl -s localhost:8081```

and this header name is whitelisted in slf4j's MDC, so the value gets printed along with trace and span ids in logs

Config property keys are changed in spring-cloud-sleuth 3.0  
Refer this [migration guide](https://github.com/spring-cloud/spring-cloud-sleuth/wiki/Spring-Cloud-Sleuth-3.0-Migration-Guide)