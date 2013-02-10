/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project_hibernate;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author sean
 */

@Entity
@Table(name="congressman")
public class Congressman {
    
    private int id;
    private String firstName;
    private String lastName;
    private String party;
    private State state;
    private List<Committee> committees = new ArrayList<Committee>();
    
    private District district;

    
    public Congressman() {}
    
    public Congressman(String firstName, String lastName, String party) {
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.party = party;
    }

    @Id
    @GeneratedValue
    @Column(name="congressman_id")
    public int getCongressmanId() { return id; }
    public void setCongressmanId(int id) { this.id = id; }
    
    @Column(name="first_name")
    public String getFirstName() { return firstName; } 
    public void setFirstName(String name) { firstName = name; }

    @Column(name="last_name")
    public String getLastName() { return lastName; }
    public void setLastName(String name) { lastName = name; }
   
    @Column(name="party")
    public String getparty() { return party; }
    public void setparty(String party) { this.party = party; }    
    
    @ManyToOne
    @JoinColumn(name="state_code")
    public State getState() { return state; }
    public void setState(State state) { this.state = state; }    
    
    @ManyToMany
    @JoinTable(name="congressman_committee", 
            joinColumns={@JoinColumn(name="congressman_id")},
            inverseJoinColumns={@JoinColumn(name="committee_id")})
    public List<Committee> getCommittees() { return committees; }
    public void setCommittees(List<Committee> committees) { this.committees = committees;}
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn (name = "districtID")
    public District getDistrictID() {return district;}
    public void setDistrictID(District aDistrict) {this.district = aDistrict;}
    
    
    /**
     * Load the congressman table
     */   
    public static void load() {
        
        Session session = HibernateContext.getSession();
        
        // Load the tables in a transaction
        Transaction tx = session.beginTransaction();
        {
            session.save(new Congressman("Frank", "Lucas", "Republican"));            
            session.save(new Congressman("David", "Scott", "Democratic"));
            session.save(new Congressman("Jim", "Costa", "Democratic"));
            session.save(new Congressman("Fred", "Upton", "Republican"));
            session.save(new Congressman("Ralph", "Hall", "Republican"));
            session.save(new Congressman("Paul", "Tonko", "Democratic"));
            session.save(new Congressman("Ed", "Markey", "Democratic"));
            session.save(new Congressman("Andrew", "Harris", "Republican"));
            session.save(new Congressman("Kevin", "Cramer", "Republican"));
            session.save(new Congressman("Paul", "Broun", "Republican"));
            session.save(new Congressman("Michael", "McCaul", "Republican"));
            session.save(new Congressman("Randy", "Neugebauer", "Republican"));
        }
        tx.commit();
        
        session.close();
    }
    
    
    /**
     * List all the congressmen
     */
    public static void list() {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Congressman");
        
        System.out.println("All comgressmen: ");
        
        for(Congressman c : (List<Congressman>)query.list()) {
            c.print();
        }
        
        session.close();
    }
    
    
    /**
     * Fetch the congressman with a matching id.
     * @param id the id to match.
     * @return the student or null.
     */
    public static Congressman find(int id)
    {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from comgressman where id = :id");
        
        query.setInteger("id", id);
        Congressman congressman = (Congressman) query.uniqueResult();
        
        session.close();
        return congressman;
    }
    
    
    /**
     * Find the congressman by his/her last name
     * @param lastName
     * @return a congressman object
     */
    public static Congressman find(String lastName) {
        
        Session session = HibernateContext.getSession();
        Query query = 
                session.createQuery("from Congressman where last_name = :name");
        
        query.setString("name", lastName);
        Congressman congressman = (Congressman)query.uniqueResult();
        
        session.close();
        return congressman;
    }
    
    
    /**
     * Print congressman attributes
     */
    public void print() {
        System.out.printf("%d: %s %s %s\n", id, firstName, lastName, party);
    }
    
}
