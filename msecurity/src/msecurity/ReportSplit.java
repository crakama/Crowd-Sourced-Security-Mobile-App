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
public class ReportSplit {
    static String userNumber="",phone="",body="",location="";
    DbConnections dbconnect=new DbConnections();
    public  void spliter(){
        String [] text2="Kajiado, bank robbery".split(",");
        System.out.println("Lenth"+text2.length);
        userNumber="254728136671";

            for (String text21 : text2) {
            System.out.println(text21);
            location=text2[0];
            body=text2[1];
            
                   }
        System.out.println("##############################################");
   if  (text2.length==2){
       System.out.println("userNumber:"+userNumber);
       System.out.println("Location:"+location);
System.out.println("Body"+body);
       //dbconnect.assignReport("Kajiado", "254728136671");
dbconnect.insertUserReport(userNumber, body, location);
        //dbconnect.conx("root","");
         //sto.insertDb(userName,phone,location,body);
         //System.out.println("Inserted");
    }      else
        System.out.println("Not Inserted");
                }
   /*public static void main(String[] args) {
        // TODO code application logic here
       // Strings strings=new Strings();
        //tokenizer();
       ReportSplit rs=new ReportSplit();
       rs.spliter();
 
   }*/
}
