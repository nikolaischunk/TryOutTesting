In this exercise the goal is to create DTOs, implement those into our controllers and map them to regular business objects.
The focus of this exercise is to understand how to implement DTOs and their purpose.
As a quick recap; the main purpose of DTOs is to send a representation of the objects in our database. 
This excapsulation process is used to hide data and to make it easier for the other team to work with our API, as now they know exactly what 
response entity they'll receive.
A DTO doesn't need to possess every single attribute from the actual business object, for an example look at the userDTO in the project.
As usual, in this project there are three objects who have created, implemented and mapped their DTO: Role, authority and user.

Short description of the exercise:
1. Add the following dependencies to your build.gradle and then refresh the gradle imports:

	implementation "org.mapstruct:mapstruct:1.3.0.Final"
	annotationProcessor "org.mapstruct:mapstruct-processor:1.3.0.Final"
	testAnnotationProcessor "org.mapstruct:mapstruct-processor:1.3.0.Final"

2. Create a DTO for each business object with attributes which represent the actual values of mentioned object.
3. Create a mapper interface for each business object, use the existing mappers as inspiration
4. Remove the businessobjects from the controller and instead use the DTOs and map them accordingly
   This includes the response types and the request types

