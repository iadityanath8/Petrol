package DB_beta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DB_beta.maindb.Db_errno;

class mbdb {
    public void printer(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String row;
        while ((row = reader.readLine()) != null) {
            String data[] = row.split(",");
            for (String valio : data) {
                System.out.printf("%20s", valio);
            }
            System.out.println();
        }
        reader.close();
    }

    public void printer_f_col(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String row;
        while ((row = reader.readLine()) != null) {
            String data[] = row.split(",");
            if (data.length > 1) {
                for (String valio : data) {
                    System.out.printf("%18s", valio);
                }
                System.out.println();
                break;
            }
        }
        reader.close();
    }

    public int indexer(String path, String field_name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String row;
        int flag = -1;
        while ((row = reader.readLine()) != null) {
            String data[] = row.split(",");
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals(field_name)) {
                    flag = i;
                }
            }
        }
        reader.close();
        return flag;
    }

    public ArrayList<Integer> indexer2(String path, String name, String field_name) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String row;
        ArrayList<Integer> flag = new ArrayList<Integer>();
        int vir_count = 0;
        int i = indexer(path, name);
        while ((row = reader.readLine()) != null) {
            String[] data = row.split(",");
            if (data.length != 1) {
                if (data[i].equals(field_name)) {
                    flag.add(vir_count);
                    vir_count++;
                } else {
                    vir_count++;
                }
            } else {
                vir_count++;
            }
        }
        reader.close();
        if (flag.isEmpty()) {
            System.err.println("Cannot Get The attribute " + field_name);
            return flag;
        } else {
            return flag;
        }
    }

    public ArrayList<String> finder_unstable(String path, String name, String field_name, String clstr)
            throws IOException {
        int op = 0;
        String row;
        BufferedReader reader = new BufferedReader(new FileReader(path));

        ArrayList<String> ret_val = new ArrayList<String>();
        ArrayList<Integer> inde = indexer2(path, name, field_name);

        int i = indexer(path, clstr);
        int indeee = 0;
        while ((row = reader.readLine()) != null) {
            String[] data = row.split(",");
            if (indeee >= inde.size()) {
                break;
            }
            if (op == inde.get(indeee)) {
                ret_val.add(data[i]);
                indeee++;
                op++;
            } else {
                op++;
            }
        }
        reader.close();
        return ret_val;
    }

    public Db_errno finder_unstable(String path, String name, String field_name) throws IOException {
        String row;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        ArrayList<Integer> inde = indexer2(path, name, field_name);
        int indeee = 0;
        int op = 0;
        while ((row = reader.readLine()) != null) {
            String[] data = row.split(",");
            if (indeee >= inde.size()) {
                break;
            }
            if (op == inde.get(indeee)) {
                for (String valio : data) {
                    System.out.printf("%18s", valio);
                }
                System.out.println();
                indeee++;
                op++;
            } else {
                op++;
            }
        }
        reader.close();
        if (inde.size() == 0) {
            return Db_errno.EE_QUERY_FAILED;
        } else {
            return Db_errno.EE_QUERY_SUCCESS;
        }
    }

    public ArrayList<ArrayList<String>> Selecter(String path, String[] attr, String[] feild_name, String[] what)
            throws IOException {
        // {"name","class"},{"Aditya Nath","1"},{"class","Roll_no"}
        ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < what.length; i++) {
            arr.add(finder_unstable(path, attr[i], feild_name[i], what[i]));
        }
        return arr;
    }
}

class Mbdb_first_beta_0 extends mbdb {
    public void write_Create_unstable_db(String path, String pk, String... args) throws IOException {
        FileWriter writer = new FileWriter(path);

        int len = args.length;
        int i = 0;
        String val = "";
        for (int j = 0; j < args.length; j++) {
            if (args[j].equals(pk)) {
                val = Integer.toString(j);
            }
        }
        writer.append(val);
        writer.append("\n");
        while (i < len - 1) {
            writer.append(args[i]);
            writer.append(",");
            i++;
        }
        writer.append(args[i]);

        writer.append("\n");

        writer.flush();
        writer.close();
    }

    public void write(String path, List<List<String>> op, Boolean selecter) throws IOException {
        FileWriter writer = new FileWriter(path, selecter);
        for (List<String> rowdata : op) {
            writer.append(String.join(",", rowdata));
            writer.append("\n");
        }
        writer.flush();
        writer.close();
    }

    public void replace_unstable(String path, String old_opt, String new_opt) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(path));
        String row;
        List<List<String>> op = new ArrayList<List<String>>();
        while ((row = read.readLine()) != null) {
            row = row.replaceAll(old_opt, new_opt);
            String[] data = row.split(",");
            List<String> a = Arrays.asList(data);
            op.add(a);
        }
        write(path, op, false);
        read.close();
    }
}

public class maindb extends Mbdb_first_beta_0 {
    public enum Db_errno {
        EE_TABLE_CREATED,
        EE_TABLE_CREATION_FAILED,
        EE_TABLE_INSERTION_SUCCEDED,
        EE_TABLE_INSERTION_FAILED,
        EE_TABLE_PK_VIOLATION,
        EE_INTERNAL_EXECPTION_HANDLED,
        EE_QUERY_SUCCESS,
        EE_QUERY_FAILED
    }

    public void replace_stable(String path, String field_name, String old_opt, String new_opt)
            throws IOException {
        mbdb obj = new mbdb();
        int index = obj.indexer(path, field_name);
        int flag = -1;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String row;
        List<List<String>> op = new ArrayList<List<String>>();
        List<String> ad = new ArrayList<String>();
        while ((row = reader.readLine()) != null) {
            String[] data = row.split(",");
            if (index == -1) {
                break;
            }
            if (data[index].equals(old_opt)) {
                data[index] = data[index].replace(old_opt, new_opt);
                ad = Arrays.asList(data);
                op.add(ad);
                flag = 1;
            } else {
                ad = Arrays.asList(data);
                op.add(ad);
            }
        }
        if (index == -1) {
            System.err.println("cannot find " + field_name);
        }
        if (flag == -1) {
            System.err.println("cannot find " + old_opt);
        }
        write(path, op, false);
        reader.close();
    }

    public String find_indexer(String path, int i, int j) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String row;
        int normal = 0;
        String re_val = " ";
        while ((row = reader.readLine()) != null) {
            String data[] = row.split(",");
            if (normal == i) {
                if (j < data.length) {
                    re_val = data[j];
                    break;
                }
            } else {
                normal++;
            }
        }
        reader.close();
        if (re_val.isEmpty()) {
            return "failed";
        } else {
            return re_val;
        }
    }

    public void change_cond_primary_key(String path, String field_name, String val, String sec_name,
            String new_opt)
            throws IOException {
        mbdb obj = new mbdb();
        ArrayList<Integer> a = obj.indexer2(path, field_name, val);
        int index = obj.indexer(path, sec_name);
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String row;
        List<List<String>> op = new ArrayList<List<String>>();
        List<String> ad = new ArrayList<String>();
        int i = 0;
        while ((row = reader.readLine()) != null) {
            String[] data = row.split(",");
            if (i == a.get(0)) {
                data[index] = data[index].replace(find_indexer(path, i, index), new_opt);
                ad = Arrays.asList(data);
                op.add(ad);
                i++;
            } else {
                ad = Arrays.asList(data);
                op.add(ad);
                i++;
            }
        }
        reader.close();
        Mbdb_first_beta_0 cl1 = new Mbdb_first_beta_0();
        cl1.write(path, op, false);
    }

 public void delete_unstable(String path,String field,String Primary__rec_mon) throws IOException{
        mbdb db = new mbdb();
        var r = db.indexer2(path, field, Primary__rec_mon).get(0);
        int count = 0;
        String row;
        List<String> ad = new ArrayList<String>();
        List<List<String>> op = new ArrayList<List<String>>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while ((row = reader.readLine())!=null) {
            String[] data = row.split(",");
            if(count != r){ 
                ad = Arrays.asList(data);
                op.add(ad);
                count++;
            }else{
                count++;
            }
        }
        Mbdb_first_beta_0 __dbinsec = new Mbdb_first_beta_0();
        __dbinsec.write(path, op, false);
        reader.close();
    }
    public int Last_indexer(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine().split(",");
        String[] data = reader.readLine().split(",");
        reader.close();
        return data.length - 1;
    }

    public String Lastrow(String path, int indexing) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String rows;
        String i = "-1";
        int two = 2;
        while ((rows = reader.readLine()) != null) {
            if (two != 0) {
                var r = (rows.split(",")[indexing]);
                two--;
            } else {
                try {
                    i = (rows.split(",")[indexing]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please pass the index within the range of csv database");
                }
            }
        }
        reader.close();
        return i;
    }

    public int pk_check(String path) throws IOException {
        String row;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        row = reader.readLine();
        reader.close();
        if (row == null) {
            return 97;
        }
        return Integer.parseInt(row);
    }

    public Db_errno create_tb(String path, String pk, String... args) throws IOException {
        if (pk_check(path) == 97) {
            write_Create_unstable_db(path, pk, args);
            Db_errno err = Db_errno.EE_TABLE_CREATED;
            return err;
        } else {
            Db_errno err = Db_errno.EE_TABLE_CREATION_FAILED;
            return err;
        }
    }

    public Db_errno insert_into_tb_UTH_i(String path, String... args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String checker = args[pk_check(path)]; /// Error occured
        int vaaar = 0;
        int index = pk_check(path);
        String row;
        while ((row = reader.readLine()) != null) {
            String data[] = row.split(",");
            if (data.length != 1) {
                if (data[index].equals(checker)) {
                    System.err.println("Primary key violation");
                    vaaar = 90;
                }
            }
        }
        reader.close();
        if (vaaar == 0) {
            if (args.length - 1 == Last_indexer(path)) {
                List<List<String>> __l = new ArrayList<List<String>>();
                __l.add(Arrays.asList(args));
                write(path, __l, true);
                Db_errno err = Db_errno.EE_TABLE_INSERTION_SUCCEDED;
                return err;
            } else {
                System.err.println("data inserted is quite larger than the attribute");
                Db_errno err = Db_errno.EE_TABLE_INSERTION_FAILED;
                return err;
            }
        } else {
            Db_errno err = Db_errno.EE_TABLE_PK_VIOLATION;
            return err;
        }

    }
}