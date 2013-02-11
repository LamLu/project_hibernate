/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project_hibernate;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sean
 */

@Entity
@Table(name="committee")
public class Committee {
    
    private int id;
    private String name;
    private List<Congressman> congressmen = new ArrayList<Congressman>();
    
    public Committee() {};
    
    public Committee(String name) {
        
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name="committee_id")
    public int getID() { return id; }
    public void setID(int id) { this.id = id; }
    
    @Column(name="name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @ManyToMany
    @JoinTable(name="congressman_committee", 
            joinColumns={@JoinColumn(name="committee_id")},
            inverseJoinColumns={@JoinColumn(name="congressman_id")})
    public List<Congressman> getCongressmen() { return congressmen; }
    public void setCongressmen(List<Congressman> congressmen) { this.congressmen = congressmen;}    
    
    
    /**
     * Load the Congressman table
     */
    public static void load() {
        
        Session session = HibernateContext.getSession();
        
        Committee agr = new Committee("Agriculture");
        Committee enc = new Committee("Engrgy_and_Commerce");
        Committee nr = new Committee("Natural_Resource");
        Committee sst = new Committee("Science_Space_and_Technology");
        
        Congressman lucas = Congressman.find("Lucas");
        Congressman scott = Congressman.find("Scott");
        Congressman costa = Congressman.find("Costa");
        Congressman upton = Congressman.find("Upton");
        Congressman hall = Congressman.find("Hall");
        Congressman tonko = Congressman.find("Tonko");
        Congressman markey = Congressman.find("Markey");
        Congressman harris = Congressman.find("Harris");
        Congressman cramer = Congressman.find("Cramer");
        Congressman broun = Congressman.find("Broun");
        Congressman mccaul = Congressman.find("McCaul");
        Congressman neugebauer = Congressman.find("Neugebauer");
        
        // Assign committees to congressmen
        agr.getCongressmen().add(lucas);
        agr.getCongressmen().add(scott);
        agr.getCongressmen().add(costa);
        agr.getCongressmen().add(neugebauer);
        enc.getCongressmen().add(upton);
        enc.getCongressmen().add(hall);
        enc.getCongressmen().add(tonko);
        enc.getCongressmen().add(markey);
        nr.getCongressmen().add(markey);
        nr.getCongressmen().add(harris);
        nr.getCongressmen().add(cramer);
        nr.getCongressmen().add(costa);
        nr.getCongressmen().add(tonko);
        sst.getCongressmen().add(broun);
        sst.getCongressmen().add(mccaul);
        sst.getCongressmen().add(neugebauer);
        sst.getCongressmen().add(lucas);
        sst.getCongressmen().add(hall);
        sst.getCongressmen().add(tonko);
        sst.getCongressmen().add(harris);
        sst.getCongressmen().add(cramer);
        
        // Load the tables in a transaction
        Transaction tx = session.beginTransaction();
        {
            session.save(agr);
            session.save(enc); 
            session.save(nr);
            session.save(sst);
        }
        tx.commit();
        
        session.close();
		System.out.println("Committee is loaded");
        
    }
    
    /**
     * List all the congressmen
     */
    public static void list() {
        Session session = HibernateContext.getSession();
        Query query = session.createQuery("from Committee");
        
        System.out.println("\nAll committees: ");
        
        for(Committee c : (List<Committee>)query.list()) {
            c.print();
        }
        
        session.close();
    }
       
    /**
     * Print committee attributes
     */
    public void print() {
        System.out.printf("%d: %s\n", id, name);
    }    
}
