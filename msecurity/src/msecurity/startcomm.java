/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msecurity;

import org.smslib.AGateway;
import org.smslib.Library;
import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author admin
 */
public class startcomm {

    public SerialModemGateway gateway;

    public void doit() {
        // Create the Gateway representing the serial GSM modem.
        gateway = new SerialModemGateway("", "COM4", 115200, "Huawei", "");
        gateway.setProtocol(AGateway.Protocols.PDU);
        // Set the modem protocol to PDU (alternative is TEXT). PDU is the default, anyway...
        // Do we want the Gateway to be used for Inbound messages?
        gateway.setInbound(true);
        // Do we want the Gateway to be used for Outbound messages?
        gateway.setOutbound(true);
        // Let SMSLib know which is the SIM PIN.
        gateway.setSimPin("");
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());

    }
        public static void main(String[] args) {
        // TODO code application logic here
        startcomm sc=new startcomm();
        sc.doit();

        
    }
}
