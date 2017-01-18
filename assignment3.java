//this is very very basic and wont even compile. Id like you guys to read the function names and descriptions and think if I //have thought about all that is required and put something there if you think appropriate. also write on messenger if you //change something and update the pastebin. And please think on how to store the grid in memory because I dont have a very //efficient idea for it.



import ShefRobot.*;
import sheffield.*;
import java.util.Scanner;

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
    static boolean[] used = new boolean[9];
    static int[] posX = new int[9];
    static int[] posY = new int[9];
    static final int WALKINGSPEED = 100;
    public static void findTheLine(Robot myRobot, Motor leftMotor, Motor rightMotor, Speaker speaker, ColorSensor colorSensor) {

        ColorSensor.Color x;

      //robot follows the black line (ideally to the left) until it finds a coloured dot

      leftMotor.forward();
      rightMotor.forward();
      x = colorSensor.getColor();

      while(x != ColorSensor.Color.BLACK && x!=ColorSensor.Color.BROWN) {
        System.out.println(x);
        leftMotor.stop();
        rightMotor.stop();
        leftMotor.setSpeed(WALKINGSPEED);
        rightMotor.setSpeed(WALKINGSPEED);
        leftMotor.forward();
        rightMotor.forward();
        myRobot.sleep(100);

        x = colorSensor.getColor();
      }

      leftMotor.setSpeed(0);
      rightMotor.setSpeed(WALKINGSPEED);
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
    public static void followTheLine(Robot myRobot, Motor leftMotor, Motor rightMotor, Speaker speaker, ColorSensor colorSensor, boolean[] used) {
        ColorSensor.Color lastDotColor, x = colorSensor.getColor();

        while(x == ColorSensor.Color.BLACK || x == ColorSensor.Color.BLUE || x==ColorSensor.Color.BROWN) {
          boolean checkedonce = false;
          leftMotor.forward();
          rightMotor.forward();
          x = colorSensor.getColor();
          System.out.println(x);
            if(x==ColorSensor.Color.BROWN && checkedonce == false) {
              checkedonce = true;
              System.out.println("continued for brown");
              continue;
            }
            else if(x == ColorSensor.Color.BLACK || x == ColorSensor.Color.BROWN) {
              checkedonce = false;
              leftMotor.setSpeed(WALKINGSPEED);
              rightMotor.setSpeed(0);

            }
            else {
              checkedonce = false;
              leftMotor.setSpeed(0);
              rightMotor.setSpeed(WALKINGSPEED);
            }
        }
        lastDotColor = x;
        //analyzeDot(colorSensor, ...);
        boolean ending = checkForSecond(lastDotColor, leftMotor, rightMotor, used);
        if(ending == true) {
          leftMotor.stop();
          rightMotor.stop();
          return;
        }
        findnextLine(myRobot, rightMotor, leftMotor, speaker, colorSensor, lastDotColor, used);
        return;

    }
    public static boolean checkForSecond(ColorSensor.Color lastDot, Motor leftMotor, Motor rightMotor, boolean[] used) {
      if(used[lastDot.ordinal()] == true) {
          spin(leftMotor, rightMotor);
          System.out.println("been THERE1");
          return true;
      }
      else {
        used[lastDot.ordinal()] = true;
        System.out.println("been here");
      }
      return false;
    }

    public static void findnextLine(Robot myRobot, Motor rightMotor, Motor leftMotor, Speaker speaker, ColorSensor colorSensor, ColorSensor.Color lastDotColor, boolean[] used ){
      rightMotor.setSpeed(WALKINGSPEED);
      leftMotor.setSpeed(WALKINGSPEED);
      rightMotor.stop();
      leftMotor.stop();
      Scanner scanner = new Scanner(System.in);
      System.out.println("Should I find the next line?");
      int ans = scanner.nextInt();
      if(ans!=1)return;
      ColorSensor.Color x;
      x = colorSensor.getColor();
      while(x != ColorSensor.Color.BLACK || x==lastDotColor) {
        System.out.println(x);
        if(x == ColorSensor.Color.BLUE) {
          rightMotor.setSpeed(WALKINGSPEED);
          leftMotor.setSpeed(0);
        }
        else {
          leftMotor.setSpeed(WALKINGSPEED);
          rightMotor.setSpeed(0);
        }
        leftMotor.forward();
        rightMotor.forward();
        x = colorSensor.getColor();

      }
      followTheLine(myRobot, leftMotor, rightMotor, speaker, colorSensor, used);
      return;
    }
    // robot stores the dot position (how?) and its colour, updates the graph and the boolean array whether this colour has already been used. then checks if the dot has been used before - if so, then calls goToDot(previous dot). otherwise searches for another black line. I could try and implement that and goToDot(seb)
    //commented because it doesn't compile -> declare posX,posY etc properly
    public static void analyzeDot(ColorSensor colorSensor,int positionX,int positionY, Motor leftMotor, Motor rightMotor) {
      ColorSensor.Color dotColor =  colorSensor.getColor();

      if(colorSensor.getColor()!=ColorSensor.Color.BLACK && colorSensor.getColor()!=ColorSensor.Color.WHITE) {
        dotColor=colorSensor.getColor();
        if(used[dotColor.ordinal()]) {
          //goToDot(colorSensor, leftMotor, rightMotor, dotColor);
        }
        else {
          posX[dotColor.ordinal()]=positionX;
          posY[dotColor.ordinal()]=positionY;
          used[dotColor.ordinal()]=true;
        }

      }
      return;
    }
    /*public static goToDot(colorSensor, leftMotor, rightMotor, dotColor) {

    }
    public static void makeSound() {
      return;
    }*/


    public static void spin(Motor leftMotor, Motor rightMotor) {
      //TODO:some sounds when rotating
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
      return;
    }

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
        followTheLine(myRobot, leftMotor, rightMotor, speaker, colorSensor, used);



        myRobot.close();

    }

}
