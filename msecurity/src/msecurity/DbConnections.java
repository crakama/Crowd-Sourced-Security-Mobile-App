
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msecurity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DbConnections {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/msecurity";
   // ReadMessage sendsms=new ReadMessage();
    Connection Connlog = null;

    public String getPoliceName() {
        return PoliceName;
    }

    public String getPoliceStationName() {
        return PoliceStationName;
    }

    public int getNo() {
        return no;
    }
    Statement Statlog = null;
    PreparedStatement report_record, Preplog1, ps_insertUserReport;
    String District = "", Division = "", UserName = "", UserLocation = "";
    String PoliceName = "", PoliceStationLocation = "", PoliceStationName = "", PoliceOfficerServiceNo = "";
    int UserIDNo, no,ReportID,txtReportID;

    public void conx(String user, String pswd) {
        try {
            Class.forName(JDBC_DRIVER);
            Connlog = DriverManager.getConnection(DB_URL, user, pswd);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnections.class.getName()).log(Level.SEVERE, null, ex);
            System.out.printf("Erro");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnections.class.getName()).log(Level.SEVERE, null, ex);
            System.out.printf("Erro");
        }
    }

    public boolean insertUserReport(String userPhoneNumber, String reportBody, String IncidentLocation) {
        Random randno = new Random();
        no = randno.nextInt(2000);
        conx("root", "");
        String sql_insertUserReport = "INSERT INTO userreports(ReportID,ReportBody,IncidentLocation,UserPhoneNumber) VALUES(?,?,?,?)";
        try {
            ps_insertUserReport = Connlog.prepareStatement(sql_insertUserReport);
            ps_insertUserReport.setInt(1, no);
            ps_insertUserReport.setString(2, reportBody);
            ps_insertUserReport.setString(3, IncidentLocation);
            ps_insertUserReport.setString(4, userPhoneNumber);
            ps_insertUserReport.execute();
            Connlog.close();
            System.out.println("DB Insert");
        } catch (SQLException ex) {
            System.out.printf("Error in InsertUserReport");
        }
        assignReport(IncidentLocation,userPhoneNumber);
        
      // sendsms.send(no,PoliceName,PoliceStationName,userPhoneNumber);
        return true;
    }

    public String assignReport(String IncidentLocation, String UserPhoneNumber) {
        conx("root", "");
        try {
            PreparedStatement ps_insertassignReport = null;
            //PoliceName = "", PoliceStationLocation = "", PoliceStationName = "", PoliceOfficerServiceNo = "";
            String sql_assignReport_Police = "SELECT * FROM policeofficers WHERE "
                    + "Dutystatus=? AND PoliceStationLocation=? limit 1";
            ps_insertassignReport = Connlog.prepareStatement(sql_assignReport_Police);
            ps_insertassignReport.setString(1, "On Duty");
            ps_insertassignReport.setString(2, IncidentLocation);
            ResultSet rs;

            rs = ps_insertassignReport.executeQuery();

            while (rs.next()) {
                PoliceName = rs.getString("PoliceName");
                PoliceStationLocation = rs.getString("PoliceStationLocation");
                PoliceStationName = rs.getString("PoliceStationName");
                PoliceOfficerServiceNo = rs.getString("PoliceOfficerServiceNo");
            }
            rs.close();
            Connlog.close();
            VerifyUser(UserPhoneNumber);
            System.out.println("Police Name"+ PoliceName );
            insertReportTrack(UserIDNo, no, IncidentLocation, PoliceName, PoliceStationName);
           // sendReportConfirmation(ReportID,PoliceStationName ,IncidentLocation);
            // insertReportTrack(String UserIDNo,String IncidentLocation,String PoliceName,String PoliceStationName
        } catch (SQLException ex) {
            Logger.getLogger(DbConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void VerifyUser(String UserPhoneNumber) {
        conx("root", "");
        try {
            //String District="",Division="",UserName="",UserLocation="",UserIDNo="";
            PreparedStatement ps_assignReport_User = null;
            String sql_assignReport_User = "SELECT * FROM users WHERE "
                    + "UserPhoneNumber=?";
            ps_assignReport_User = Connlog.prepareStatement(sql_assignReport_User);
            ps_assignReport_User.setString(1, UserPhoneNumber);
            ResultSet rs = null;
            rs = ps_assignReport_User.executeQuery();

            while (rs.next()) {
                District = rs.getString("District");
                Division = rs.getString("Division");
                UserName = rs.getString("UserName");
                UserLocation = rs.getString("UserLocation");
                UserIDNo = rs.getInt("UserIDNo");
                
            }
            rs.close();
            Connlog.close();

        } catch (SQLException ex) {
            Logger.getLogger(DbConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertReportTrack(int UserIDNo, int ReportID, String IncidentLocation, String PoliceName, String PoliceStationName) {
        conx("root", "");
        String sql_assignReport = "INSERT INTO reporttracking(PoliceName,"
                + "IncidentLocation,PoliceStationName,ReportID,IncidentReporterID,IncidentStatus,IncidentProgress) VALUES(?,?,?,?,?,?,?)";
        try {
            int IncidentReporterID = UserIDNo;
            PreparedStatement ps_insertassignReport =null;
                    ps_insertassignReport = Connlog.prepareStatement(sql_assignReport);
            ps_insertassignReport.setString(1, PoliceName);
            ps_insertassignReport.setString(2, IncidentLocation);
            ps_insertassignReport.setString(3, PoliceStationName);
            ps_insertassignReport.setInt(4, ReportID);
            ps_insertassignReport.setInt(5, IncidentReporterID);
            ps_insertassignReport.setString(6, "OPEN");
            ps_insertassignReport.setString(7, "NO LEADS");
            ps_insertassignReport.execute();
            Connlog.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendReportConfirmation(int ReportID,String PoliceStationName ,String PoliceName ) {
        conx("root", "");
        ReportID=no;
        try {
 
            PreparedStatement ps_confirmation_msg_User = null;
            String sql_assignReport_User = "SELECT PoliceName,PoliceStationName,ReportID FROM reporttracking WHERE "
                    + "ReportID=?";
            ps_confirmation_msg_User = Connlog.prepareStatement(sql_assignReport_User);
            ps_confirmation_msg_User.setInt(1,ReportID);
            ResultSet rs = null;
            rs = ps_confirmation_msg_User.executeQuery();

            while (rs.next()) {
                PoliceName = rs.getString("PoliceName");
                PoliceStationName = rs.getString("PoliceStationName");
                txtReportID= rs.getInt(ReportID);
                System.out.println("Police Name: "+PoliceName+"PoliceStationName:"+PoliceStationName
                        +"ReportID: "+txtReportID);
            }
            rs.close();
            Connlog.close();
          //  sendsms.send(, DB_URL, rs);

        } catch (SQLException ex) {
            Logger.getLogger(DbConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertDb(String userName, String userPhone, String userLocation, String userReport) {
        conx("root", "");
        String report_sql = "INSERT INTO reports(reportID,userName,userPhone,userLocation,userReport)VALUES(?,?,?,?,?)";
        try {

            report_record = Connlog.prepareStatement(report_sql);
            report_record.setInt(1, 3);
            report_record.setString(2, userName);
            report_record.setString(3, userPhone);
            report_record.setString(4, userLocation);
            report_record.setString(5, userReport);
            report_record.execute();
            Connlog.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbConnections.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
