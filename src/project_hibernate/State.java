/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project_hibernate;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sean
 */

@Entity
@Table(name="state")
public class State {

    private int id;
    private String stateCode;
    private List<Congressman> congressmen;
    
    public State() {}
    
    public State(String stateCode) {
        
        this.stateCode = stateCode;
    }

    @Id
    @GeneratedValue
    @Column(name="id")
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    @Column(name="state_code")
    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }
    
    @OneToMany(mappedBy="state", targetEntity=Congressman.class,
            cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    public List<Congressman> getCongressmen() { return congressmen; }
    public void setCongressmen(List<Congressman> congressmen) { this.congressmen = congressmen; }
    
    /**
     * Load the state table
     */
    public static void load() {
        
        Session session = HibernateContext.getSession();
        
        // Load the tables in a transaction
        Transaction tx = session.beginTransaction();
        {
            session.save(new State("AL"));
            session.save(new State("AK"));
            session.save(new State("AZ"));
            session.save(new State("AR"));
            session.save(new State("CA"));
            session.save(new State("CO"));
            session.save(new State("CT"));
            session.save(new State("DE"));
            session.save(new State("FL"));
            session.save(new State("GA"));
            session.save(new State("HI"));
            session.save(new State("ID"));
            session.save(new State("IL"));
            session.save(new State("IN"));
            session.save(new State("IA"));
            session.save(new State("KS"));
            session.save(new State("KY"));
            session.save(new State("LA"));
            session.save(new State("ME"));
            session.save(new State("MD"));
            session.save(new State("MA"));
            session.save(new State("MI"));
            session.save(new State("MN"));
            session.save(new State("MS"));
            session.save(new State("MO"));
            session.save(new State("MT"));
            session.save(new State("NE"));
            session.save(new State("NV"));
            session.save(new State("NH"));
            session.save(new State("NJ"));
            session.save(new State("NM"));
            session.save(new State("NY"));
            session.save(new State("NC"));
            session.save(new State("ND"));
            session.save(new State("OH"));
            session.save(new State("OK"));
            session.save(new State("OR"));
            session.save(new State("PA"));
            session.save(new State("RI"));
            session.save(new State("SC"));
            session.save(new State("SD"));
            session.save(new State("TN"));
            session.save(new State("TX"));
            session.save(new State("UT"));
            session.save(new State("VT"));
            session.save(new State("VA"));
            session.save(new State("WA"));
            session.save(new State("WV"));
            session.save(new State("WI"));
            session.save(new State("WY"));
        }
        tx.commit();
        
        session.close();
		System.out.println("State is loaded");
    }
    
    /**
     * List all the states
     */
    public static void list() {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from State");
        
        System.out.println("\nAll states: ");
        
        for(State s : (List<State>)query.list()) {
            s.print();
        }
        
        session.close();
    }
    
    /**
     * Find the state by its code
     * @param stateCode the name code for the state
     * @return a State object
     */
    public static State find(String stateCode) {
        
        Session session = HibernateContext.getSession();
        Query query = 
                session.createQuery("from State where state_code = :stateCode");
        
        query.setString("stateCode", stateCode);
        State state = (State)query.uniqueResult();
        
        session.close();
        return state;
    }    
    
    /**
     * Print state attributes
     */
    public void print() {
        System.out.printf("%d: %s\n", id, stateCode);
    }
    
}
