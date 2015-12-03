package introsde.assignment.soap.ws;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import introsde.assignment.soap.model.LifeStatus;
import introsde.assignment.soap.model.MeasureDefinition;
import introsde.assignment.soap.model.Person;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") int id);

    @WebMethod(operationName="getPersonList")
    @WebResult(name="people") 
    public List<Person> getPeople();

    @WebMethod(operationName="createPerson")
    @WebResult(name="personId") 
    public int addPerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="updatePerson")
    @WebResult(name="personId") 
    public int updatePerson(@WebParam(name="person") Person person);

    @WebMethod(operationName="deletePerson")
    @WebResult(name="personId") 
    public int deletePerson(@WebParam(name="personId") int id);

    @WebMethod(operationName="updatePersonHealthProfile")
    @WebResult(name="hpId") 
    public int updatePersonHP(@WebParam(name="personId") int id, @WebParam(name="healthProfile") LifeStatus hp);
    
    @WebMethod(operationName="getMeasureType")
    @WebResult(name="measureType") 
    public List<LifeStatus> getLifeStatus(@WebParam(name="personId") int id, @WebParam(name="measure") String measure);
    
    @WebMethod(operationName="getSingleMeasureType")
    @WebResult(name="measureType") 
    public MeasureDefinition getSingleMeasureType(@WebParam(name="measureId") int id);
    
    @WebMethod(operationName="readMeasureTypes")
    @WebResult(name="measureTypes") 
    public List<MeasureDefinition> readMeasureTypes();
    
    @WebMethod(operationName="readLifeStatus")
    @WebResult(name="readLifeStatus") 
    public LifeStatus readLifeStatus(@WebParam(name="personId") int id);
    
    @WebMethod(operationName="readPersonFromLifeStatus")
    @WebResult(name="readPersonFromLifeStatus") 
    public Person readPersonFromLifeStatus(@WebParam(name="idLifeStatus") int id);
    
    @WebMethod(operationName="readPersonMeasure")
    @WebResult(name="measureTypes") 
    public LifeStatus readPersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") String measure,@WebParam(name="measureId") int mid);
    
    @WebMethod(operationName="savePersonMeasure")
    @WebResult(name="measureTypes") 
    public LifeStatus savePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") LifeStatus measure);
    
    @WebMethod(operationName="updatePersonMeasure")
    @WebResult(name="measureTypes") 
    public LifeStatus updatePersonMeasure(@WebParam(name="personId") int id, @WebParam(name="measure") LifeStatus measure);
    
    
}