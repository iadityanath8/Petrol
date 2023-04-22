package Orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import DB_beta.maindb.Db_errno;
import Petrol_engine.Petrol;


public final class Orm {

    private static ArrayList<String> pa_table;

    public static void Stringify(Class cls, String Pk) {

        // 5 Strings and 1 StringBuilder
        var r = cls.getDeclaredFields();
        StringBuilder p = new StringBuilder();
        for (int i = 0; i < r.length; i++) {
            p.append(r[i].toString());
            p.append(" ");
        }
        String pp = p.toString();
        String[] data = pp.split("[.]|\s+");

        ArrayList<String> pa = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals("int")) {
                pa.add(data[i + 2]);
            } else if (data[i].equals("String")) {
                pa.add(data[i + 2]);
            }else if(data[i].equals("long")){
                pa.add(data[i + 2]);
            }else if (data[i].equals("boolean")) {
                pa.add(data[i + 2]);
            }
        }
        // Orm delaration
        pa_table = pa;

        String[] srr = new String[pa_table.size()];

        // Petrol declaration
        Petrol __pa = new Petrol();
        __pa.Petrol_make(cls.toString().split(" ")[1], Pk, pa.toArray(srr));
    }

    public static <T> Db_errno O_inserter(T cls_obj) {
        String[] data = new String[pa_table.size()];
        for (int i = 0; i < pa_table.size(); i++) {
            Field a = null;
            try {
                a = cls_obj.getClass().getField(pa_table.get(i));
            } catch (NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
            try {
                data[i] = a.get(cls_obj).toString();
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Petrol __inter_cdel_Pet = new Petrol(cls_obj.getClass().toString().split(" ")[1].concat(".Petrol"));
        return __inter_cdel_Pet.insert(data);
    }   
}