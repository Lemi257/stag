package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.SpecializationsList;

import java.util.stream.Collectors;

public class Main7 {
    public static void main (String[] args) {
        System.out.println(specializationDeadlines(2025));
    }
    public static String specializationDeadlines (int year){
        String json = Api.getSpecializations(year);
        SpecializationsList specializationsList
                = new Gson().fromJson(json, SpecializationsList.class);

        return specializationsList.prijimaciObor.stream()
                .filter(s -> s.eprDeadlinePrihlaska != null && s.eprDeadlinePrihlaska.value != null)
                .map(s -> s.eprDeadlinePrihlaska.value)
                .distinct()
                .sorted((d1, d2) -> {
                    String[] p1 = d1.split("\\.");
                    String[] p2 = d2.split("\\.");

                    String n1 = p1[2] + String.format("%02d", Integer.parseInt(p1[1])) + String.format("%02d", Integer.parseInt(p1[0]));
                    String n2 = p2[2] + String.format("%02d", Integer.parseInt(p2[1])) + String.format("%02d", Integer.parseInt(p2[0]));

                    return n1.compareTo(n2);
                })
                .collect(Collectors.joining(","));
    }
}
