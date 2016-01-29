/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msecurity;

/**
 *
 * @author admin
 */
public class Msecurity {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        startcomm sc=new startcomm();
        ReadMessage readmessage=new ReadMessage();
        SendMessage sendmessage=new SendMessage();
        //sc.start();
        readmessage.start();
       // sendmessage.start();
        
    }
    
}
