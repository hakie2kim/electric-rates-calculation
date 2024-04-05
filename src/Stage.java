import java.util.Scanner;

public class Stage {
    private int quantity;

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.print("사용량을 입력하세요.>");
        quantity = sc.nextInt();

        // code here
        Calculation calculation = Calculation.getInstance(quantity);
        System.out.println(calculation);
    }
}
