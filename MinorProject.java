import edu.princeton.cs.introcs.StdDraw;
import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.Timer;

public class MinorProject {
    public static void main(String[] args) throws IOException {
        IODevice myGroveBoard = new FirmataDevice("COM3");
        try {
            myGroveBoard.start();
            System.out.println("Board started.");
            myGroveBoard.ensureInitializationIsDone();
        } catch (Exception ex) {
            System.out.println("couldn't connect to board.");
        }

        Pin theSensor;
        theSensor = myGroveBoard.getPin(15);
        theSensor.setMode(Pin.Mode.ANALOG);
        Pin thePump;
        thePump = myGroveBoard.getPin(7);
        thePump.setMode(Pin.Mode.OUTPUT);

        I2CDevice i2cDevice= myGroveBoard.getI2CDevice((byte) 0x3c);
        SSD1306 theScreen= new SSD1306(i2cDevice, SSD1306.Size.SSD1306_128_64);
        theScreen.init();

        StdDraw.setCanvasSize(400,400);
        StdDraw.setXscale(0,50);
        StdDraw.setYscale(0,900);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(25,0,"Time[s]");
        StdDraw.text(0,450,"Moisture Level[V]",90);
        StdDraw.text(25,900,"Moisture Level / Time");
        StdDraw.line(2,25,500,25);
        StdDraw.line(2,25,2,1000);


        Timer timer = new Timer();
        starOfTheShow task;
        task = new starOfTheShow(theSensor, thePump, theScreen);
        timer.schedule(task, 0,1000);
    }
}