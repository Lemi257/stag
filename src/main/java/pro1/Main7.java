package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.SpecializationsList;
import pro1.apiDataModel.Specialization;
import pro1.apiDataModel.Deadline;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main7 {
    public static void main (String[] args) {
        System.out.println(specializationDeadlines(2025));
    }
    public static String specializationDeadlines (int year){
        String json = Api.getSpecializations(year);
        SpecializationsList specializationsList
                = new Gson().fromJson(json, SpecializationsList.class);

        //řešení zhruba stejně jako Main1-6 ze cvik
        return specializationsList.prijimaciObor.stream()
                .filter(s -> s.eprDeadlinePrihlaska != null && s.eprDeadlinePrihlaska.value != null)
                .map(s -> s.eprDeadlinePrihlaska.value)
                .distinct()
                .sorted((d1, d2) -> {
                    String[] p1 = d1.split("\\.");
                    String[] p2 = d2.split("\\.");
                    //pozn na zk.:String s datumem se rozdělí ve dvou místech -> na tři části (part[0]+part[1]+part[2])

                    String n1 = p1[2] + String.format("%02d", Integer.parseInt(p1[1])) + String.format("%02d", Integer.parseInt(p1[0]));
                    String n2 = p2[2] + String.format("%02d", Integer.parseInt(p2[1])) + String.format("%02d", Integer.parseInt(p2[0]));
                    //pozn na zk.: %02d je standard pro formátování pomocí .format - %d je integer a 02 je min. počet číslic

                    return n1.compareTo(n2);
                })
                .collect(Collectors.joining(","));

        //řešení pomocí localdate od chata - taky celkem elegantní možnost řešení ;)
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy", Locale.ENGLISH);

        /*return specializationsList.prijimaciObor.stream()
                .filter(s -> s.eprDeadlinePrihlaska != null && s.eprDeadlinePrihlaska.value != null)
                .map(s -> {
                    try {
                        return LocalDate.parse(s.eprDeadlinePrihlaska.value, formatter);
                    } catch (Exception e) {
                        return null;
                    }})
                    //pozn na zk.:převede na datumy
                .filter(date -> date != null && date.getYear() == 2025)
                //pozn na zk.:odfiltruju null a cokoliv mimo rok 2025
                .sorted()
                .map(date -> date.format(formatter))
                //pozn na zk.:převede zpět na string
                .distinct()
                .collect(Collectors.joining(","));*/
    }
}
