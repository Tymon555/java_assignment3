import ShefRobot.*;
import sheffield.*;
import java.util.Scanner;

/**
 * @author Tymon Solecki, Sebastian Ksiazczyk, Aydin Hepsaydir
 */

public class RobotAssignment3 {

    //an array containing information whether a certain colour of dot has already occured
    static boolean[] used = new boolean[9];
    static final int WALKING_SPEED = 100;
    static final int SPIN_SPEED = 300;
    
    /**
    * @param myRobot - The robot
    * @param leftMotor - The left motor
    * @param rightMotor - The right motor
    * @param speaker - The speaker
    * @param colorSensor - The colour sensor 
    */
    public static void findTheLine(Robot myRobot, Motor leftMotor, Motor rightMotor, Speaker speaker, ColorSensor colorSensor) {

      //robot moves forward until its color sensor detects a black line
      
      ColorSensor.Color currentColor;

      leftMotor.forward();
      rightMotor.forward();
      currentColor = colorSensor.getColor();

      while(currentColor != ColorSensor.Color.BLACK && currentColor!=ColorSensor.Color.BROWN) {
        // a loop taking the color sample and checking whether it is a black line or not
        
        leftMotor.stop();
        rightMotor.stop();

        leftMotor.setSpeed(WALKING_SPEED);
        rightMotor.setSpeed(WALKING_SPEED);

        leftMotor.forward();
        rightMotor.forward();

        myRobot.sleep(100);

        currentColor = colorSensor.getColor();
      }
      // once the line is found the robot follows it
   
      leftMotor.setSpeed(0);
      rightMotor.setSpeed(WALKING_SPEED);
      do {
        currentColor = colorSensor.getColor();
      }
      while(currentColor!=ColorSensor.Color.BLACK);

      return;

    }

    /**
    * @param myRobot - The robot
    * @param leftMotor - The left motor
    * @param rightMotor - The right motor
    * @param speaker - The speaker
    * @param colorSensor - The colour sensor 
    * @param used - Boolean array to check whether colour has already occured
    */
    public static void followTheLine(Robot myRobot, Motor leftMotor, Motor rightMotor, Speaker speaker, ColorSensor colorSensor, boolean[] used) {
        ColorSensor.Color lastDotColor;
        ColorSensor.Color currentColor = colorSensor.getColor();

        while(currentColor == ColorSensor.Color.BLACK || currentColor == ColorSensor.Color.BLUE || currentColor==ColorSensor.Color.BROWN) {
          // checkedOnce is used so that the robot performs an additional iteration for accuracy purposes          
          boolean checkedOnce = false;

          leftMotor.forward();
          rightMotor.forward();

          currentColor = colorSensor.getColor();

            if(currentColor==ColorSensor.Color.BROWN && !checkedOnce) {
              checkedOnce = true;
              continue;
            }
            // the robot performs turns right and left, following the straight black line
            else if(currentColor == ColorSensor.Color.BLACK || currentColor == ColorSensor.Color.BROWN) {
              checkedOnce = false;
              leftMotor.setSpeed(WALKING_SPEED);
              rightMotor.setSpeed(0);

            }
            else {
              checkedOnce = false;
              leftMotor.setSpeed(0);
              rightMotor.setSpeed(WALKING_SPEED);
            }
        }
        lastDotColor = currentColor;

        //if a second dot of some colour has been found, the robot stops
        boolean ending = checkForSecond(lastDotColor, leftMotor, rightMotor, used);
        if(ending) {
          leftMotor.stop();
          rightMotor.stop();
          return;
        }

        findNextLine(myRobot, rightMotor, leftMotor, speaker, colorSensor, lastDotColor, used);
        return;
    }

    /**
    * @param lastDot - The colour of the most recent dot so that the robot does not stop on the same dot thinking it has found one of the same colour
    * @param leftMotor - The left motor
    * @param rightMotor - The right motor
    * @param used - Boolean array to check whether colour has already occured
    * @return a boolean value of whether or not the robot hass left the current circle
    */
    public static boolean checkForSecond(ColorSensor.Color lastDot, Motor leftMotor, Motor rightMotor, boolean[] used) {
      // check if a second dot of the same colour has been found
      if(used[lastDot.ordinal()]) {
          spin(leftMotor, rightMotor);
          return true;
      }
      
      else {
        used[lastDot.ordinal()] = true;
      }
      return false;
    }

    /**
    * @param myRobot - The robot
    * @param leftMotor - The left motor
    * @param rightMotor - The right motor
    * @param speaker - The speaker
    * @param colorSensor - The colour sensor 
    * @param used - Boolean array to check whether colour has already occured
    */
    public static void findNextLine(Robot myRobot, Motor rightMotor, Motor leftMotor, Speaker speaker, ColorSensor colorSensor, ColorSensor.Color lastDotColor, boolean[] used ){
      // this function is used to find a new black line after analyzing a dot 
      rightMotor.setSpeed(WALKING_SPEED);
      leftMotor.setSpeed(WALKING_SPEED);
      // the motors are stopped because of the change of movement
      rightMotor.stop();
      leftMotor.stop();
      

      ColorSensor.Color currentColor;
      currentColor = colorSensor.getColor();
      
      while(currentColor != ColorSensor.Color.BLACK || currentColor == lastDotColor) {
        // robot follows the blue colour (the colour of the 'white' board)
        if(currentColor == ColorSensor.Color.BLUE) {
          rightMotor.setSpeed(WALKING_SPEED);
          leftMotor.setSpeed(0);
        }
        else {
          leftMotor.setSpeed(WALKING_SPEED);
          rightMotor.setSpeed(0);
        }
        //with each iteration robot goes forward a bit and senses a color at the new position
        leftMotor.forward();
        rightMotor.forward();
        currentColor = colorSensor.getColor();
      }
      
      followTheLine(myRobot, leftMotor, rightMotor, speaker, colorSensor, used);
      return;
    }

    /**
    * @param leftMotor - The left motor 
    * @param rightMotor - The right motor
    */
    public static void spin(Motor leftMotor, Motor rightMotor) {
    
      leftMotor.setSpeed(SPIN_SPEED);
      rightMotor.setSpeed(SPIN_SPEED);
      leftMotor.forward();
      rightMotor.backward();
      
      // the spin will last 5 seconds
      long curTime = System.currentTimeMillis();
      long finishTime = curTime+5000;
      if(System.currentTimeMillis() == finishTime) {
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
      }
      return;
    }

    public static void main(String[] args) {

      Robot myRobot = new Robot();

      ColorSensor colorSensor = myRobot.getColorSensor(Sensor.Port.S1);
      Motor leftMotor = myRobot.getLargeMotor(Motor.Port.C);
      Motor rightMotor = myRobot.getLargeMotor(Motor.Port.B);
      Speaker speaker = myRobot.getSpeaker();
      

      findTheLine(myRobot, leftMotor, rightMotor, speaker, colorSensor);
      followTheLine(myRobot, leftMotor, rightMotor, speaker, colorSensor, used);
      

      myRobot.close();

    }

}
