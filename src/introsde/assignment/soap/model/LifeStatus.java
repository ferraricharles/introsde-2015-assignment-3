package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;
import introsde.assignment.soap.model.MeasureDefinition;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElement;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;


/**
 * The persistent class for the "LifeStatus" database table.
 * 
 */
@Entity
@Table(name = "LifeStatus")
@NamedQuery(name = "LifeStatus.findAll", query = "SELECT l FROM LifeStatus l")
@XmlRootElement(name="Measure")
public class LifeStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_lifestatus")
	@TableGenerator(name="sqlite_lifestatus", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="LifeStatus")
	@Column(name = "idMeasure")
	private int idMeasure;

	@Column(name = "value")
	private String value;

	@Column(name = "createdDate")
	private String createdDate;
	
	@OneToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	@XmlElement(name = "MeasureType")
	public String getMeasureTypeDescription(){
		MeasureDefinition md = this.getMeasureDefinition();		
		return md.getMeasureName();
		
	}

	public void setMeasureTypeDescription(String param){
		//System.out.println("\n\n\n\n\n\n "+param);
		this.setMeasureDefinition (MeasureDefinition.getMeasureDefinitionByTitle(param));
		
	}

	public LifeStatus() {

	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	@XmlElement(name = "mid")
	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}


    @XmlTransient
	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}

	// we make this transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {
		
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		//System.out.println("Saving person "+person.getName());
	}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static LifeStatus getLifeStatusById(int lifestatusId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		LifeStatus p = em.find(LifeStatus.class, lifestatusId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<LifeStatus> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<LifeStatus> list = em.createNamedQuery("LifeStatus.findAll", LifeStatus.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static LifeStatus saveLifeStatus(LifeStatus p) {

		//System.out.println("\n\n\n\n\n\n\n AQUI BITCH");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		//System.out.println("\n\n\n\n\n\n\n AQUI BITCH2");
		Date today = Calendar.getInstance().getTime(); 
		
		String reportDate = df.format(today);
		
		
		p.setCreatedDate(reportDate);

		
		

		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static LifeStatus updateLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
