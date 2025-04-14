package pro1;

import com.google.gson.Gson;
import pro1.apiDataModel.ActionsList;

import java.util.stream.Collectors;

public class Main5 {

    public static void main(String[] args) {
        System.out.println(roomsSummary("KIKM",2024));
    }

    public static String roomsSummary(String department, int year)
    {
     // TODO 5.1: Vrať výpis učeben, které katedra v daném roce využila (seřadit abecedně, oddělit čárkou)
        //import rozvrhu
        //seřazení abecedně a oddělit J1,J10,J11,J2...
        String json = Api.getActionsByDepartment(department,year);
        ActionsList actions= new Gson().fromJson(json, ActionsList.class);

        //upravili jsme apiDataModel/Action aby četl i místnost
        String result = actions.items.stream()
                .map(a -> a.room)
                .filter(r -> r != null)
                .distinct() //projde aby každá místnost byla jen jednou
                .sorted()
                .collect(Collectors.joining(","));
        return result;
    }
}