# Assignment 3 INTROSDE 2015

[Educational porpouses only]

This is the development of the third assignment given on the Introduction to Service Design Engineering course at University of Trento during Computer Science MSC.

The basic function is to provide an WSDL service that is able to manage simple Person's model of a Health Insurance company (assuming).

### Important Links 

* [This server on Heroku] [1]
* [The Client example for this server][2]

### Instructions for the Server
 * Git clone the folder to your machine
 * Make sure you have ant installed
 * Go to the folder and in your terminal, type down ant go
 
####The basic WSDL functions are:
 
 * readPersonList()
 * readPerson(Long id)
 * updatePerson(Person p) 
 * createPerson(Person p)
 * deletePersonById(Long id) 
 * readPersonHistory(Long id, String measureType)
 * readMeasureTypes()
 * readPersonMeasure(Long id, String measureType, Long mid)
 * savePersonMeasure(Long id, Measure m) 
 * updatePersonMeasure(Long id, Measure m)
  
### Extra information
Models for using the client are available on the [Client's repository][2]
    
[1]: https://frozen-shore-6890.herokuapp.com/ws/people?wsdl
[2]: https://github.com/ferraricharles/introsde-2015-assignment-3-client
