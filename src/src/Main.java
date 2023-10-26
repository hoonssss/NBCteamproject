package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Order order = new Order();
    static MessageBox messageBox = new MessageBox();
    static waitBox waitBox = new waitBox();
    static CompletedBox completedBox = new CompletedBox();
    static int waitNumber = 1;

    public static void main(String[] args) throws Exception {
        while (true) {
            displayMainMenu(order);
            int number = sc.nextInt();
            sc.nextLine();
            if (number == 1) {
                displayMenu(createBurger(), order, sc);
            } else if (number == 2) {
                displayMenu(createForzenCustard(), order, sc);
            } else if (number == 3) {
                displayMenu(createDrinks(), order, sc);
            } else if (number == 4) {
                displayMenu(createBeer(), order, sc);
            } else if (number == 5) {
                displayOrder(order, sc);
            } else if (number == 6) {
                cancelOrder(order, sc);
            } else if (number == 7) {
                managementProgram();
            }
        }
    }

    private static void displayMainMenu(Order order) {
        System.out.println("\"JaeHoon BURGER 에 오신걸 환영합니다.\"");
        if (!order.getOrders().isEmpty()) {
            System.out.println("[ 현재 메뉴 ]");
            List<Product> products = order.getOrders();
            for (int i = 0; i < Math.min(3, products.size()); i++) {
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
        System.out.println("7. Kiosk Management");
    }

    private static void displayMenu(List<Product> product, Order order, Scanner sc) {
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.");
        for (int i = 0; i < product.size(); i++) {
            Product products = product.get(i);
            System.out.println(i + 1 + ". " + products.getName() + " | " + products.getPrice() + " | " + products.getExplanation());
        }
        int number = sc.nextInt();
        sc.nextLine();
        if (number >= 1 && number <= product.size()) {
            Product products = product.get(number - 1);
            System.out.println(products.getName() + " | " + products.getPrice() + " | " + products.getExplanation());
            System.out.println("위 메뉴를 장바구니에 추가하시겘습니까?");
            System.out.println("1. 확인  2. 취소");
            int numberOrder = sc.nextInt();
            sc.nextLine();
            if (numberOrder == 1) {
                order.addProducts(products); // 오더
                waitBox.addWaitBoxs(products); // 대기
                completedBox.addCompleteBoxs(products);
                System.out.println(products.getName() + " 장바구니에 추가되었습니다.");
                System.out.println("대기번호는 [ " + waitNumber + " ] 번 입니다.");
                waitNumber++;
                System.out.println();
            }
        }
    }

    private static void displayOrder(Order order, Scanner sc) throws Exception {
        MessageBox messageBox = new MessageBox();
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println("[ Orders ]");
        for (Product orderMenu : order.getOrders()) {
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
            if (input.length() > 20) {
                input = input.substring(0, 20);
                System.out.println("글자수가 초과되었습니다.");
            }
            System.out.println("요청 사항을 확인해주세요.");
            System.out.println(input);
            System.out.println("1. 확인  2. 취소  3. 메뉴판");
            messageBox.addMessage(input);
            int check = sc.nextInt();
            if (check == 1) {
                System.out.println("주문이 완료되었습니다!");
                System.out.println("3초후 메뉴판으로 돌아갑니다.");
                Thread.sleep(3000);
                order.clearOrder();
            } else if (check == 2) {
                System.out.println("취소하였습니다. 기존 메뉴는 유지됩니다.");
                displayOrder(order, sc);
            } else {
                System.out.println("메뉴판으로 돌아가겠습니다. 기존 메뉴는 유지됩니다.");
            }
        }
    }

    private static void cancelOrder(Order order, Scanner sc) {
        System.out.println("진행하던 주문을 취소하시곘습니까?");
        System.out.println("1. 확인  2. 취소");
        int number = sc.nextInt();
        sc.nextLine();
        if (number == 1) {
            order.clearOrder();
            System.out.println("진행하던 주문이 취소되었습니다.");
        }
    }

    private static void managementProgram() throws Exception {
        System.out.println("1. 대기 주문목록");
        System.out.println("2. 완료 주문목록");
        System.out.println("3. 상품 생성");
        System.out.println("4. 상품 삭제");
        System.out.println("5. 메인 메뉴");
        int number = sc.nextInt();
        sc.nextLine();


        if (number == 1) {
            System.out.println("대기번호 : " + (waitNumber - 1));
            System.out.print("주문 상품 목록 : ");
            List<Product> waitProduct = waitBox.getWaitBoxs();
            for (Product waitBox : waitProduct) {
                System.out.print(waitBox.getName() + ", ");
            }
            System.out.println();
            System.out.println("주문 총 가격 : " + waitBox.totalPrice());
            System.out.print("요청 사항 : ");
            List<String> message = messageBox.getMessage();
            for(String messageBox : message) {
                System.out.println(messageBox);
            }
            System.out.println();
            System.out.println("주문 일시 : " + getTimestamp());
            System.out.println("3초 후 완료 주문 목록으로 이동됩니다.");
            waitBox.clearWaitBoxs();
            messageBox.clearMessage();
            Thread.sleep(3000); // 5초후
        } else if (number == 2) {
            System.out.println("대기번호 : " + (waitNumber - 1));
            System.out.print("주문 상품 목록 : ");
            List<Product> productList = completedBox.getCompleteBoxs();
            for (Product product : productList) {
                System.out.print(product.getName() + ", ");
            }
            System.out.println();
            System.out.println("주문 총 가격 : " + completedBox.totalPrice());
            System.out.println("주문 일시 : " + getTimestamp());
            System.out.println("요청 사항 : " );
            System.out.println("5초 후 주문목록으로 이동됩니다. 취소는 1번입니다.");
            Thread.sleep(3000);
            displayMainMenu(order);
            int timeNumber = sc.nextInt();
            sc.nextLine();
            if (timeNumber == 1) {
                managementProgram();
            }
        } else if(number == 3){
            createProduct(order, sc);
        } else if(number == 4){

        } else if(number == 5){
            displayMainMenu(order);
        }
    }

    public static void createProduct(Order order, Scanner sc){
        System.out.println("카테고리를 선택하세요");
        System.out.println("1. Burgers");
        System.out.println("2. Frozen Custard");
        System.out.println("3. Drinks");
        System.out.println("4. Beer");
        int categoryChoice = sc.nextInt();
        sc.nextLine();

        List<Product> products = new ArrayList<>();
        String categoryName = "";

        switch (categoryChoice) {
            case 1:
                products = createBurger();
                categoryName = "Burgers";
                break;
            case 2:
                products = createForzenCustard();
                categoryName = "Frozen Custard";
                break;
            case 3:
                products = createDrinks();
                categoryName = "Drinks";
                break;
            case 4:
                products = createBeer();
                categoryName = "Beer";
                break;
            default:
                System.out.println("오류 입니다.");
                return;
        }

        System.out.println("상품명을 작성해주세요 : ");
        String product = sc.nextLine();
        System.out.println("상품 가격을 작성해주세요 :");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.println("상품 설명을 작성해주세요 :");
        String explanation = sc.nextLine();

        Product addProduct = new Product(product, price, explanation);
        order.addProducts(addProduct);
    }
    private static String getTimestamp(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    private static List<Product> createBurger() {
        List<Product> burger = new ArrayList<>();
        burger.add(new Product("맥도날드", 5.0, "맛있음"));
        burger.add(new Product("버거킹", 5.5, "맛있음"));
        burger.add(new Product("롯데리아", 4.5, "맛있음"));
        return burger;
    }

    private static List<Product> createForzenCustard() {
        List<Product> frozenCustard = new ArrayList<>();
        frozenCustard.add(new Product("소프트콘", 2.0, "맛있음"));
        frozenCustard.add(new Product("초코콘", 2.5, "맛있음"));
        frozenCustard.add(new Product("바닐라콘", 2.5, "맛있음"));
        return frozenCustard;
    }

    private static List<Product> createDrinks() {
        List<Product> drinks = new ArrayList<>();
        drinks.add(new Product("콜라", 2.0, "맛있음"));
        drinks.add(new Product("사이다", 2.5, "맛있음"));
        drinks.add(new Product("제로추가", 0.5, "맛있음"));
        return drinks;
    }

    private static List<Product> createBeer() {
        List<Product> beer = new ArrayList<>();
        beer.add(new Product("생맥주", 2.0, "시원함"));
        beer.add(new Product("흑맥주", 2.5, "시원함"));
        beer.add(new Product("라거맥주", 2.5, "시원함"));
        return beer;
    }
}