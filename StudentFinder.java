import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.Object;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.time.Instant;


public class StudentFinder{

  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    Instant instant = Instant.now();
    System.out.println();

    System.out.println("\u001B[31m"); // color red

    System.out.println("----------------------------------");
    System.out.println("*   STUDENT FINDER ");
    System.out.println("*   EMPLOYEE   : ADRIEN HEYMANS ");
    System.out.println("*   DATE OF USE : "+ instant.toString());
    instant = instant.plusSeconds(5400);


    System.out.println("----------------------------------");
    System.out.println("\u001B[37m"); // reset color
    //wait(11);
    LocalDateTime time = LocalDateTime.now();
    int day = time.getDayOfMonth();
    int month = time.getMonthValue();
    int year = time.getYear();
    int hour = time.getHour();
    int minute = time.getMinute();
    int seconds = time.getSecond();
    System.out.println("Attendance document created...");
    //"attendance"+"_"+year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+seconds+".csv";
    String name = "sswCareerDev"+"_"+year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+seconds+".csv";
    //String name = "mail_not_received.csv";



    try (PrintWriter writer = new PrintWriter(new File(name))) {

      StringBuilder sb = new StringBuilder();
      String[] tab = new String[100];
      int nbr=0;


      for (int i =0;i<100;i++){
        System.out.print("\u001B[31m"); // color red

        System.out.print("Please enter email (without @uottawa.ca) : ");

          System.out.print("\u001B[37m"); // reset color

        String j = scan.nextLine();
        if(j.equals("stop")){
          break;
        }
        search(j);
        String sid = searchiD(j);
        String email = searchMail(j);
        String firstname = searchfirst(j);
        String lastname = searchlast(j);
        show(j);

        nbr++;
        tab[i]=j;
        if (sid !=null){
        sb.append(sid);
        sb.append(",");
        sb.append(email);
        sb.append(",");
        sb.append(firstname);
        sb.append(",");
        sb.append(lastname);


        sb.append("\n");
        System.out.println("Ready to ask next student.");

        wait(11);
        System.out.println("Closing protocol. Do not quit.");
        wait(11);
      }



      }

      writer.write(sb.toString());
      }catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
      }

  }

  public static void search(String j){
    boolean found = false;
    try ( BufferedReader infos = new BufferedReader(new FileReader("/Users/adrienheymans/Desktop/uOttawa/Playground/attendance/students3.csv"))) {
      String row="";

      while (( row = infos.readLine()) != null) {
        //System.out.println(row);
        String[] data = row.split(",");
        String test1=  data[7];
        int indexOfat = test1.indexOf("@");
        String test = test1.substring(0,indexOfat);
          if(test.equals(j)){
            found = true;
            System.out.println("Student found in master csv.");
            //wait(11);
            System.out.println("Welcome to the wellness center "+data[1]+" "+data[2]);
            break;
          }
        }

    }catch (IOException e){
      e.printStackTrace();
    }
    if (!found){
      System.out.print("\u001B[37m");
      System.out.println("\u001B[33m"+"WARNING ! Student not found,try again...");
        System.out.print("\u001B[37m"); // reset color
      //wait(11);
    }

  }
  public static String searchiD(String j){
    String str = " ";
    try ( BufferedReader infos = new BufferedReader(new FileReader("/Users/adrienheymans/Desktop/uOttawa/Playground/attendance/students3.csv"))) {
      String row="";
      while (( row = infos.readLine()) != null) {
        //System.out.println(row);
        String[] data = row.split(",");
        String test1=  data[7];
        int indexOfat = test1.indexOf("@");
        String test = test1.substring(0,indexOfat);
          if(test.equals(j)){
            //found = true;
            //wait(11);
            str = data[0];
            return str;
          }
        }
    }catch (IOException e){
      e.printStackTrace();
    }
    //System.out.println(" ! First name not found in master csv.");
    //wait(11);
    return null;
  }

  public static String searchMail(String j){
    String str = " ";
    try ( BufferedReader infos = new BufferedReader(new FileReader("/Users/adrienheymans/Desktop/uOttawa/Playground/attendance/students3.csv"))) {
      String row="";
      while (( row = infos.readLine()) != null) {
        //System.out.println(row);
        String[] data = row.split(",");
        String test1=  data[7];
        int indexOfat = test1.indexOf("@");
        String test = test1.substring(0,indexOfat);
          if(test.equals(j)){
            //found = true;
            //wait(11);
            str = data[7];
            return str;
          }
        }
    }catch (IOException e){
      e.printStackTrace();
    }
    //System.out.println(" ! First name not found in master csv.");
    //wait(11);
    return null;
  }

  public static String searchfirst(String j){
    String str = " ";
    try ( BufferedReader infos = new BufferedReader(new FileReader("/Users/adrienheymans/Desktop/uOttawa/Playground/attendance/students3.csv"))) {
      String row="";
      while (( row = infos.readLine()) != null) {
        //System.out.println(row);
        String[] data = row.split(",");
        String test1=  data[7];
        int indexOfat = test1.indexOf("@");
        String test = test1.substring(0,indexOfat);
          if(test.equals(j)){
            //found = true;
            //wait(11);
            str = data[1];
            return str;
          }
        }
    }catch (IOException e){
      e.printStackTrace();
    }
    //System.out.println(" ! First name not found in master csv.");
    //wait(11);
    return null;
  }

  public static String searchlast(String j){
    String str = " ";
    try ( BufferedReader infos = new BufferedReader(new FileReader("/Users/adrienheymans/Desktop/uOttawa/Playground/attendance/students3.csv"))) {
      String row="";
      while (( row = infos.readLine()) != null) {
        //System.out.println(row);
        String[] data = row.split(",");
        String test1=  data[7];
        int indexOfat = test1.indexOf("@");
        String test = test1.substring(0,indexOfat);
          if(test.equals(j)){
            //found = true;
            System.out.println("LastName found in master csv.");
            //wait(11);
            str = data[2];
            return str;
          }
        }
    }catch (IOException e){
      e.printStackTrace();
    }
    //System.out.println(" ! Last name not found in master csv.");
    //wait(11);
    return null;
  }

  public static void show(String j){
    System.out.println("Now showing the students info...");
    //wait(11);
    String str = " ";
    try ( BufferedReader infos = new BufferedReader(new FileReader("/Users/adrienheymans/Desktop/uOttawa/Playground/attendance/students3.csv"))) {
      String row="";
      while (( row = infos.readLine()) != null) {
        //System.out.println(row);
        String[] data = row.split(",");
        String test1=  data[7];
        int indexOfat = test1.indexOf("@");
        String test = test1.substring(0,indexOfat);
          if(test.equals(j)){

            System.out.println("\u001B[44m");
            System.out.println("-----------------------------------------------");
            System.out.print("\u001B[44m");


            System.out.println("FIRST NAME : "+data[1]);
            System.out.print("\u001B[44m");
            System.out.println("LAST NAME  : "+data[2]);
            System.out.print("\u001B[44m");
            System.out.println("NUMBER     : "+data[0]);
            System.out.print("\u001B[44m");
            System.out.println("RESIDENCE  : "+data[3]);
            System.out.print("\u001B[44m");
            System.out.println("ROOM       : "+data[5]+" "+data[6]);
            System.out.print("\u001B[44m");
            System.out.println("EMAIL      : "+data[7]);
            System.out.print("\u001B[44m");
            System.out.println("FOUND      : YES");
            System.out.print("\u001B[44m");
            System.out.println("-----------------------------------------------");
            System.out.println("\u001B[40m");
            System.out.println("Student added to the attendance sheet.");
          }
        }
    }catch (IOException e){
      e.printStackTrace();
    }

    //wait(11);
    return;
  }

  public static void wait(int s){
    try{
          Thread.sleep(1000);
        }
    catch(InterruptedException ex){
    Thread.currentThread().interrupt();
    }
  }


}
