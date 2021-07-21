import java.sql.*;
import java.util.*;
public class Lab4 {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/lab4?characterEncoding=latin1";

    //  Database credentials
    static final String USER = ""; // removed for privacy reasons
    static final String PASS = ""; // removed for privacy reasons

    public static void main(String[] args){
        Connection conn = null;
        Statement stmt = null;
        Scanner keyboard = new Scanner(System.in);

        try{
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
            boolean loop = true;
            do {
                System.out.println("You now have access to the Car Inventory Database. Please Select an option from our list of features or type 0 to exit");
                System.out.println("1. Display Schedule");
                System.out.println("2. Edit Schedule");
                System.out.println("3. Display Stops");
                System.out.println("4. Display Weekly Schedule");
                System.out.println("5. Add Driver");
                System.out.println("6. Add Bus");
                System.out.println("7. Delete Bus");
                System.out.println("8. Record Actual Data");

                //Choose Vehicle Selection
                int selection = keyboard.nextInt();

                if (selection == 0) {
                    System.exit(0);
                }
                switch (selection) {
                    case 1:
                        System.out.print("Enter Start Name: ");
                        keyboard.nextLine();
                        String startName = keyboard.nextLine();
                        System.out.println();
                        System.out.print("Enter Destination Name: ");
                        String endName = keyboard.nextLine();
                        System.out.println();
                        System.out.print("Enter Date: ");
                        String date = keyboard.next();
                        String sql1 = "SELECT StartLocationName, DestinationName, TripDate, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID FROM Trip, TripOffering WHERE StartLocationName = '" + startName + "' AND DestinationName = '" + endName + "' AND TripDate = '" + date + "' AND Trip.TripNumber = TripOffering.TripNumber;";
                        stmt = conn.createStatement();
                        ResultSet rs1 = stmt.executeQuery(sql1);
                        while(rs1.next()){
                            String start = rs1.getString("StartLocationName");
                            String destination = rs1.getString("DestinationName");
                            String tdate = rs1.getString("TripDate");
                            String stime = rs1.getString("ScheduledStartTime");
                            String atime = rs1.getString("ScheduledArrivalTime");
                            String dname = rs1.getString("DriverName");
                            int bid = rs1.getInt("BusID");

                            System.out.print("Start Name: " + start + ", Destination Name: " + destination + ", Trip Date: " + tdate + ", Scheduled Start Time: "
                                    + stime + ", Scheduled Arrival Time: " + atime + ", Driver Name: " + dname + ", BusID: " + bid + "\n");
                        }
                        rs1.close();
                        break;

                    case 2:
                        System.out.println("1. Delete Trip");
                        System.out.println("2. Insert Trip");
                        System.out.println("3. Change Trip Driver");
                        System.out.println("4. Change Trip Bus");
                        int choice = keyboard.nextInt();
                        String sql2 ="";
                        if(choice == 1) {
                            System.out.print("Enter Trip Number: ");
                            int tnum = keyboard.nextInt();
                            System.out.print("Enter Date: ");
                            String date2 = keyboard.next();
                            System.out.print("Enter Start Time: ");
                            String stime = keyboard.next();

                            stmt = conn.createStatement();
                            sql2 = "DELETE FROM TripOffering"
                                    + " WHERE TripNumber = " + tnum + " AND TripDate = '" + date2 + "' AND ScheduledStartTime = '" + stime + "';";
                            stmt.executeUpdate(sql2);
                        }
                        else if(choice == 2){
                            boolean done = true;
                            do {
                                System.out.print("Enter Trip Number: ");
                                int tnum = keyboard.nextInt();
                                System.out.print("Enter Date: ");
                                String date2 = keyboard.next();
                                System.out.print("Enter Start Time: ");
                                String stime = keyboard.next();
                                System.out.print("Enter Arrival Time: ");
                                String atime = keyboard.next();
                                System.out.print("Enter Driver Name: ");
                                keyboard.nextLine();
                                String dname = keyboard.nextLine();
                                System.out.print("Enter BusID: ");
                                int bid = keyboard.nextInt();

                                sql2 = "INSERT INTO TripOffering VALUES (" + tnum + ", '" + date2 + "', '" + stime + "', '" + atime + "', '" + dname + "'," + bid + ")";
                                stmt = conn.createStatement();
                                stmt.executeUpdate(sql2);
                                System.out.println("if not done enter 0");
                                int check = keyboard.nextInt();
                                if(check == 0)
                                    done = false;
                            }while(!done);
                        }
                        else if(choice == 3){
                            System.out.print("Enter Trip Number: ");
                            int tnum = keyboard.nextInt();
                            System.out.print("Enter Date: ");
                            String date2 = keyboard.next();
                            System.out.print("Enter Start Time: ");
                            String stime = keyboard.next();
                            System.out.print("Enter new Driver Name: ");
                            keyboard.nextLine();
                            String dname = keyboard.nextLine();

                            sql2 = "UPDATE TripOffering" +
                                    " SET DriverName = '" + dname +
                                    "' WHERE TripNumber = " + tnum + " AND TripDate = '" + date2 + "' AND ScheduledStartTime = '" + stime + "';";
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql2);

                        }
                        else if(choice == 4){
                            System.out.print("Enter Trip Number: ");
                            int tnum = keyboard.nextInt();
                            System.out.print("Enter Date: ");
                            String date2 = keyboard.next();
                            System.out.print("Enter Start Time: ");
                            String stime = keyboard.next();
                            System.out.print("Enter new BusID: ");
                            keyboard.nextLine();
                            int bid2 = keyboard.nextInt();

                            sql2 = "UPDATE TripOffering " +
                                    "SET BusID = "+ bid2 +
                                    " WHERE TripNumber = "+tnum+" AND TripDate = '"+date2+"' AND ScheduledStartTime = '" + stime + "';";
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql2);
                        }
                        break;
                    case 3:
                        System.out.print("Enter Trip Number: ");
                        int tnum = keyboard.nextInt();
                        String sql3 = "SELECT TripStopInfo.TripNumber, StopNumber, SequenceNumber, DrivingTime FROM TripStopInfo, Trip WHERE Trip.TripNumber = " + tnum + " AND Trip.TripNumber = TripStopInfo.TripNumber";
                        stmt = conn.createStatement();
                        ResultSet rs3 = stmt.executeQuery(sql3);
                        while(rs3.next()){
                            int tripNum = rs3.getInt("TripNumber");
                            int stopNum = rs3.getInt("StopNumber");
                            int snum = rs3.getInt("SequenceNumber");
                            int driveTime = rs3.getInt("DrivingTime");

                            System.out.print("Trip Number: " + tripNum + ", Stop Number: " + stopNum + ", SequenceNumber: " + snum + ", driving time: " + driveTime + "\n");
                        }
                        rs3.close();
                        break;
                    case 4:
                        System.out.print("Enter Driver Name: ");
                        keyboard.nextLine();
                        String dname = keyboard.nextLine();
                        System.out.print("Enter Date: ");
                        String date4 = keyboard.next();
                        String sql4 = "SELECT*FROM TripOffering WHERE TripDate = '" + date4 + "' " +
                                "AND DriverName = '" + dname + "';";
                        stmt = conn.createStatement();
                        ResultSet rs4 = stmt.executeQuery(sql4);
                        while(rs4.next()){
                            int tripNum = rs4.getInt("TripNumber");
                            String tdate = rs4.getString("TripDate");
                            String stime = rs4.getString("ScheduledStartTime");
                            String atime = rs4.getString("ScheduledArrivalTime");
                            dname = rs4.getString("DriverName");
                            int bid = rs4.getInt("BusID");

                            System.out.print("TripNumber: " + tripNum + ", Trip Date: " + tdate + ", Scheduled Start Time: "
                                    + stime + ", Scheduled Arrival Time: " + atime + ", Driver Name: " + dname + ", BusID: " + bid + "\n");
                        }
                        rs4.close();
                        break;
                    case 5:
                        System.out.print("Enter new driver name: ");
                        keyboard.nextLine();
                        String dname5 = keyboard.nextLine();
                        System.out.print("Enter new driver phone number: ");
                        String pnum = keyboard.next();

                        String sql5 = "INSERT INTO Driver VALUES ('" + dname5 + "', '" + pnum + "');";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql5);
                        break;
                    case 6:
                        System.out.print("Enter new BusID: ");
                        int bid = keyboard.nextInt();
                        System.out.print("Enter new Bud Model: ");
                        String bmodel = keyboard.next();
                        System.out.print("Enter new Bus Year: ");
                        int year = keyboard.nextInt();

                        String sql6 = "INSERT INTO Bus VALUES (" + bid + ", '" + bmodel + "', " + year + ");";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql6);
                        break;
                    case 7:
                        System.out.print("Enter ID of Bus you want to delete: ");
                        int bid7 = keyboard.nextInt();

                        String sql7 = "DELETE FROM Bus WHERE BusID = " + bid7 + ";";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql7);
                        break;
                    case 8:
                        System.out.print("Enter Trip Number: ");
                        int tnum8 = keyboard.nextInt();
                        System.out.print("Enter Date: ");
                        String d8 = keyboard.next();
                        System.out.print("Enter Stop Number: ");
                        int snum = keyboard.nextInt();
                        System.out.print("Enter actual start time: ");
                        String ast = keyboard.next();
                        System.out.print("Enter actual arrival time: ");
                        String aat = keyboard.next();
                        System.out.print("Enter # of passengers in: ");
                        int passIn = keyboard.nextInt();
                        System.out.print("Enter # of passengers out: ");
                        int passOut = keyboard.nextInt();

                        String sql8 = "INSERT INTO ActualTripStopInfo VALUES(" + tnum8 + ", '" + d8 + "', " + snum + ", '" + ast + "', '" + aat + "', " + passIn + ", " + passOut + ");";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql8);
                        break;
                }
                int stop;
                do {
                    System.out.println("To Continue using the program enter 1, to stop enter 0");
                    stop = keyboard.nextInt();
                }while(stop < 0 || stop > 1);
                if(stop == 0)
                    loop = false;

            }while(loop);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
}
