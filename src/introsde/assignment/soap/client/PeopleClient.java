package introsde.assignment.soap.client;

import introsde.assignment.soap.model.LifeStatus;
import introsde.assignment.soap.model.Person;
import introsde.assignment.soap.ws.People;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class PeopleClient{
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:6902/ws/people?wsdl");
        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://ws.soap.assignment.introsde/", "PeopleService");
        Service service = Service.create(url, qname);
        
        People people = service.getPort(People.class);
        // read person with id 1
        List<Person> pList = people.getPeople();
        System.out.println("Method #1: Geting result list ==> "+pList.size()+" persons found");
        Person p = people.readPerson(1);
        System.out.println("Method #2: Reading Person id = 1 ==> "+p.getName()+" "+p.getLastname()+" found");
        
       
        // change name of the person with id 1
        String uuid = UUID.randomUUID().toString();
        p.setName("Dinamara"+uuid);
        people.updatePerson(p);
        // read again this person
        p = people.readPerson(1);
        
        System.out.println("Method #3 updading Person with ID 2 ==> "+p.getName());
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Person p2 = new Person();
        p2.setBirthdate(formatter.parse("05/03/1990"));
        p2.setEmail("down@johnes.com");
        p2.setLastname("Santos");
        p2.setName("Bigode");
        
        int idPerson = people.addPerson(p2);
        
        p = people.readPerson(idPerson);
        System.out.println("Method #4 Person added ==> "+idPerson+p.getName());
        
        int i = people.deletePerson(p.getIdPerson());
        if (i==0){
        	System.out.println("Method #5 Person deleted, old ID = "+idPerson );
        }
        
        List<LifeStatus> ls = people.getLifeStatus(1, "weight");
        System.out.println("Method #6 List for person id=1 and measureType = weight returned = "+ls.size()+" results" );
        
        
    }
}
