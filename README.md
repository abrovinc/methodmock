# methodmock.swoklabs.com
Method Mock framework. 

Developed by Steve Widinghoff and Örjan Karlsson


## Example
# Dependencies
There are two projects that you need to depend on. 
The model project needs to be added as a normal dependency. 
The methodmock project only needs to be available during the test-phase. 

# Annotate
In the model project there is a single interface, an annotation class. 
This annotation is the markup for all the methods that you want to mock during the test-phase. 
The annotation require you to fill in an id, the id should be unique across your project.   

# Loading java agent
The framework relies on loading an java-agent that's called aspectjweaver.
There are a few different ways to load the java-agent and here are the most common
1. Extend LoadJavaAgent in the unit-test class. This will load the java agent. 
2. Use @BeforeClass initialization method. There do a new LoadJavaAgent() call. 
3. Load java-agent through parameter in run-config
4. Load java-agent by configuring it to load via maven/grade etc. 

# Using mockmethod
Once you have added your annotations to the methods you want to mock it's time to actually mock them in unit-tests

Simple mock
mockMethod("<insert method id>").returns(<insert return object here>); 

Advance mock
mockMethod("<insert method id>").calls(<insert Use Enum value>).returns(<insert return object here>); 
the calls method defines if the object in returns should only be returned once once or reused several times. 
The default for calls is that the return value will be removed once its been used. 

# Clearing methodmock
If you have multiple tests and you need to clear the results that's been used you need to manually clear it. 
You can do this by calling 
```java
clearMock();
```

##  How it works
The framework uses Aspect Oriented Programming to create a type of proxy class that intercepts all the calls to the original object. 

To read more about AspectJ, that's being used in this framework, look at the article series that i have produced earlier. 

http://www.jayway.com/2015/09/03/aspectj-and-aop-the-black-magic-of-programming/

There are also a smaller AspectJ project connected to that tutorial. 

https://github.com/Nosfert/AspectJ-Tutorial-jayway

## Setup in your code
Add the annotation 
```java
@MockInTest(methodId = "<uniqueId>") 
```
on the method that you want to mock. 
Replace <uniqueId> with the ID that you want the method to have, the ID will be used in the unit-test. 
The ID lets the framework identify the specific method and connect it with the mocked object that should be returned. 

## Unit-test
TBD - Work in progress on how the framework shall work in the unit-test phase. 

## Documentation

## Support and discussion

## Links

