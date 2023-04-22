import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import DB_beta.maindb;
import DB_beta.maindb.Db_errno;
import Petrol_engine.Petrol;

public class Cli {

    public static void Hire(Boolean[] red) {
        Scanner sc = new Scanner(System.in);
        r: while (true) {
            {
                Boolean elf = false;
                System.out.println();
                System.out.printf("%63s\n", "---Please Enter Your Name---");
                String name = sc.nextLine();
                long ph_no = 0;
                if (Character.isAlphabetic(name.charAt(0)) == false) {
                    System.out.println("Only allowed Characters");
                    elf = true;
                    continue r;
                }
                l: if (elf == false) {
                    System.out.printf("%65s\n", "---Please Enter your Phone_no---");
                    try {
                        ph_no = sc.nextLong();
                        if (Long.toString(ph_no).length() != 10) {
                            System.out.println("Please Enter the phone number in correct format");
                            red[0] = false;
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Please Enter the phone number in correct format");
                        red[0] = false;
                        break;
                    }
                    Cars type = new Cars();
                    System.out.printf("%102s\n",
                            "------------------------------------------------------------------------------------------------");
                    System.out.println();

                    if (type.show_car_status("not_booked") == Db_errno.EE_QUERY_FAILED) {
                        System.out.printf("%77s\n", "Sorry No Cars Are Available Sorry For inconvinience");
                        red[0] = true;
                        break;
                    }

                    System.out.println();
                    System.out.printf("%85s\n", "!!! Please Enter the number plate of Car that you want to book !!!");

                    System.out.println();
                    Boolean breal = true;
                    String numberplate = null;
                    while (breal) {
                        numberplate = sc.next();
                        Petrol utl = new Petrol("Cars.Petrol");
                        var tt = utl.query("no_plate", numberplate, "cust_id");
                        if (tt.size() != 0) {
                            break;
                        }
                        System.out.println("Please Enter the number plate frm the given options available");
                    }

                    System.out.printf("%85s\n", "!!! Please Enter the return date of Car that you want to book !!!");
                    System.out.println();
                    String date = sc.next();

                    Customer cust = new Customer();
                    cust = cust.make_customer(name, ph_no, date);

                    Cars p = new Cars();
                    p.book(cust.id, numberplate, cust.ret_data);
                    red[0] = true;
                    break;
                }
            }
        }
    }

    public static void Interactive_mode() {
        Scanner sc = new Scanner(System.in);
        int cho = 0;

        Boolean breal2 = true;
        while (breal2) {
            System.out.println();
            System.out.printf("%105s\n",
                    "<-------------------------- Welcome to Berjesh Car Hire Company ------------------------------>");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.printf("%45s\n", "Menu");
            System.out.println();
            System.out.printf("%47s\n", "1. Hire Car");
            System.out.printf("%51s\n", "2. Show All Car");
            System.out.printf("%52s\n", "3. Show Customer");
            System.out.printf("%54s\n", "4. Show booked Car");
            System.out.printf("%58s\n", "5. Show non booked Car");
            System.out.printf("%49s\n", "6. Submit Car");
            System.out.println();
            System.out.printf("%85s\n", "Please Select the corresponding options to avail following facilities");
            Cars type = new Cars();
            Boolean red[] = { false };
            cho = sc.nextInt();
            switch (cho) {
                case 1:
                    while (true) {
                        Hire(red);
                        if (red[0] == true) {
                            break;
                        }
                    }
                    break;
                case 2:
                    type.total_Car_detail();
                    System.out.printf("%55s\n", "Press -1 to exit the interface");
                    int i = sc.nextInt();
                    if (i == -1) {
                        breal2 = false;
                    }
                    System.out.println();
                    break;
                case 3:
                    type.total_cust_detail();
                    System.out.println();
                    System.out.println();
                    System.out.printf("%55s\n", "Press -1 to exit the interface");
                    int j = sc.nextInt();
                    if (j == -1) {
                        breal2 = false;
                    }
                    break;
                case 4:
                    type.show_car_status("booked");
                    System.out.println();
                    System.out.println();
                    System.out.printf("%55s\n", "Press -1 to exit the interface");
                    int ai = sc.nextInt();
                    if (ai == -1) {
                        breal2 = false;
                    }
                    break;
                case 5:
                    type.show_car_status("not_booked");
                    System.out.println();
                    System.out.println();
                    System.out.printf("%55s\n", "Press -1 to exit the interface");
                    int aai = sc.nextInt();
                    if (aai == -1) {
                        breal2 = false;
                    }
                    break;
                case 6:
                    int cust_id = 0;
                    System.out.printf("%45s", "Please Enter The Customer Id to be unbooked");
                    cust_id = sc.nextInt();

                    type.un_book(cust_id);

                    System.out.println();
                    System.out.println();

                    System.out.printf("%55s\n", "Press -1 to exit the interface");
                    int aaia = sc.nextInt();
                    if (aaia == -1) {
                        breal2 = false;
                    }
                    break;
                default:
                    break;
            }
        }
        sc.close();
    }

    public static void handle_pquery(String[] args) {
        String name1 = null;
        String name2 = null;
        int id_target = 0;
        ArrayList<String> arr1 = new ArrayList<String>();
        ArrayList<String> brr2 = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--pquery")) {
                if (args[i + 1].equals("id")) {
                    i += 2;
                    id_target = Integer.parseInt(args[i]);
                }
                name1 = args[i + 1];
                int j = i + 2;
                while (!args[j].equals("rel")) {
                    arr1.add(args[j]);
                    i++;
                    j++;
                }
                j++;
                name2 = args[j];
                j++;
                while (j < args.length) {
                    brr2.add(args[j]);
                    i++;
                    j++;
                }
                break;
            }
        }
        StringBuilder b = new StringBuilder(name1).append(".Petrol");
        StringBuilder ab = new StringBuilder(name2).append(".Petrol");
        String newname1 = new String(b);
        String newname2 = new String(ab);

        Cars type = new Cars();
        String[] a1 = arr1.toArray(new String[0]);
        String[] a2 = brr2.toArray(new String[0]);
        type.merge_detail(newname1, newname2, id_target, a1, a2);
    }

    public static void handle_cust_query(String[] args) {
        if (args[0].equals("--querycust")) {
            if (args[1].equals("--all")) {
                maindb db = new maindb();
                try {
                    db.printer("Customer.Petrol");
                } catch (IOException e) {
                    System.out.println("Does Not exit the file");
                }
            }
            if (args[1].equals("-select")) {
                String name1 = args[2];
                String name2 = args[3];
                Petrol pet = new Petrol("Customer.Petrol");
                pet.query(name1, name2);
            }
        }

    }

    public static void main(String[] args) {
        if (args[0].equals("-inn")) {
            try {
                Interactive_mode();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.printf("%97s\n",
                        "---Expected Integer type found unexpected type please fill carefully---");
                System.out.println();
                System.out.println();
                System.out.println();
                Interactive_mode();
            }
        }else{
            if(args[0].equals("--pquery")){
                handle_pquery(args);
            }
            else if(args[0].equals("--querycust")){
                handle_cust_query(args);
            }
        }
    }
}