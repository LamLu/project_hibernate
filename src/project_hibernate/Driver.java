/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project_hibernate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author sean
 */
public class Driver {
    
    private static final String HELP_MSG = 
            "Commands: create, load, find<n>, congressmen, districts, "
            + "committees, states";
    
    public static void main(String[] args) {
        
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String command = "";
        
        Class[] clases = {Congressman.class, Committee.class, State.class, District.class};
        
        HibernateContext.addClasses(clases);
        
        do {
            System.out.println();
            System.out.println(HELP_MSG);
            System.out.print("Command? ");
            
            try {
                command = stdin.readLine();
            }
            catch(java.io.IOException ex) {
                command = "?";
            }
            
            String parts[] = command.split(" ");
            
            if(command.equalsIgnoreCase("create")) {
                HibernateContext.createSchema();
            }
            else if(command.equalsIgnoreCase("load")) {
                Congressman.load();
                Committee.load();
                District.load();
                State.load();
            }
            else if(command.equalsIgnoreCase("congressmen")) {
                Congressman.list();
            }
            else if(command.equalsIgnoreCase("districts")) {
                
            }
            else if(command.equalsIgnoreCase("committees")) {
                Committee.list();
            }
            else if(command.equalsIgnoreCase("states")) {
                State.list();
            }
            else if(parts[0].equalsIgnoreCase("find") && parts.length >= 2) {
                
            }
        } while(!command.equalsIgnoreCase("quit"));
        
    }
    
}
