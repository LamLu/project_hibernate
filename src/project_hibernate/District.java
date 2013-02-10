package project_hibernate;

import java.util.List;


import javax.persistence.*;
import org.hibernate.*;
import org.hibernate.Query;

/**
 * this class represents a district table on the database
 * @author lamlu
 *
 */
@Entity
@Table(name = "district")
public class District 
{
	private int id;
	private String name;
	
	/**
	 * constructor for the class
	 * @param name the district name
	 */
	public District (String districtName)
	{
		this.name = districtName;
	}
	
	@Id
	@GeneratedValue
	@Column(name ="id")
	public int getID (){ return this.id;}
	
	public void setID (int anID){ this.id = anID;};
	
	@Column(name = "name")
	public String getName(){ return this.name;}
	public void setName(String aName){ this.name = aName;}
	
	/**
	 * Load the district name into district table
	 */
	public static void load()
	{
		Session session = HibernateContext.getSession();
		Transaction tx = session.beginTransaction();
		{
			session.save(new District("Texas's 19th District"));
			session.save(new District("Texas's 10th District"));
			session.save(new District("Georgia's 10th District"));
			session.save(new District("North Dakota's At-Large District"));
			session.save(new District("Maryland's 1st District"));
			session.save(new District("Massachusetts's 5th District"));
			session.save(new District("New York's 20th District"));
			session.save(new District("Texas's 4th District"));
			session.save(new District("Michigan's 6th District"));
			session.save(new District("California's 16th District"));
			session.save(new District("Georgia's 13th District"));
			session.save(new District("Oklahoma's 3rd District"));
		}
		tx.commit();
		session.close();
		System.out.print("District is loaded");
	}
	
	/**
	 * list all the districts
	 */
	public static void list()
	{
		Session session = HibernateContext.getSession();
		Query query = session.createQuery("from District");
		System.out.println("All District: ");
		for (District district : (List<District>) query.list())
		{
			System.out.format("%d: %s", district.getID(), district.getName());
		}
	}
}
