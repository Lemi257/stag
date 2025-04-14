package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.ActionsList;
import pro1.apiDataModel.Teacher;
import pro1.apiDataModel.TeachersList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main6 {

    public static void main(String[] args) {
        System.out.println(idOfBestTeacher("KIKM",2024));
    }

    public static long idOfBestTeacher(String department, int year)
    {
        // TODO 6.1 (navazuje na TODO 3):
        //  - Stáhni seznam akcí na katedře (jiná data nepoužívat)
        //  - Najdi učitele s nejvyšším "score" a vrať jeho ID
        String json = Api.getActionsByDepartment(department,year);
        ActionsList actions
                = new Gson().fromJson(json, ActionsList.class);

        HashMap<Long, Integer> hashMap = new HashMap<>();

        actions.items.stream()
                .forEach(a -> {
                    Integer current
                            = hashMap.getOrDefault(a.teacherId,0);
                    hashMap.put(a.teacherId,current + a.personsCount);
                });
        return hashMap.entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue()))
                .get().getKey();
    }
}