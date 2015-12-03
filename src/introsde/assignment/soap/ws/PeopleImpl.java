package introsde.assignment.soap.ws;
import java.util.List;

import javax.jws.WebService;

import introsde.assignment.soap.model.LifeStatus;
import introsde.assignment.soap.model.MeasureDefinition;
import introsde.assignment.soap.model.Person;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment.soap.ws.People",
    serviceName="PeopleService")
public class PeopleImpl implements People {

    @Override
    public Person readPerson(int id) {
        System.out.println("---> Reading Person by id = "+id);
        Person p = Person.getPersonById(id);
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getName());
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
        return p;
    }

    @Override
    public List<Person> getPeople() {
        return Person.getAll();
    }

    @Override
    public int addPerson(Person person) {
    	
        Person.savePerson(person);
        
        return person.getIdPerson();
    }

    @Override
    public int updatePerson(Person person) {
    	
    
        Person.updatePerson(person);
        
        return person.getIdPerson();
    }

    @Override
    public int deletePerson(int id) {
        Person p = Person.getPersonById(id);
        if (p!=null) {
            Person.removePerson(p);
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public int updatePersonHP(int id, LifeStatus hp) {
        LifeStatus ls = LifeStatus.getLifeStatusById(hp.getIdMeasure());
        if (ls.getPerson().getIdPerson() == id) {
            LifeStatus.updateLifeStatus(hp);
            return hp.getIdMeasure();
        } else {
            return -1;
        }
    }

	@Override
	public List<LifeStatus> getLifeStatus(int id, String measure) {
		try{
			Person p =  Person.getPersonById(id);
			MeasureDefinition md = MeasureDefinition.getMeasureDefinitionByTitle(measure);
			return Person.getLifeStatusHistory(id,md.getIdMeasureDef());
		}catch(Exception e){
			//System.out.println(e);
			return null;
		}
		
		
	}

	@Override
	public List<MeasureDefinition> readMeasureTypes() {
		
		return MeasureDefinition.getAll();
	}

	@Override
	public LifeStatus readPersonMeasure(int id, String measure, int mid) {
		LifeStatus ls = LifeStatus.getLifeStatusById(mid);
		System.out.println("ID "+ls.getPerson().getIdPerson()+"| "+id);
		System.out.println("MEASURE "+ls.getMeasureDefinition().getMeasureType()+"| "+measure);
		if(ls.getPerson().getIdPerson() == id && ls.getMeasureDefinition().getMeasureType() == measure){
			
			return ls;
		}
		return null;
	}

	@Override
	public LifeStatus readLifeStatus(int id) {
		return LifeStatus.getLifeStatusById(id);
		
	}

	@Override
	public Person readPersonFromLifeStatus(int id) {
		return LifeStatus.getLifeStatusById(id).getPerson();
		
	}

	@Override
	public LifeStatus savePersonMeasure(int id, LifeStatus measure) {
		// TODO Auto-generated method stub
		Person p = Person.getPersonById(id);
		measure.setPerson(p);
		return LifeStatus.saveLifeStatus(measure);
	}

	@Override
	public LifeStatus updatePersonMeasure(int id, LifeStatus measure) {
		Person p = Person.getPersonById(id);
		measure.setPerson(p);
		return LifeStatus.updateLifeStatus(measure);
	}

	@Override
	public MeasureDefinition getSingleMeasureType(int id) {
		return MeasureDefinition.getMeasureDefinitionById(id);
		
	}

	


	
}
