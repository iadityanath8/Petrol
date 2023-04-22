import java.io.IOException;

import DB_beta.maindb;
import DB_beta.maindb.Db_errno;
import Petrol_engine.Petrol;
import java.util.ArrayList;

public class Cars {
    public int cust_id;
    public String car_name;
    public String booking_date;
    public String status;
    public String no_plate;

    Cars() {
    }

    Cars(int c_id, String car_name, String bd, String st, String no_plate) {
        this.booking_date = bd;
        this.cust_id = c_id;
        this.car_name = car_name;
        this.status = st;
        this.no_plate = no_plate;
    }

    public void total_Car_detail() {
        maindb writer = new maindb();
        try {
            writer.printer("Cars.Petrol");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Db_errno show_car_status(String flag) {
        Petrol p = new Petrol("Cars.Petrol");
        maindb __db_insec = new maindb();
        try {
            __db_insec.printer_f_col(p._file_dbname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var t = p.query("status", flag);
        if (t == Db_errno.EE_QUERY_FAILED && flag.equals("booked")) {
            System.out.println("No cars are booked till date");
        }
        if (t == Db_errno.EE_QUERY_FAILED && flag.equals("not_booked")) {
            System.out.println("all cars are booked till date");
        }
        return t;
    }

    public void pretty_print(ArrayList<ArrayList<ArrayList<String>>> p1, ArrayList<String> n) {
        for (int i = 0; i < p1.size(); i++) { // 0(n^2)
            for (int j = 0; j < p1.get(i).size(); j++) {
                // System.out.printf("%15s",);
                System.out.printf("%15s", p1.get(i).get(j).get(0));
            }
            System.out.print(" ");
        }
        System.out.println();
    }

    public void merge_detail(String c1, String c2, int id_no, String[] arrc1, String[] brrc2) {
        // String p2 = c1.toString().split(" ")[1];
        // String p1 = c2.toString().split(" ")[1];
        // StringBuilder newp1 = new StringBuilder(p1);
        // StringBuilder newp2 = new StringBuilder(p2);

        // newp1.append(".Petrol");
        // newp2.append(".Petrol");

        ArrayList<ArrayList<ArrayList<String>>> p = new ArrayList<ArrayList<ArrayList<String>>>();

        Petrol pet = new Petrol(new String(c1));
        ArrayList<ArrayList<String>> __db_nb_sec = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < arrc1.length; i++) {
            __db_nb_sec.add(pet.query("id", Integer.toString(id_no), arrc1[i]));
        }

        p.add(__db_nb_sec);
        pet = new Petrol(new String(c2));

        ArrayList<ArrayList<String>> db_sec = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < brrc2.length; i++) {
            db_sec.add(pet.query("cust_id", Integer.toString(id_no), brrc2[i]));
        }
        p.add(db_sec);
        ArrayList<String> n = new ArrayList<>();

        for (int i = 0; i < arrc1.length; i++) {
            n.add(arrc1[i]);
        }

        for (int i = 0; i < brrc2.length; i++) {
            n.add(brrc2[i]);
        }
        for (int i = 0; i < n.size(); i++) {
            System.out.printf("%15s", n.get(i));
        }
        System.out.println();
        pretty_print(p, n);
    }

    public void total_cust_detail() {
        maindb writer = new maindb();
        try {
            writer.printer("Customer.Petrol");
        } catch (IOException e) {
            System.out.println();
            System.out.printf("%87s", "-----No Customer has yet availed our precious Car booking facilities-----");
            System.out.println();
        }
    }

    public void book(int cst_id, String numberplate, String date) {
        Petrol a = new Petrol("Cars.Petrol");
        if (a.query("status", "not_booked", "status").size() > 0) {
            a.replace("no_plate", numberplate, "status", "booked");
            a.replace("no_plate", numberplate, "booking_date", date);
            a.replace("no_plate", numberplate, "cust_id", Integer.toString(cst_id));
        } else {
            System.out.printf("%50s\n", "!!---Sorry No Car is available---!!");
        }
    }

    public void un_book(int cst_id) {
        Petrol a = new Petrol("Cars.Petrol");
        if (a.query("status", "booked", "status").size() > 0) {
            a.replace("cust_id", Integer.toString(cst_id), "status", "not_booked");
            a.replace("cust_id", Integer.toString(cst_id), "booking_date", "0");
            a.replace("cust_id", Integer.toString(cst_id), "cust_id", "0");
            a = new Petrol("Customer.Petrol");
            a.del_db("id", Integer.toString(cst_id));
        } else {
            System.out.printf("%50s\n", "!!---Sorry All Car is available---!!");
        }
    }

    public static void main(String[] args) {

    }
}
