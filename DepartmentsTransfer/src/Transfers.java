import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Transfers {
    public static void main(String[] args) {

        // Запрашиваем у пользователя путь к файлу
        System.out.println("Введите путь файла с сотрудниками.");
        System.out.println("Предполагается, что фамилии, отделы и зарплаты разделены" +
                " точкой с запятой.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = null;
        try {
            fileName = reader.readLine();
            reader.close();
        } catch (IOException ioex) {
            System.out.println("Введите, пожалуйста, корректный путь файла");
        }

        // Создаем отдельные списки для фамилий, отделов и зарплат
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> departments = new ArrayList<>();
        ArrayList<Double> salaries = new ArrayList<>();

        // Читаем файл, сплитим по разделителю и заполняем списки
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException fnfex) {
            System.out.println("Ошибка указания пути файла. Перезапустите программу и " +
                    "укажите верный путь.");
        }

        try {
            while(fileReader.ready()) {
                String nextLine = fileReader.readLine();
                String[] data = nextLine.split(";");
                if (data.length != 3) System.out.println("Неверный формат данных. " +
                        "Данные должны быть указаны в формате Фамилия;Отдел;Зарплата(число).");
                names.add(data[0].trim()); // Добавляем фамилию в список
                departments.add(data[1].trim()); // Добавляем отдел

                try {
                    double salary = Double.parseDouble(data[2].trim());
                    salaries.add(salary); // Добавляем зарплату
                } catch (NumberFormatException nfex) {
                    System.out.println("Зарплата не является числом." +
                            "Поэтому вы видите ошибку ниже. Пожалуйста, проверьте данные о зарплате.");
                }
            }
        } catch (IOException fileReadEx) {
            System.out.println("Ошибка чтения файла.");
        }

        // Создаем список департаментов
        HashSet<String> departmentsSet = new HashSet<>();
        for (String department : departments) {
            departmentsSet.add(department);
        }

        // Проходимся по списку департаментов и считаем среднюю зарплату
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("C:/Test/result.txt"));
            for (String departmentInSet : departmentsSet) {
                double sum = 0.0; // Для расчета средней зарплаты
                int employeeNumber = 0; // Для подсчета количества сотрудников в департаменте
                for (int i = 0; i < departments.size(); i++) {
                    if (departmentInSet.equals(departments.get(i))) {
                        sum += salaries.get(i);
                        employeeNumber++;
                    }
                }
                double averageSalary = sum / employeeNumber;
                fileWriter.write("Средняя зарплата в департаменте " + departmentInSet +
                " равна " + averageSalary);
                fileWriter.newLine();
            }
            fileWriter.close();
            System.out.println("Готово. Проверьте файл с результатом C:/Test/result.txt");
        } catch (IOException ioex) {
            System.out.println("Ошибка записи в файл. Пожалуйста, повторите попытку.");
        }

    }

    /*
    public static double getAverageSalary(ArrayList<Double> list) {
        double sum = 0.0;
        for (Double number : list) {
            sum += number;
        }
        return  sum/list.size();
    }
    */
}
