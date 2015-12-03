package introsde.assignment.soap.client;

import introsde.assignment.soap.model.LifeStatus;
import introsde.assignment.soap.model.MeasureDefinition;
import introsde.assignment.soap.model.Person;
import introsde.assignment.soap.ws.People;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        p = people.readPerson(2);
        
        System.out.println("Method #3 updading Person with ID 2 ==> "+p.getName());
        
        MeasureDefinition md = people.getSingleMeasureType(1);
        LifeStatus pLs = new LifeStatus();
        pLs.setMeasureDefinition(md);
        pLs.setValue(120+"");
        List<LifeStatus> lsList = new ArrayList<LifeStatus>();
        
        lsList.add(pLs);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Person p2 = new Person();
        p2.setBirthdate(formatter.parse("05/03/1990"));
        p2.setEmail("down@johnes.com");
        p2.setLastname("Santos");
        p2.setName("Bigode");
        p2.setLifeStatus(lsList);
        
        int idPerson = people.addPerson(p2);
        
        p = people.readPerson(idPerson);
        System.out.println("Method #4 Person added ==> "+idPerson+p.getName());
        
        
        int i = people.deletePerson(p.getIdPerson());
        if (i==0){
        	System.out.println("Method #5 Person deleted, old ID = "+idPerson );
        }
        
        
        p = people.readPerson(2);
        String measureType = "weight";
        List<LifeStatus> ls = people.getLifeStatus(p.getIdPerson(), measureType);
        
        System.out.println("Method #6 List for person id="+p.getIdPerson()+" and measureType = "+measureType+"  returned = "+ls.size()+" results" );
        
        List <MeasureDefinition> mds = people.readMeasureTypes();
        System.out.println("Method #7 found "+mds.size()+" different measure definitions");
        
        try{
        	 LifeStatus ls1 = ls.get(0);
        	 ls1 = people.readLifeStatus(ls1.getIdMeasure());
        	 
     		 
             LifeStatus ls2 = people.readPersonMeasure(p.getIdPerson(), measureType, ls1.getIdMeasure());
             System.out.println("Method #8 Received from person id="+p.getIdPerson()+" "
             		+ "Measure Type = "+measureType +""
             				+ " Measure Id = "+ls1.getIdMeasure());
        }catch(Exception e){
        	System.out.println(e);
        	
        }
        
        LifeStatus newLs = new LifeStatus();
        
        newLs.setMeasureDefinition(mds.get(0));
        newLs.setValue(120+"");
        newLs = people.savePersonMeasure(p.getIdPerson(), newLs);
        
        System.out.println("Method #9 new Measure stored for "
        		+ "person "+p.getIdPerson()+" "
        				+ "measure = "+mds.get(0).getMeasureName()+" "
        						+ "value = "+newLs.getValue()  );
       
        
        newLs.setValue(150+"");
        
        newLs = people.updatePersonMeasure(p.getIdPerson(), newLs);
        System.out.println("Method #10 updating Measure for "
        		+ "person "+p.getIdPerson()+" "
        				+ "measure = "+mds.get(0).getMeasureName()+" "
        						+ "value = "+newLs.getValue()  );
        		
        
    }
}
