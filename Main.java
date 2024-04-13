import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carID;
    private String carName;
    private String carBrand;
    private int basePrice;
    private boolean isAvailable;

    public Car(String carID,String carName,String carBrand,int basePrice){

        this.carID=carID;
        this.carName=carName;
        this.carBrand=carBrand;
        this.basePrice=basePrice;
        this.isAvailable=true;
    }

    public String getcarID(){
        return carID;
    }
    
    public String getcarName(){
        return carName;
    }
    
    public String getcarBrand(){
        return carBrand;
    }
    
    public int getbasePrice(){
        return basePrice;
    }
    
    public boolean isAvailable(){
        return isAvailable;
    }

    public boolean rent(){
        return isAvailable=false;
    }
    public boolean returnCar(){
        return isAvailable=true;
    }

    public int calculatePrice(int days){
        return basePrice * days;
    }
}

class Customer{
    private String cusID;
    private String cusName;

    public Customer(String cusID,String cusName){
        this.cusID=cusID;
        this.cusName=cusName;
    }

    public String getcusID(){
        return cusID;
    }
    public String getcusName(){
        return cusName;
    }
}

class rental{
    private Car car;
    private Customer customer;

    private int days;
    
    public rental(Car car,Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }
    public Car getCar(){
        return car;
    
    }
    public Customer getCustomer(){
        return customer;

    }

    public int getDays(){
        return days;

    }

}

class CarRentalSystem{

    private List<Car> cars;
    private List<Customer> Customers;
    private List<rental> Rentals;

    public CarRentalSystem(){
        cars=new ArrayList<>();
        Customers=new ArrayList<>();
        Rentals=new ArrayList<>();

    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        Customers.add(customer);
    }
    public void addRental(Car car,Customer customer,int days){
        if(car.isAvailable()){
            car.rent();
            Rentals.add(new rental(car,customer,days));
        }
        else{
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car){
        rental RentaltoRemove=null;
        for(rental rental:Rentals){
            if(rental.getCar()==car){
                RentaltoRemove=rental;
                break;
            }
            if(RentaltoRemove!=null){
                Rentals.remove(RentaltoRemove);
                System.out.println("Car returned successfully.");
            }
            else{
                System.out.println("Car was not rented.");
            }
        }
        car.returnCar();
    }
    public void menu(){
        Scanner scanner=new Scanner(System.in);
        while (true) {
            System.out.println(); //Blank line
            System.out.println("====Car Rental System====");
            System.out.println("Choose a car to rent: ");
            System.out.println("1.Rent a car");
            System.out.println("2.Return a car");
            System.out.println("3.Exit");
            System.out.println();

            int choice=scanner.nextInt();
            scanner.nextLine();
            if(choice==1){
                System.out.println("\n==Rent a car==\n");
                System.out.println("Enter your name.");
                String cusName=scanner.nextLine();
                
                System.out.println("\nAvailable cars: ");
                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println(car.getcarID()+"\t\t"+car.getcarName()+"\t\t"+car.getcarBrand());
                    }
                }
                System.out.println("\nEnter the id of Car you want to rent: ");
                String carID=scanner.nextLine();

                System.out.println("Enter the number of days you want to rent: ");
                int days=scanner.nextInt();

                Customer newCustomer=new Customer("CUS"+(Customers.size()+1), cusName);
                Customers.add(newCustomer);

                Car SelectedCar=null;
                for(Car car:cars){
                    if(car.getcarID().equals(carID)&&car.isAvailable()){
                        SelectedCar = car;
                        break;
                    }
                }
                if(SelectedCar!=null){
                    double totalPrice=SelectedCar.calculatePrice(days);
                    System.out.println("\n==Rental Information==");
                    System.out.println("Customer ID:\t"+newCustomer.getcusID());
                    System.out.println("Customer Name:\t"+newCustomer.getcusName());
                    System.out.println("Car:\t"+SelectedCar.getcarID()+"\t"+SelectedCar.getcarBrand());
                    System.out.println("Rented days:\t"+days);
                    System.out.println("Total price:\t"+totalPrice);
                    scanner.nextLine();
                    System.out.println("\nConfirm Rental Y/N?");
                    String confirm=scanner.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        addRental(SelectedCar, newCustomer, days);
                        System.out.println("\nCar rented successfully.");
                    }
                    else{
                        System.out.println("\nRental cancelled.");
                    }
                }
                else{
                    System.out.println("\nCar not available for rent.");
                }
            }
            else if(choice==2){
                System.out.println("==Return a Car==");
                System.out.println("Enter the ID of the Car you want to return: ");
                String carID=scanner.nextLine();

                Car carToReturn=null;
                for(Car car:cars){
                    if(car.getcarID().equals(carID)&&!car.isAvailable()){
                        carToReturn=car;
                        break;
                    }
                }
                if(carToReturn!=null){
                    Customer customer=null;
                    for(rental rental:Rentals){
                        if(rental.getCar()==carToReturn){
                            customer=rental.getCustomer();
                            break;
                        }
                    }
                    if(customer!=null){
                        returnCar(carToReturn);
                        System.out.println("\nCar returned successfully by "+customer.getcusName());
                    }
                    else{
                        System.out.println("Car was not returned.");
                    }
                }
                else{
                    System.out.println("Invalid car ID or Car was not rented.");
                }

            }
            else if(choice==3){
                break;
            }
            else{
                System.out.println("Invalid input.Please choose a valid option.");
            }
        }
    }    
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem=new CarRentalSystem();
        Car car1=new Car("C001", "Ford2010", "Ford", 100);
        Car car2=new Car("C002", "T-Y", "Tesla", 150);
        Car car3=new Car("C003", "Maruti", "Suzuki", 80);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
    
}