//Note via Seb
//this is very very basic and wont even compile. Id like you guys to read the function names and descriptions and think if I //have thought about all that is required and put something there if you think appropriate. also write on messenger if you //change something and update the pastebin. And please think on how to store the grid in memory because I dont have a very //efficient idea for it.



import ShefRobot.*;
import sheffield.*;

/**
 *
 * @author sdn
 */
public class assignment3 {

    /**
     * @param args the command line arguments
     */
    /*
      robot goes forward until it finds a black line
      it goes straightforward until black  or brown is seen - then it rotate to the left until it sees the line again.
    */
    public static void findTheLine(Robot myRobot, Motor leftMotor, Motor rightMotor, Speaker speaker, ColorSensor colorSensor) {

        ColorSensor.Color x;
        leftMotor.forward();
        rightMotor.forward();
        x = colorSensor.getColor();
        while(x != ColorSensor.Color.BLACK && x!=ColorSensor.Color.BROWN) {
          System.out.println(x);
          leftMotor.stop();
          rightMotor.stop();
            leftMotor.setSpeed(100);
            rightMotor.setSpeed(100);
            leftMotor.forward();
            rightMotor.forward();
            myRobot.sleep(100);

            x = colorSensor.getColor();
        }
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(80);
        do {
          x = colorSensor.getColor();
        }
        while(x!=ColorSensor.Color.BLACK);

        return;

    }

    /*
      robot follows the black line (ideally to the left) until it finds a coloured dot
      it goes by the right edge of the dot.\
      TODO: add next behaviour after the dot is found.
    */
    public static void followTheLine(Robot myRobot, Motor leftMotor, Motor rightMotor, Speaker speaker, ColorSensor colorSensor) {
        ColorSensor.Color x;
        int i =0;
        final double TRESHOLD = 0.4; //thershold between black and white
        while(i<300) {
          leftMotor.forward();
          rightMotor.forward();
          x = colorSensor.getColor();
          System.out.println(x);
            if(x == ColorSensor.Color.BLACK) {
              leftMotor.setSpeed(100);
              rightMotor.setSpeed(0);

            }
            else {
              leftMotor.setSpeed(0);
              rightMotor.setSpeed(100);
            }

            i++;
        }
        return;

    }
    /*robot stores the dot position (how?) and its colour, updates the graph and the boolean array whether this colour has already been used. then checks if the dot has been used before - if so, then calls goToDot(previous dot). otherwise searches for another black line. I could try and implement that and goToDot(seb)
    public static analyzeDot() {

    }
    public static goToDot() {

    }
    public static void makeSound() {

    }
    public static void spin() {

    }*/
    public static void testFunctions(Robot myRobot, Motor leftMotor,Motor rightMotor, Speaker speaker, ColorSensor colorSensor ) {
      //test if all the things work
      leftMotor.setSpeed(400);
      rightMotor.setSpeed(400);
      leftMotor.forward();
      rightMotor.forward();
      myRobot.sleep(1000);

      speaker.playTone(500,200);

      myRobot.sleep(100);

      return ;
    }

    public static void main(String[] args) {

        Robot myRobot = new Robot();
        ColorSensor colorSensor = myRobot.getColorSensor(Sensor.Port.S1);
        Motor leftMotor = myRobot.getLargeMotor(Motor.Port.C);
        Motor rightMotor = myRobot.getLargeMotor(Motor.Port.B);

        Speaker speaker = myRobot.getSpeaker();
        //testFunctions(myRobot, leftMotor, rightMotor, speaker, colorSensor);
        findTheLine(myRobot, leftMotor, rightMotor, speaker, colorSensor);
        followTheLine(myRobot, leftMotor, rightMotor, speaker, colorSensor);

        myRobot.close();

    }

}
