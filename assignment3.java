//this is very very basic and wont even compile. Id like you guys to read the function names and descriptions and think if I //have thought about all that is required and put something there if you think appropriate. also write on messenger if you //change something and update the pastebin. And please think on how to store the grid in memory because I dont have a very //efficient idea for it.



import ShefRobot.*;
import sheffield.*;

/**
 *
 * @author sdn
 */

public class Assignment3 {

    /**
     * @param args the command line arguments
     */
    //robot goes forward until it finds a black line
    /*public static findTheLine() {

    }*/
    //robot follows the black line (ideally to the left) until it finds a coloured dot
    boolean used[] = new boolean[9];
    int posX[] = new int[9];
    int posY[] = new int[9];    

    public static followTheLine(leftMotor, rightMotor, speaker, colorSensor) {
        leftMotor.setSpeed(300);
        rightMotor.setSpeed(300);
        leftMotor.forward();
        rightMotor.forward();

        final float TRESHOLD = 0.4; //thershold between black and white

        while(true) {
            if(colorSensor.getAmbient() < TRESHOLD) {
              leftMotor.setSpeed(300);
              rightMotor.setSpeed(0);
            }
            else {
              leftMotor.setSpeed(0);
              rightMotor.setSpeed(300);
            }
        }
        return false;
    }
    // robot stores the dot position (how?) and its colour, updates the graph and the boolean array whether this colour has already been used. then checks if the dot has been used before - if so, then calls goToDot(previous dot). otherwise searches for another black line. I could try and implement that and goToDot(seb)
    public static analyzeDot(colorSensor, positionX, positionY) {
      ColorSensor.Color dotColor = new ColorSensor.Color;

      if(colorSensor.getColor()!=color.BLACK && colorSensor.getColor()!=color.WHITE) {
        dotColor=colorSensor.getColor();
        if(used[dotColor.ordinal()]) {
          goToDot(colorSensor, leftMotor, rightMotor, dotColor);
        }
        else {
          posX[dotColor.ordinal()]=positionX;
          posY[dotColor.ordinal()]=positionY;
          used[dotColor.ordinal()]=true;
        }

      }
    }
    public static goToDot(colorSensor, leftMotor, rightMotor, dotColor) {
      
    }
    public static void makeSound() {

    }*/
    public static void spin(leftMotor, rightMotor) {
      leftMotor.setSpeed(300);
      rightMotor.setSpeed(300);
      leftMotor.forward();
      rightMotor.backward();
      long curTime = System.currentTimeMillis();
      long finishTime = curTime+5000;
      if(System.currentTimeMillis() == finishTime) {
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);  
      }
    }
    public static void testFunctions(leftMotor, rightMotor, speaker, colorSensor) {
      //test if all the things work
      leftMotor.setSpeed(400);
      rightMotor.setSpeed(400);
      leftMotor.forward();
      rightMotor.forward();
      myRobot.sleep(1000);

      speaker.playTone(500,200);

      myRobot.sleep(100);

      return false;
    }

    public static void main(String[] args) {

        Robot myRobot = new Robot();
        Sensor mySensor= new Sensor();

        Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
        Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
        Speaker speaker = myRobot.getSpeaker();
        ColorSensor colorSensor = myRobot.getColorSensor(Sensor.Port.S1); //not sure about the sensor.port thing
        testFunctions(leftMotor, rightMotor, speaker, colorSensor);

        ColorSensor.Color color;



        myRobot.close();

    }

}
