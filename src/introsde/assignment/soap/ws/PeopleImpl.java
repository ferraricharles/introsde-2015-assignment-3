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
    public Person readPerson(long id) {
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
    public long addPerson(Person person) {
    	
        Person.savePerson(person);
        
        return person.getIdPerson();
    }

    @Override
    public long updatePerson(Person person) {
    	
    
        Person.updatePerson(person);
        
        return person.getIdPerson();
    }

    @Override
    public long deletePerson(long id) {
        Person p = Person.getPersonById(id);
        if (p!=null) {
            Person.removePerson(p);
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public int updatePersonHP(long id, LifeStatus hp) {
        LifeStatus ls = LifeStatus.getLifeStatusById(hp.getIdMeasure());
        if (ls.getPerson().getIdPerson() == id) {
            LifeStatus.updateLifeStatus(hp);
            return hp.getIdMeasure();
        } else {
            return -1;
        }
    }

	@Override
	public List<LifeStatus> getLifeStatus(long id, String measure) {
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
	public LifeStatus readPersonMeasure(long id, String measure, int mid) {
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
	public LifeStatus savePersonMeasure(long id, LifeStatus measure) {
		// TODO Auto-generated method stub
		Person p = Person.getPersonById(id);
		measure.setPerson(p);
		return LifeStatus.saveLifeStatus(measure);
	}

	@Override
	public LifeStatus updatePersonMeasure(long id, LifeStatus measure) {
		Person p = Person.getPersonById(id);
		measure.setPerson(p);
		return LifeStatus.updateLifeStatus(measure);
	}

	@Override
	public MeasureDefinition getSingleMeasureType(int id) {
		return MeasureDefinition.getMeasureDefinitionById(id);
		
	}

	@Override
	public long deletePersonById(long id) {
		try{
			Person p = Person.getPersonById(id);
			Person.removePerson(p);
			return 1;
		}catch(Exception e){
			return 0;
		}
		
		
	}

	


	
}
