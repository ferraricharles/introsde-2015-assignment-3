package introsde.assignment.soap.client;


import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import introsde.assignment.soap.client.PeopleService;
import introsde.assignment.soap.client.People;
import introsde.assignment.soap.model.Person;


public class PeopleClientForget {


	
    public static void main(String[] args) throws Exception {
	    //PeopleService service = new PeopleService();
	    //People people = service.getPeopleImplPort();
		URL url = new URL("http://localhost:6902/ws/people");
	    //1st argument service URI, refer to wsdl document above
	    //2nd argument is service name, refer to wsdl document above
	    QName qname = new QName("http://ws.soap.assignment.introsde/", "PeopleService");
	    Service service = Service.create(url, qname);
        People people = service.getPort(People.class);
        Person p = people.readPerson(2);
        
        
        List<Person> pList = people.getPersonList();
        System.out.println("Method #1: Geting result list ==> "+pList.size()+" persons found");
        System.out.println("Method #2: Reading Person id = 2 ==> "+p.getName()+" "+p.getLastname()+" found");
        
        //People people = new People();
        System.out.println(p.getIdPerson());
        String uuid = UUID.randomUUID().toString();
        p.setName("Veabrechuk");
        people.updatePerson(p);
        p = people.readPerson(2);
        System.out.println("Method #3: Updading Person id = 2 ==> "+ p.getName());
        Person p2 = new Person();
        
        
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar now = 
                datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        //p2.setBirthdate(now);
        p2.setName("Alfredo");
        p2.setLastname("Balduino");
        
        System.out.println(people.createPerson(p2));
        
        
    }
	
}
