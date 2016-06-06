# methodmock.swoklabs.com
Method Mock framework. 

Developed by Steve Widinghoff and Örjan Karlsson


## Example
Add dependencies to the methodmock and the model. 
Add the annotation to the methods that you want to mock, add a unique id  in the annotation. 

The mockmethod framework relies on the ability of aspectj-weaver. Thus we need to load that java agent into the runtime. 
There are numerous ways to do this and we have created a support class that loads the java agent during runtime. 

# Loading java agent
1. Extend LoadJavaAgent in the unit-test class. This will load the java agent. 
2. Use @BeforeClass initialization method. There do a new LoadJavaAgent() call. 
3. 


##  How it works
The framework uses Aspect Oriented Programming to create a type of proxy class that intercepts all the calls to the original object. 

To read more about AspectJ, that's being used in this framework, look at the article series that i have produced earlier. 

http://www.jayway.com/2015/09/03/aspectj-and-aop-the-black-magic-of-programming/

There are also a smaller AspectJ project connected to that tutorial. 

https://github.com/Nosfert/AspectJ-Tutorial-jayway

## Setup in your code
Add the annotation @MockInTest(methodId = "<uniqueId>") on the method that you want to mock. 
Replace <uniqueId> with the ID that you want the method to have, the ID will be used in the unit-test. 
The ID lets the framework identify the specific method and connect it with the mocked object that should be returned. 

## Unit-test
TBD - Work in progress on how the framework shall work in the unit-test phase. 
