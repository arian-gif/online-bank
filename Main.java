// import libraries
import java.util.*;
import java.io.*;

public class Main { // start class

  // method used for printing something in a different colour
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static final String BLACK_BOLD = "\033[1;30m";  
  public static final String RED_BOLD = "\033[1;31m";    
  public static final String GREEN_BOLD = "\033[1;32m";  
  public static final String YELLOW_BOLD = "\033[1;33m";
  public static final String BLUE_BOLD = "\033[1;34m";  
  public static final String PURPLE_BOLD = "\033[1;35m";
  public static final String CYAN_BOLD = "\033[1;36m";  
  public static final String WHITE_BOLD = "\033[1;37m";  
  public static final String BRIGHT_YELLOW = "\u001b[33;1m";

  // Arraylists to contain all the file contents
  public static ArrayList<String> names = new ArrayList<String>();
  public static ArrayList<String> pass = new ArrayList<String>();
  public static ArrayList<Integer> cheqNums = new ArrayList<Integer>();
  public static ArrayList<Integer> savingNums = new ArrayList<Integer>();
  public static ArrayList<Double> cheqBalance = new ArrayList<Double>();
  public static ArrayList<Double> savingBalance = new ArrayList<Double>();

  public static Scanner in = new Scanner(System.in);

  public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException { // start main
    menu();//uses menu method to see what user wants to do
  } // end main

  // login method
  public static void menu() throws IOException, FileNotFoundException, ClassNotFoundException{
    asciiTitle();
    int choice;
    String reply = "";

    //print menu
    do{
      System.out.println(GREEN_BOLD + "1. Login to account\n" + YELLOW_BOLD + "2. Create account\n" + ANSI_RESET + ANSI_RED + "3. Exit\n"  + ANSI_RESET + "\nEnter Choice:");
      choice = in.nextInt();
    } while(choice <1 || choice >3);//if user inputs invalid values

    in.nextLine();//clears buffer

    if(choice==1){
      login();//login method

    }
    else if(choice==2){
      create();//if user wants to create an account
      do{
        System.out.println("Do you want to login now");
        reply = in.nextLine();
        reply = reply.toLowerCase();
      } while(!reply.equals("yes") && !reply.equals("no"));
      if(reply.equals("yes")){
        login();//if user wants to log into their newly created account
      }
      else{
        goodBye();//if user just wants to leave
      }

    }
    else{
      goodBye();//if user wants to exit
    }
  }


  public static void login() throws IOException{
    System.out.print("\033[H\033[2J");  //clears screen
    System.out.flush();

    String username, password;
    int index =0;
    boolean check;

    reading();

    do{
      int postion=-1;//starts with postion at -1
      System.out.print("\nEnter your username:\n");
      username= in.nextLine();
      for(int i =0;i<names.size();i++){
        if(names.get(i).equals(username)){
          postion =i;//postion becomes index
          break;
        }

      }
      System.out.print("\nEnter your password:\n");
      password = in.nextLine();
      if(postion ==-1){//if postion is still -1, the condition is not met
        check = false;
      }
      else{

         if(pass.get(postion).equals(password)){//if its the same username and password
          check = true;
          index=postion;
        }
        else{
          check =false;
        }
      }


      if(!check){
        System.out.println(ANSI_RED+"You have an invalid username or password"+ANSI_RESET);//prints the invalid data
      }
      else{
        System.out.println(ANSI_GREEN+"logging in................."+ANSI_RESET);
      }
    }while(!check);//if check is false, reruns
    double saveBal = savingBalance.get(index);
    double cheqBal = cheqBalance.get(index);

    System.out.print("\033[H\033[2J");  
    System.out.flush();
    Account(username,cheqBal,saveBal,index);//now goes to account method, where u can see what to do

  } // end login

public static void Account(String username, double cheqBal,double saveBal,int index)throws IOException{
  String replay;
  System.out.println(WHITE_BOLD + "User: " + username + ANSI_RESET);
  do{
    int option;
    String secondUser,date="";
      do{

        System.out.println(CYAN_BOLD +"               Options:\n" + GREEN_BOLD + "1. Deposit\n" + RED_BOLD + "2. Withdraw\n" + BLUE_BOLD + "3. E-transfer\n" + ANSI_RESET + BRIGHT_YELLOW + "4. Check Saving Accounts\n5. Check Chequing account\n" + PURPLE_BOLD + "6. View transactions\n" + ANSI_RESET);
        System.out.println("Enter option: ");//prints options
        option = in.nextInt();
        if(option<1||option>6){
          System.out.println(ANSI_RED+"Invalid Option"+ANSI_RESET);//if its an invalid option
        }
      }while(option<1||option>6);

    System.out.print("\033[H\033[2J");  
    System.out.flush();

    if(option ==1||option==2||option==3){
      date += date();//gets date of transaction
    }
    System.out.print("\033[H\033[2J");  
    System.out.flush();
  if(option ==1){
    double deposit;
    do{
     System.out.println(GREEN_BOLD + "How much do you want to deposit?: " + ANSI_RESET);
     deposit = in.nextDouble(); //asks for depostit amount
      if(deposit<0){
        System.out.println(ANSI_RED+"deposit must be positive"+ANSI_RESET);//can't be negative values
      }
    }while(deposit<0);

    deposit = (Math.round(deposit*100.0))/100.0;
    updateFile(username,deposit,0.0,"");
    System.out.print("\033[H\033[2J");  
    System.out.flush();
    history(username,date,"deposit","",deposit,"");
  }
  else if(option==2){
    double withdraw;
    do{
      System.out.print("How much do you want to withdraw?: " + ANSI_RESET);//asks for withdraw amount
      withdraw = in.nextDouble();
      if(withdraw<0){
        System.out.println(ANSI_RED+"Withdraw must be positive"+ANSI_RESET);//can't be negative
      }
    }while(withdraw<0);

    withdraw = (Math.round(withdraw*100.0))/100.0;
    updateFile(username,0.0,withdraw,"");
    System.out.print("\033[H\033[2J");  
    System.out.flush();
    history(username,date,"withdraw","",withdraw,"");
  }
  else if(option == 3){
    boolean check;
    in.nextLine();//clears the buffer
    do{
    check=false;
    System.out.println("who would you like to e-transfer to:");
    secondUser = in.nextLine();//asks user who they want to e transfer too
    for(int i=0; i<names.size();i++){
      if(names.get(i).equals(secondUser)){//checks if user exists
        check =true;
        break;
      }
    }
      if(!check){
        System.out.println(ANSI_RED+"This user does not exist"+ANSI_RESET);//if user doesn't exist
      }
    }while(!check);
    double transfer;
    do{
       System.out.println("How much money are you transferring to " + secondUser);//asks how much they are transferring to second user
       transfer = in.nextDouble();
      if(transfer<0){
        System.out.println(ANSI_RED+"transfer must be positive"+ANSI_RESET);
      }
    }while(transfer<0);


      transfer = (Math.round(transfer*100.0))/100.0;

      updateFile(username,0.0,transfer,"");
      history(username,date,"transfer",secondUser,transfer,"");
      updateFile(secondUser,transfer,0.0,"transfer");
      history(secondUser,date,"receive",username,transfer,"");
    }

    else if(option==4){
      savingsAccount(username,index);
    }
    else if(option==5){
      chequingAccount(username,index);
    }
    else{
      printHistory(username);
      in.nextLine();
    }
      do{
        System.out.println("\nDo you want to do anymore transactions (enter yes or no): ");//if user wants to keep making transactions
        replay = in.nextLine();
        replay = replay.toLowerCase();
      }while(!replay.equals("yes")&&!replay.equals("no"));//makes sure right data is given
    }while(replay.equals("yes"));

    if(replay.equals("no")){
      goodBye();
    }

  }

  // chequingAccount method
  public static void chequingAccount(String username, int index)throws IOException{

    // initialize variables
    double amount;
    int choice;

    // get money and account number from file
    double money = cheqBalance.get(index);
    int accountNum = cheqNums.get(index);

    // show chequing information and options
    System.out.println(BLUE_BOLD + "Welcome to your Chequing Account\n\n" + CYAN_BOLD + "Account number: " + ANSI_RESET + accountNum + CYAN_BOLD + "\nAccount Balance: " + ANSI_RESET + "$" + money);
    do{
      System.out.println(RED_BOLD + "\nOptions:\n" + BRIGHT_YELLOW + "1. Want to transfer money to savings account\n2. Pay Bills\n" + ANSI_RESET + "\nEnter choice:");  
      choice = in.nextInt();// get sure input
      if(choice >2 || choice <1){
        System.out.println("Invalid choice");
      }
    }while(choice>2||choice<1);




    // clear screen
    System.out.print("\033[H\033[2J");  
    System.out.flush();

    // transfer if choice is 1
    if(choice ==1){
      do{
       System.out.println("How much would you like to transfer to saving account (can't give negative amount)");
        amount = in.nextDouble();
      }while(amount<0);
      in.nextLine();//clears the buffer

      if(amount>money){
        System.out.println(ANSI_RED+"You can't make this transaction"+ANSI_RESET);
      }
      else{
       transferAccount(username,"saving",amount);
      }
    }

    // else pay bills
    else{
      String date = date();
      String bill;
      double billCost;
      System.out.println("What bill would you like to pay:");
      bill = in.nextLine();
      do{
        System.out.println("How much does this bill cost");
        billCost = in.nextDouble();
      }while(billCost<0);
      billCost = (Math.round(billCost*100.0))/100.0;
      in.nextLine();//clears the buffer
      updateFile(username,0.0,billCost,"bills");
      history(username, date, "bills", "", billCost, bill);
    }
  } // end chequingAccount method

  // savingsAccount method
  public static void savingsAccount(String username,int index)throws IOException{

    // initialize variables
    double amount;
    int choice;

    // get money and account number from file
    double money = savingBalance.get(index);
    int accountNum = savingNums.get(index);

    // show account information
    System.out.println(BLUE_BOLD + "Welcome to your Savings Account\n\n" + CYAN_BOLD + "Account number: " + ANSI_RESET + accountNum + CYAN_BOLD + "\nAccount Balance: " + ANSI_RESET + "$" + money);

    // show options
    do{
      System.out.println(RED_BOLD + "\nOptions:\n" + BRIGHT_YELLOW + "1. Transfer money to chequing account\n2. Check interest accured\n\n" + ANSI_RESET + "Enter choice:");
      choice = in.nextInt();
    }while(choice>2||choice<1);

    // clear screen
    System.out.print("\033[H\033[2J");  
    System.out.flush();

    // transfer if choice is 1
    if(choice ==1){
      do{
        System.out.println("How much would you like to transfer to chequing account: (can't give negative amount)");
        amount = in.nextDouble();
      }while(amount<0);
      in.nextLine();//clears the buffer
      if(amount > money){
        System.out.println("You can't make this transaction");
      }
      else{
        transferAccount(username, "chequing", amount);
      }
    }

    // else do interest option
    else{
      //interest rate is 1.2%

      // initizalize variables
      int years, months, totalMonths;

      // ask how many years they want to simulate
      do{
        System.out.println("Input how many years you want to see of interest accrued (enter 0 if you want to see just months): ");
        years = in.nextInt();
        if(years<0){
          System.out.println(ANSI_RED+"this value must be positve"+ANSI_RESET);
        }
      }while(years<0);

      // ask how many months they want to simulate
      do{
        System.out.println("Input how many months you want to see of interest accrued: ");
        months = in.nextInt();
        if(months<0){
          System.out.println(ANSI_RED+"this value must be positve"+ANSI_RESET);
        }
      }while(months<0);
      in.nextLine();//clears the buffer
      // convert to months
      totalMonths = (years*12) + months;

      // calculate interest accrued
      for(int i = 0; i < totalMonths; i++){  
        double interest = money*0.012;
        money += interest;
      }
      money = (Math.round(money*100.0))/100.0;

      // show interest accrued to user
      System.out.println("\nYour total balance in " + years + " years and " + months + " month(s) with interest accrued would be $" + money);
    }
  }
  public static String date(){
    //gets date
    String date ="";
    int year,month,day;
    boolean checkDays;
    do{
      System.out.println(BLUE_BOLD + "What is the date you are doing this transaction (transaction must be between 2010 to present)?\n");
      System.out.print(RED_BOLD + "Year: ");
      year = in.nextInt();
    }while(year<2010||year>2022);//makes sure right inputs are given
      do{
       System.out.print(CYAN_BOLD + "Month (respond with the number of month. For example, March--->03): ");
       month = in.nextInt();
      }while(month<1||month>12);//makes sure right inputs are given
      do{
       System.out.print(BRIGHT_YELLOW + "Day: ");
       day = in.nextInt();
        if(day<0){
          checkDays = false;
          System.out.println("You must enter positive values");//makes sure right inputs are given
        }
        else{
          checkDays = checkDay(month,day,year);
          if(!checkDays){
            System.out.println("You entered too many days");//makes sure right inputs are given
          }
        }
      }while(!checkDays);

      date += Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
    return date;
  }
  public static boolean checkDay(int month, int days,int year){
    boolean leapYear = ifLeapYear(year);//if its leap year, feb would have 29 days and not 28
    if(month==4||month==6||month==9||month==11){//months with only 30 days
      if(days>30){//if user inputs more days than 30
        return false;
      }
      else {
        return true;
      }
    }
    else if(month==2&&leapYear){
      if(days>29){//if its leapyear and days inputted is greater than 29
        return false;
      }
      else {
        return true;
      }
    }
    else if(month == 2 && !leapYear){
      if(days>28){//if its not leapyear and days inputted is greater than 28
        return false;
      }
      else {
        return true;
      }
    }
    else{//every other month has 31 days
      if(days>31){// if days given is greater than 31
        return false;
      }
      else {
        return true;
      }
    }
  }
  public static boolean ifLeapYear(int year){//start leapyear method
        if ((year % 400 ==0) || (year % 4 == 0 && year %100 != 0) ){
          return true;
        }

        else{
          return false;
        }

    }//end ifLeapYear

  // create method
  public static void create(){
    System.out.print("\033[H\033[2J");  
    System.out.flush();

    // initialize variables
    String username, password, confirmPass;
    boolean right,check;

    // create account until passwords match
    do{

      // ask for username
      do{
        check=false;

        // ask user to enter username
        System.out.print("\nEnter your username (must be atleast 3 characters long): ");
        username = in.nextLine();

        // read from file and save into arraylists to access information
        reading();

        // check if username user inputted already exists
        for(int i =0; i < names.size(); i++){
          if(names.get(i).equals(username)){
            check =true;
            System.out.println(ANSI_RED+"This username already exists"+ANSI_RESET);
            break;
          }
        }
        if(username.length()<3){
          System.out.println(ANSI_RED+"Your username must be atleast 3 characters long"+ANSI_RESET);
          check =true;//must
        }
        if(username.equals("accounts")){
          System.out.println(ANSI_RED+"Your username can't be accounts"+ANSI_RESET);//every username is turned into a file, so having 2 accounts.txt would be confusing
          check=true;
        }
      }while(check);

      // ask for password
      System.out.print("Enter your password: ");
      password = in.nextLine();

      // confirm password
      System.out.print("Please confirm password: ");
      confirmPass= in.nextLine();

      // check if passwords match
      if(password.equals(confirmPass)){
        right = true;
      }
      else{
        right = false;
        System.out.println(ANSI_RED+"Passwords do not match"+ANSI_RESET);
      }

    }while(!right);

    // clear screen
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
    System.out.println();

    // show chequing account is being created
    System.out.println(ANSI_GREEN+"Creating Chequing Account and Savings Account...."+ANSI_RESET);

    // initialize variables
    int cheqNum,saveNum;
    boolean same, same2;

    // create unique chequing account number
    do{
      same = false;
      cheqNum = (int)(Math.random()*(9999-1000))+1000; //creates a 4 digit code, using the math.random range formula
      for(int i =0;i<cheqNums.size();i++){
        if(cheqNums.get(i)==cheqNum){
          same = true;
        }
      }
    }while(same);

    // create unique savings account number
    do{
      same2= false;
      saveNum = (int)(Math.random()*(999-100))+100; //creates a 3 digit savings account number
      for(int i = 0;i<savingNums.size();i++){
        if(savingNums.get(i)==saveNum){
          same2= true;
        }
      }
    }while(same2);

    // show the account numbers
    System.out.println("\n");
    System.out.println("You saving account number is "+saveNum);
    System.out.println("You cheqing account number is "+cheqNum);
    System.out.println(ANSI_GREEN+"Your account has been registered"+ANSI_RESET);


    double startChequing = 60; //anyone that uses our bank gets a free 60$ to start
    double startSaving = 0; // savings accounts starts with 0

    // adds account to accounts file
    try(FileWriter fw = new FileWriter("accounts.txt", true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter out = new PrintWriter(bw)){
        out.println(username + "," + password + "," + cheqNum + "," + saveNum +"," + startChequing + "," + startSaving);
    } catch (IOException e) {
      System.out.println("There is an error");
    }

    // create a new file for the user to show transactions when transactions are made
    createFile(username);

  } // end create method

  // createFile method
  public static void createFile(String userName){

    // make new file with user's name
    try {
      File myFile = new File(userName +".txt");
      myFile.createNewFile();
    }
     catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  // reading method
  public static void reading(){

    // read from accounts file
    try{
      File myFile = new File("accounts.txt");
      Scanner myFileReader= new Scanner(myFile);

      // runs until data has no next lines
      while (myFileReader.hasNextLine()) { // start while loop
        String comma = myFileReader.nextLine();
        String [] data = comma.split(",");

        // add information to the arraylists
        names.add(data[0]);
        pass.add(data[1]);
        cheqNums.add(Integer.parseInt(data[2]));
        savingNums.add(Integer.parseInt(data[3]));
        cheqBalance.add(Double.parseDouble(data[4]));
        savingBalance.add(Double.parseDouble(data[5]));
      }

      myFileReader.close();
    }
    catch (FileNotFoundException e){ // if there is an error
      System.out.println("Error:");
      System.out.println("Your file does not exist");
    }
  }

  // updateFile method
  public static void updateFile(String user,double deposit,double withdraw,String mode) throws IOException{
    String account = "";

    if(mode.equals("transfer")||mode.equals("bills")){//these 2 modes automatically using the chequing account
      account.equals("chequing");
    }
    else{
      in.nextLine();
    do{
      //asks user if they want to use chequing or savings account
      System.out.println(YELLOW_BOLD + "\nDo you want to use your chequing or saving account (reply with chequing or saving): " + ANSI_RESET);
      account = in.nextLine();
      account = account.toLowerCase();
    }while(!account.equals("saving") && !account.equals("chequing"));//makes sure right data is given
    }
    try{

      File myFile = new File("accounts.txt");
      Scanner myFileReader= new Scanner(myFile);
      String fileContents = "";
      double cheqTotal, savingTotal;

      while(myFileReader.hasNextLine()){
        String line = myFileReader.nextLine();
        String[] data = line.split(",");
        if(data[0].equals(user)){//if its the right username
          if(account.equals("saving")){//changes the amount in savings account
            savingTotal = Double.parseDouble(data[5]);
            if(withdraw > savingTotal){//if the transaction is greater than amount in bank account
              System.out.println("You are broke, you can't make this transaction");
            }
            else{
              savingTotal += deposit - withdraw;
              data[5]=Double.toString(savingTotal);
            }
          }
          else{//uses saving account
            cheqTotal = Double.parseDouble(data[4]);
            if(withdraw > cheqTotal){//if transaction is greater than amount in bank account
              System.out.println("You are broke, you can't make this transaction");
            }
            else{
              cheqTotal += deposit - withdraw; //parse double
              data[4] = Double.toString(cheqTotal);
            }
          }
        }


        fileContents += data[0]+","+data[1]+","+data[2]+","+data[3]+","+data[4]+","+data[5]+"\n";//the filcontents stores the whole file and replaces data we want replaced
      }

      FileOutputStream fileOut = new FileOutputStream(myFile);
      fileOut.write(fileContents.getBytes());//writes filecontents
      fileOut.close();
      myFileReader.close();
      if(mode.equals("transfer")){
        System.out.println(ANSI_GREEN+"\nYou have successfully transfered "+ deposit+" to "+user+ANSI_RESET);
      }
    }

    catch (FileNotFoundException e){ //if there is an error
      System.out.println("Error:");
      System.out.println("Your file does not exist");
    }
  } // end updateFile

  // transferAccount method
  public static void transferAccount(String username,String mode,double amount)throws IOException{

    // read from accounts file
    try{
      File myFile = new File("accounts.txt");
      Scanner myFileReader= new Scanner(myFile);
      String fileContents = "";
      while(myFileReader.hasNextLine()){
        String line = myFileReader.nextLine();
        String[] data = line.split(",");

        // once it reaches user's file line
        if(data[0].equals(username)){

          // transfer from chequing to saving
          if(mode.equals("saving")){ //chequing to saving
            double cheq = Double.parseDouble(data[4]); //chequing account amount
            double save = Double.parseDouble(data[5]); //saving account amount
            cheq -= amount;
            save += amount;

            // change the chequing and saving balance part of user's file line
            data[4] = Double.toString(cheq);
            data[5] = Double.toString(save);
          }

          // transfer from saving to chequing
          else if(mode.equals("chequing")){//chequing to saving
            double cheq = Double.parseDouble(data[4]);//chequing account amount
            double save = Double.parseDouble(data[5]);//saving account amount
            cheq += amount;
            save -= amount;

            // change the chequing and saving balance part of user's file line
            data[4] = Double.toString(cheq);
            data[5] = Double.toString(save);
          }
        }

        // rewrite each file line with updated data
        fileContents += data[0]+"," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5] + "\n";
      }

      // output to file
      FileOutputStream fileOut = new FileOutputStream(myFile);
      fileOut.write(fileContents.getBytes());
      fileOut.close();
      myFileReader.close();
    }
    catch (FileNotFoundException e){//if there is an error
      System.out.println("Error:");
      System.out.println("Your file does not exist");
    }
  }

  // history method
  public static void history(String user,String date, String type,String secondUser,double amount,String bills) throws IOException {

    // access user's transaction file
    try{
      String file = "";//gets user's file
      file += user + ".txt";
      File myFile = new File(file);
      Scanner myFileReader = new Scanner(myFile);
      // StringBuffer buffer = new StringBuffer();
      String fileContents = "";

      // writes they deposited
      if(type.equals("deposit")){
        fileContents += "You deposited $"+Double.toString(amount)+" on "+date;
      }

      // writes they withdrew
      else if(type.equals("withdraw")){
        fileContents += "You withdrew $"+Double.toString(amount)+" on "+date;
      }

      // writes they transferred
      else if(type.equals("transfer")){
        fileContents += "You e-transferred $" + Double.toString(amount) + " to " + secondUser + " on " + date;
      }

      // writes who received money that they received it
      else if(type.equals("receive")){
        fileContents += "You received $" + Double.toString(amount) + " from " + secondUser + " on " + date;
      }
      //writes user paid bills  
      else if(type.equals("bills")){
        fileContents +="You paid $"+Double.toString(amount)+" for "+bills+" on "+date;
      }

      // add to transaction file
      try(FileWriter fw = new FileWriter(file, true);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter out = new PrintWriter(bw))
      {
        out.println(fileContents);//writes to users file
      } catch (IOException e) {
        System.out.println("There is an error");
      }
    }
    catch (FileNotFoundException e){//if there is an error
      System.out.println("Error:");
      System.out.println("Your file does not exist");
    }
  }

  // printHistory method
  public static void printHistory(String username){
    String file= "";
    file+=username+".txt";

    // print user's transaction history
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);//prints users file
      }
    }

    catch (IOException e){ //if there is an error
      System.out.println("Error:");
      System.out.println("Your file does not exist");
    }
  }

  public static void goodBye(){ // start goodbye method
    System.out.println(ANSI_RED+"\n***************************************");
    System.out.println("****            Thanks             ****");
    System.out.println("****          For Using :)         ****");
    System.out.println("***************************************"+ANSI_RESET);
  } // end goodbye method

  public static void asciiTitle(){ // title for bank name: goop banking
    System.out.print("\033[H\033[2J");  
    System.out.flush();
    System.out.println(ANSI_CYAN+"░██████╗░░█████╗░░█████╗░██████╗░  ██████╗░░█████╗░███╗░░██╗██╗░░██╗██╗███╗░░██╗░██████╗░");
    System.out.println("██╔════╝░██╔══██╗██╔══██╗██╔══██╗  ██╔══██╗██╔══██╗████╗░██║██║░██╔╝██║████╗░██║██╔════╝░");
    System.out.println("██║░░██╗░██║░░██║██║░░██║██████╔╝  ██████╦╝███████║██╔██╗██║█████═╝░██║██╔██╗██║██║░░██╗░");
    System.out.println("██║░░╚██╗██║░░██║██║░░██║██╔═══╝░  ██╔══██╗██╔══██║██║╚████║██╔═██╗░██║██║╚████║██║░░╚██╗");
    System.out.println("╚██████╔╝╚█████╔╝╚█████╔╝██║░░░░░  ██████╦╝██║░░██║██║░╚███║██║░╚██╗██║██║░╚███║╚██████╔╝");
    System.out.println("░╚═════╝░░╚════╝░░╚════╝░╚═╝░░░░░  ╚═════╝░╚═╝░░╚═╝╚═╝░░╚══╝╚═╝░░╚═╝╚═╝╚═╝░░╚══╝░╚═════╝░\n"+ANSI_RESET);
  }


} // end class