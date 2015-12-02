package introsde.assignment.soap.model;

import introsde.assignment.soap.dao.LifeCoachDao;
import introsde.assignment.soap.model.HealthMeasureHistory;
import introsde.assignment.soap.model.MeasureDefinition;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlElementRef;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Person") // to whole table must be persisted 
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")
    @Column(name="idPerson")
    private int idPerson;
    @Column(name="lastname")
    private String lastname;

    @Column(name="name")
    private String firstname;
    @Column(name="username")
    private String username;
    @Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @Column(name="birthdate")
    private Date birthdate; 
    @Column(name="email")
    private String email;
    
    
    // mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation
    
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<LifeStatus> lifeStatus;
    
    
    // add below all the getters and setters of all the private attributes
   

    // getters
    public int getIdPerson(){
        return idPerson;
    }

    public String getLastname(){
        return lastname;
    }
    @XmlElement(name = "firstname")
    public String getName(){
        return firstname;
    }
    @XmlTransient
    public String getUsername(){
        return username;
    }
    public Date getBirthdate(){
        return birthdate;
    }
    @XmlTransient
    public String getEmail(){
        return email;
    }



    @XmlElementWrapper(name = "healthProfile")
    public List<LifeStatus> getLifeStatus() {
        return getRecentLifeStatus(this.getIdPerson());

        /*
            if(!recentLifeStatus){
                setRecentLifeStatus(false);
                

            }
                

            return lifeStatus;
            */

    }




    public void setLifeStatus (List<LifeStatus> lifeStatus){
        System.out.println("\n\n\n\n\n\n\n LIFE STATUS SETTED");
        this.lifeStatus = lifeStatus;
    }


    
    // setters
    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setName(String name){
        this.firstname = name;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setBirthdate(Date birthdate){
        this.birthdate = birthdate;
    }
    public void setEmail(String email){
        this.email = email;
    }

    /* CHARLES LINDO*/
    public static List<LifeStatus> getLifeStatusHistory(int personid) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        System.out.println("Looking measurements for"+personid);
        List<LifeStatus> list = new ArrayList<LifeStatus>();
        try{
             list = em.createQuery(        
            "SELECT lf FROM LifeStatus lf WHERE hm.person.idPerson = :pID")
            .setParameter("pID", personid)
            .getResultList();
            

        }catch(Exception e){
            System.out.println("Error"+e);
            
        }
        //System.out.println("Measurements found"+list.size());
        LifeCoachDao.instance.closeConnections(em);
        return list;
        
    }

    public static List<LifeStatus> getRecentLifeStatus(int personid) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        //System.out.println("Looking measurements for"+personid);
        List<LifeStatus> list = new ArrayList<LifeStatus>();
        try{
             list = em.createQuery(        
            "SELECT lf FROM LifeStatus lf WHERE lf.person.idPerson = :pID GROUP BY (lf.measureDefinition)")
            .setParameter("pID", personid)
            .getResultList();
            

        }catch(Exception e){
            System.out.println("Error"+e);
            
        }
       // System.out.println("Measurements found"+list.size());
        LifeCoachDao.instance.closeConnections(em);
        return list;
        
    }

    public static void addPersonToLifeStatus(Person p){
       
        
        try{
            List<LifeStatus> lifeStatus = p.getLifeStatus();
            
            for(int i=0; i< p.lifeStatus.size(); i++){

                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                
                Date today = Calendar.getInstance().getTime(); 
                
                String reportDate = df.format(today);
                
                
                lifeStatus.get(i).setCreatedDate(reportDate);
                //System.out.println("\n\n\n\n\n\n\n\n ADDING PERSON FROM LOOP");
                lifeStatus.get(i).setPerson(p);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
    }

    public static List<LifeStatus> getLifeStatusHistory(int personid, int measureid) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        //System.out.println("\n\n\n\n\n\nLooking measurement "+measureid+" for "+personid);
        List<LifeStatus> list = new ArrayList<LifeStatus>();
        

        try{
             list = em.createQuery(        
            "SELECT lf FROM LifeStatus lf WHERE lf.person.idPerson = :pID AND lf.measureDefinition.idMeasureDef = :mID")
            .setParameter("pID", personid)
            .setParameter("mID", measureid)
            .getResultList();

            /*    
            list = em.createQuery(        
            "SELECT lf FROM LifeStatus lf WHERE lf.measureDefinition.idMeasureDef = :mID")
            .setParameter("mID", measureid)
            .getResultList();
            */

        }catch(Exception e){
            System.out.println("Error"+e);
            
        }
        
        LifeCoachDao.instance.closeConnections(em);
        return list;
        
    }
    
    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    

    public static Person savePerson(Person p) {
        Person.addPersonToLifeStatus(p);
        
        
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        //System.out.println("\n\n new person created "+p.getIdPerson());
        

        return p;
    } 

    public static Person updatePerson(Person newPerson) {
    	
        EntityManager em = LifeCoachDao.instance.createEntityManager(); 
       
        EntityTransaction tx = em.getTransaction();
        Person p = getPersonById(newPerson.getIdPerson());
        
        if(newPerson.getName()!=null)
            p.setName(newPerson.getName());

        if(newPerson.getBirthdate() != null)
            p.setBirthdate(newPerson.getBirthdate());

        if(newPerson.getLastname() != null)
            p.setLastname(newPerson.getLastname());
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        //System.out.println("\n\n\n\n\n Trying to DELETE");
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        try{
            em.remove(p);    
        }catch(Error e){
            System.out.println(e);
        }
        
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
}