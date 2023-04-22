import Orm.Orm;
import Petrol_engine.Petrol;

public class Customer {
    public int id;
    public String name;
    public long phone_no;
    public String ret_data;

    Customer() {
    }

    Customer(int i, String name, long ph, String ret_data) {
        this.id = i;
        this.name = name;
        this.phone_no = ph;
        this.ret_data = ret_data;
    }

    public void get_customer(int id) {
        Petrol pet = new Petrol("Customer.Petrol");
        pet.query("phone_no", Integer.toString(id));
    }

    public static void get_customer(String name) {
        Petrol pet = new Petrol("Customer.Petrol");
        pet.query("name", name);
    }

    public Customer make_customer(String name, long ph_no, String date) {
        Orm.Stringify(getClass(), "phone_no");
        Petrol pop = new Petrol("Customer.Petrol");
        int r = pop.last_int_util(pop._file_dbname);
        Customer c = null;
        if (r == -1) {
            c = new Customer(r + 2, name, ph_no, date);
            Orm.O_inserter(c);
        } else {
            c = new Customer(r + 1,name,ph_no,date);
            Orm.O_inserter(c);
        }
        return c;
    }

    public static void main(String[] args) {
        Orm.Stringify(Customer.class, "phone_no");
        // Customer c = new Customer(119, "Deellk", 7211712340L, "24/4/2019");
        // Customer c2 = new Customer(12, "Meow", 781171234L, "1/3/2019");
        // Customer c3 = new Customer(121, "PakDepp", 7411712341L, "2/4/2019");
        // Orm.O_inserter(c);
        Petrol p = new Petrol("Customer.Petrol");
        p.del_db("id", "12");
        // Orm.O_inserter(c2);
        // Orm.O_inserter(c3);
    }
}