package NBCassignment.JH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Order order = new Order();
        List<Product> products = new ArrayList<>();
        MessageBox messageBox = new MessageBox();
        while(true){
            displayMainMenu(products, order);
            int number = sc.nextInt();
            sc.nextLine();
            if(number == 1){
                displayMenu(createBurger(),order, sc);
            }else if(number == 2){
                displayMenu(createForzenCustard(),order, sc);
            }else if(number == 3){
                displayMenu(createDrinks(),order, sc);
            }else if(number == 4){
                displayMenu(Beer(),order, sc);
            }else if(number == 5){
                displayOrder(order, sc);
            }else if(number == 6){
                cancelOrder(order, sc);
            }else if(number == 7){
                managementProgram();
            }
        }
    }
    private static void displayMainMenu(List<Product> products, Order order) {
        System.out.println("\"JaeHoon BURGER 에 오신걸 환영합니다.\"");
        if(!order.getOrders().isEmpty()){
            System.out.println("[ 현재 메뉴 ]");
            products = order.getOrders();
            for (int i = 0; i < Math.min(3,products.size()); i++) {
                Product product = products.get(i);
                System.out.println(product.getName() + " | " + product.getPrice() + " | " + product.getExplanation());
            }
            System.out.println("총 금액 : " + order.totalPrice());
        }

        System.out.println("[ SHAKESHACK MENU ]");
        System.out.println("1. Burgers");
        System.out.println("2. Frozen Custard");
        System.out.println("3. Drinks");
        System.out.println("4. Beer\n");
        System.out.println("[ ORDER MENU ]");
        System.out.println("5. Order | 장바구니를 확인 후 주문합니다.");
        System.out.println("6. Cancel | 진행중인 주문을 취소합니다.");
    }
    private static void displayMenu(List<Product> product, Order order, Scanner sc){
        CompletedBox completedBox = new CompletedBox();
        waitBox waitBox = new waitBox();
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.");
        for (int i = 0; i < product.size(); i++) {
            Product products = product.get(i);
            System.out.println(i + 1 + ". " + products.getName() + " | " + products.getPrice() + " | " + products.getPrice());
        }
        int number = sc.nextInt();
        sc.nextLine();
        if(number >= 1 && number <= product.size()){
            Product products = product.get(number-1);
            System.out.println(products.getName() + " | " + products.getPrice() + " | " + products.getExplanation());
            System.out.println("위 메뉴를 장바구니에 추가하시곘습니까?");
            System.out.println("1. 확인  2. 취소");
            int numberOrder = sc.nextInt();;
            sc.nextLine();
            if(numberOrder == 1){
                order.addProducts(products); //오더
                completedBox.addCompleteBoxs(products); //완료
                waitBox.addWaitBoxs(products); //대기
                System.out.println(products.getName() + " 장바구니에 추가되었습니다.");
                System.out.println();
            }
        }
    }
    private static void displayOrder(Order order, Scanner sc) throws Exception{
        waitBox waitBox = new waitBox();
        CompletedBox completedBox = new CompletedBox();
        MessageBox messageBox = new MessageBox();
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println("[ Orders ]");
        for(Product orderMenu : order.getOrders()){
            System.out.println(orderMenu.getName() + " | " + orderMenu.getPrice() + " | " + orderMenu.getExplanation());
        }
        System.out.println("[ Total ]");
        System.out.println(" W " + order.totalPrice());
        System.out.println("1. 주문  2. 메뉴판");
        int number = sc.nextInt();
        sc.nextLine();
        if (number == 1) {
            System.out.println("요청 사항을 입력해 주세요.(20자 이내)");
            String input = sc.nextLine();
            if(input.length()>20){
                input = input.substring(0,20);
                System.out.println("글자수가 초가되었습니다.");
            }
            System.out.println("요청 사항을 확인해주세요.");
            System.out.println(input);
            System.out.println("1.확인  2.취소  3.메뉴판");
            messageBox.addMessage(input);
            int check = sc.nextInt();
            System.out.println("주문이 완료되었습니다!");
            System.out.println("대기번호는 [ " + count() + " ] 번 입니다.");
            if(check == 1){
                System.out.println("3초후 메뉴판으로 돌아갑니다.");
                order.clearOrder();
                Thread.sleep(3000);
            }else if(check == 2){
                displayOrder(order, sc);
            }
        }
    }
    private static int count(){
        int i = 1;
        int random = (int)(Math.random()*100);
        return random;
    }
    private static void cancelOrder(Order order, Scanner sc){
        CompletedBox completedBox = new CompletedBox();
        waitBox waitBox1 = new waitBox();
        waitBox waitBox = new waitBox();
        System.out.println("진행하던 주문을 취소하시곘습니까?");
        System.out.println("1. 확인  2. 취소");
        int number = sc.nextInt();
        sc.nextLine();
        if(number == 1){
            order.clearOrder();
            completedBox.clearCompleteBoxs();
            waitBox1.clearWaitBoxs();
            System.out.println("진행하던 주문이 취소되었습니다.");
        }
    }
    private static void managementProgram() throws Exception{
        List<Product> products = new ArrayList<>();
        Order order = new Order();
        Scanner sc = new Scanner(System.in);
        MessageBox messageBox = new MessageBox();
        waitBox waitBox = new waitBox();
        CompletedBox completedBox = new CompletedBox();

        System.out.println("1. 대기 주문목록");
        System.out.println("2. 완료 주문목록");
        System.out.println("3. 상품 생성");
        System.out.println("4. 상품 삭제");
        int number = sc.nextInt();
        sc.nextLine();

        if(number == 1){
            int waitNumber = count();
            System.out.println("1. 대기번호 : " + waitNumber);
            System.out.print("2. 주문 상품 목록 : ");
            for(Product product : order.getOrders()){
                System.out.println(product.getName() + ", ");
            }
            System.out.println();

            System.out.println("3. 주문 총 가격 : " + waitBox.totalPrice());
            System.out.println("4. 주문 일시 : ");
            System.out.println("5. 요청 사항 : " + messageBox.getMessage());
            System.out.println("5초 후 완료 주문목록으로 이동됩니다. 취소는 1번입니다.");
            int timeNumber = sc.nextInt();
            sc.nextLine();
            if(timeNumber == 1){
                managementProgram();
            }
            Thread.sleep(5000); //5초후
            waitBox.clearWaitBoxs();
        }
        System.out.println("2. 완료 주문목록");
        if(number == 2){
            int waitNumber = count();
            System.out.println("1. 대기번호 : " + order.totalPrice());
            System.out.print("2. 주문 상품 목록 : ");
            for(Product product : order.getOrders()){
                System.out.println(product.getName() + ", ");
            }
            System.out.println();

            System.out.println("3. 주문 총 가격 : " + completedBox.totalPrice());
            System.out.println("4. 주문 일시 : ");
            System.out.println("5. 요청 사항 : " + messageBox.getMessage());
            System.out.println("5초 후 완료 주문목록으로 이동됩니다. 취소는 1번입니다.");
            int timeNumber = sc.nextInt();
            sc.nextLine();
            if(timeNumber == 1){
                managementProgram();
            }
        }

        System.out.println("3. 상품 생성");
        System.out.println("4. 상품 삭제");
        System.out.println("5. 메뉴판");
        if(number == 5){
            displayMainMenu(products,order);
        }
    }

    private static List<Product> createBurger() {
        List<Product> burger = new ArrayList<>();
        burger.add(new Product("맥도날드", 5.0, "맛있음"));
        burger.add(new Product("버거킹", 5.5, "맛있음"));
        burger.add(new Product("롯데리아", 4.5, "맛있음"));
        return burger;
    }
    private static List<Product> createForzenCustard(){
        List<Product> ForzenCustard = new ArrayList<>();
        ForzenCustard.add(new Product("소프트콘",2.0,"맛있음"));
        ForzenCustard.add(new Product("초코콘",2.5,"맛있음"));
        ForzenCustard.add(new Product("바닐라콘",2.5,"맛있음"));
        return ForzenCustard;
    }
    private static List<Product> createDrinks(){
        List<Product> drinks = new ArrayList<>();
        drinks.add(new Product("콜라",2.0,"맛있음"));
        drinks.add(new Product("사이다",2.5,"맛있음"));
        drinks.add(new Product("제로추가",0.5,"맛있음"));
        return drinks;
    }
    private static List<Product> Beer(){
        List<Product> beer = new ArrayList<>();
        beer.add(new Product("생맥주",2.0,"시원함"));
        beer.add(new Product("흑맥주",2.5,"시원함"));
        beer.add(new Product("라거맥주",2.5,"시원함"));
        return beer;
    }
}