/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project_hibernate;

import java.util.*;
import javax.persistence.*;
import org.hibernate.*;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
    
    public Congressman(String firstName, String lastName, String party, State state) {
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.party = party;
        this.state = state;
    }

    @Id
    @GeneratedValue
    @Column(name="congressman_id")
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
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
        
        State ca = State.find("CA");
        State ga = State.find("GA");
        State md = State.find("MD");
        State mi = State.find("MI");
        State ma = State.find("MA");
        State nd = State.find("ND");
        State ny = State.find("NY");
        State ok = State.find("OK");
        State tx = State.find("TX");
        
        Congressman c1 = new Congressman("Frank", "Lucas", "Republican", ok);
        Congressman c2 = new Congressman("David", "Scott", "Democratic", ga);
        Congressman c3 = new Congressman("Jim", "Costa", "Democratic", ca);
        Congressman c4 = new Congressman("Fred", "Upton", "Republican", mi);
        Congressman c5 = new Congressman("Ralph", "Hall", "Republican", tx);
        Congressman c6 = new Congressman("Paul", "Tonko", "Democratic", ny);
        Congressman c7 = new Congressman("Ed", "Markey", "Democratic", ma);
        Congressman c8 = new Congressman("Andrew", "Harris", "Republican", md);
        Congressman c9 = new Congressman("Kevin", "Cramer", "Republican", nd);
        Congressman c10 = new Congressman("Paul", "Broun", "Republican", ga);
        Congressman c11 = new Congressman("Michael", "McCaul", "Republican", tx);
        Congressman c12 = new Congressman("Randy", "Neugebauer", "Republican", tx);
        
        
        // Load the tables in a transaction
        Transaction tran = session.beginTransaction();
        {
            session.save(c1);
            session.save(c2);
            session.save(c3);
            session.save(c4);
            session.save(c5);
            session.save(c6);
            session.save(c7);
            session.save(c8);
            session.save(c9);
            session.save(c10);
            session.save(c11);
            session.save(c12);
        }
        tran.commit();
        
        session.close();
    }
        
    /**
     * List all the congressmen
     */
    public static void list() {
        Session session = HibernateContext.getSession();
        Criteria criteria = session.createCriteria(Congressman.class);
        criteria.addOrder(Order.asc("id"));
        
        List<Congressman> congressmen = criteria.list();
        System.out.println("All comgressmen: ");
        
        for(Congressman congressman : congressmen) {
            congressman.print();
            
            for(Committee committee : congressman.getCommittees()) {
                System.out.printf(" In committee: %s\n", committee.getName());
            }
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
        Query query = session.createQuery("from Congressman where congressman_id = :id");
        
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
    
    
    public static void congressmanInDistrict(String district) {
        
        // To be fill up
    }
    
    
    /**
     * Print congressmen from state
     * @param stateCode name code for state
     */
    public static void congressmanInState(String stateCode) {
        
        Session session = HibernateContext.getSession();
        Criteria congressmanCriteria = session.createCriteria(Congressman.class);
        Criteria stateCriteria = congressmanCriteria.createCriteria("state");
        
        stateCriteria.add(Restrictions.eq("stateCode", stateCode));
        
        // Distict congressman sorted by id
        congressmanCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        congressmanCriteria.addOrder(Order.asc("id"));
        
        List<Congressman> congressmen = (List<Congressman>)congressmanCriteria.list();
        System.out.printf("\nCongressmen in state %s:\n", stateCode);
        
        for(Congressman congressman : congressmen) {
            System.out.printf("%d, %s %s\n", congressman.getId(), 
                    congressman.getFirstName(), congressman.getLastName());
        }
        
        session.close();        
    }
    
    
    /**
     * Print committee's members
     * @param committee_name the name of the committee
     */
    public static void congressmanInCommittee(String committee_name) {
        
        Session session = HibernateContext.getSession();
        Criteria congressmanCriteria = session.createCriteria(Congressman.class);
        Criteria committeeCriteria = congressmanCriteria.createCriteria("committees");
        
        committeeCriteria.add(Restrictions.eq("name", committee_name));
        
        // Distict congressman sorted by id
        congressmanCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        congressmanCriteria.addOrder(Order.asc("id"));
        
        List<Congressman> congressmen = (List<Congressman>)congressmanCriteria.list();
        System.out.printf("\nCongressmen in committee %s:\n", committee_name);
        
        for(Congressman congressman : congressmen) {
            System.out.printf("%d, %s %s\n", congressman.getId(), 
                    congressman.getFirstName(), congressman.getLastName());
        }
        
        session.close();
    }    
    
    
    /**
     * Print congressman attributes
     */
    public void print() {
        System.out.printf("\n%d, %s %s, %s, %s\n", id, firstName, lastName, party, state.getStateCode());
    }

    
    /**
     * Print congressman attributes within a session.
     */
    public void printInSession()
    {
        Session session = HibernateContext.getSession();
        session.update(this);
        print();
        session.close();
    }
    
}
