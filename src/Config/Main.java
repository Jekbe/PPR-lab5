package Config;

import Processes.ProcessA;
import Processes.ProcessB;
import Processes.ProcessC;
import Processes.ProcessD;

import javax.jms.JMSException;
import java.io.IOException;

class Main{
    public static void main(String[] args) throws JMSException, jakarta.jms.JMSException, IOException {
        ProcessA processA = new ProcessA();
        ProcessB processB = new ProcessB();
        ProcessC processC = new ProcessC();
        ProcessD processD = new ProcessD();

        processA.start();
        processB.start();
        processC.start();
        processD.start();

        System.out.println("Enter aby zakończyć");
        int _ = System.in.read();

        processA.destroy();
        processB.destroy();
        processC.destroy();
        processD.destroy();
    }
}