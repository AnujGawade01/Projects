/*
    ParkingLot Automation System

    Step 1 : Create required enums
    Step 2 : Vehicle Hierarchy creation
    Step 3 : VehicleFactory creation (Factory Pattern)
    Step 4 : ParkingSpot Hierarchy
    Step 5 : ParkingObserver class
    Step 6 : ParkingFloor class
    Step 7 : ParkingDisplayBoard (Observer Pattern)
    Step 8 : ParkingStrategy class (Strategy Pattern)
    Step 9 : PricingStrategy class (Strategy Pattern)
    Step 10 : PaymentStrategy class
    Step 11 : ParkingTicket class
    Step 12 : EntryGate class
    Step 13 : ExitGate class
    Step 14 : ParkingLot class (Singleton Pattern)
    Step 15 : Main class (Controller)
*/

import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;

/////////////////////////////////////////////////////////////
//
// Step 15 : Controller of the project
// 
/////////////////////////////////////////////////////////////

/* 
    Step 1 : Create ParkingLot class object

    Step 2 : Create floors

    Step 3 : Add parking spots

    Step 4 : Create Display board

    Step 5 : Register observers

    Step 6 : Add floor to parkingLot

    Step 7 : Create Entry Exit Gates

    Step 8 : Display Menu
*/
class parkinglotsys
{
    public static void main(String A[]) throws Exception
    {
        Scanner sobj = new Scanner(System.in);

        /////////////////////////////////////////////////////////////
        //
        // 1 : Create single Parking Lot object
        // 
        /////////////////////////////////////////////////////////////

        ParkingLot parkingLot = ParkingLot.getInstance();

        parkingLot.setParkingLotName("Marvellous ParkEngine");

        /////////////////////////////////////////////////////////////
        //
        // 2 : Create multiple floors
        // 
        /////////////////////////////////////////////////////////////

        ParkingFloor floor1 = new ParkingFloor(1);

        /////////////////////////////////////////////////////////////
        //
        // 3 : Create multiple spots
        // 
        /////////////////////////////////////////////////////////////

        // Add first floor

        floor1.addParkingSpot(new BikeSpot(101));
        floor1.addParkingSpot(new BikeSpot(102));

        floor1.addParkingSpot(new CarSpot(103));
        floor1.addParkingSpot(new CarSpot(104));
        
        floor1.addParkingSpot(new TruckSpot(105));
        floor1.addParkingSpot(new TruckSpot(106));

        /////////////////////////////////////////////////////////////
        //
        // 4 : Create display board
        // 
        /////////////////////////////////////////////////////////////

        ParkingDisplayBoard board1 = new ParkingDisplayBoard(floor1);

        // Register the display board with observer
        floor1.addObserver(board1);

        // Add second floor

        ParkingFloor floor2 = new ParkingFloor(2);

        /////////////////////////////////////////////////////////////
        //
        // 3 : Create multiple spots
        // 
        /////////////////////////////////////////////////////////////

        floor2.addParkingSpot(new BikeSpot(201));
        floor2.addParkingSpot(new BikeSpot(202));

        floor2.addParkingSpot(new CarSpot(203));
        floor2.addParkingSpot(new CarSpot(204));
        
        floor2.addParkingSpot(new TruckSpot(205));
        floor2.addParkingSpot(new TruckSpot(206));

        /////////////////////////////////////////////////////////////
        //
        // 4 : Create display board
        // 
        /////////////////////////////////////////////////////////////

        ParkingDisplayBoard board2 = new ParkingDisplayBoard(floor2);

        // Register the display board with observer
        floor2.addObserver(board2);

        /////////////////////////////////////////////////////////////
        //
        // 6 : Add floors to ParkingLot
        // 
        /////////////////////////////////////////////////////////////

        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);

        /////////////////////////////////////////////////////////////
        //
        // 7 : Create EntryGate and ExitGate
        // 
        /////////////////////////////////////////////////////////////

        EntryGate entryGate = new EntryGate(1);
        ExitGate exitGate = new ExitGate(1);

        /////////////////////////////////////////////////////////////
        //
        // 8 : Display menu
        // 
        /////////////////////////////////////////////////////////////

        int choice = 0;
        while(true)
        {
            System.out.println("-----------------------------------");
            System.out.println("------ Marvellous ParkEngine ------");
            System.out.println("-----------------------------------");

            System.out.println("1 : Park Vehicle");
            System.out.println("2 : Exit Vehicle");
            System.out.println("3 : Search Vehicle");
            System.out.println("4 : Display Parking Lot");
            System.out.println("5 : Exit");

            System.out.println("Enter your choice : ");

            choice = sobj.nextInt();

            try
            {
                switch(choice)
                {
                    case 1: // Park Vehicle
                        {
                            System.out.println();

                            System.out.println("Select vehicle type : ");

                            System.out.println("1 : Bike");
                            System.out.println("2 : Car");
                            System.out.println("3 : Truck");

                            int type = sobj.nextInt();

                            System.out.println("Enter vehicle number : ");

                            String number = sobj.next();

                            Vehicle vehicle;

                            // Factory pattern is used

                            switch(type)
                            {
                                case 1: // BIKE
                                    vehicle = VehicleFactory.creatVehicle(VehicleType.BIKE, number);
                                    break;
                                
                                case 2: // CAR
                                    vehicle = VehicleFactory.creatVehicle(VehicleType.CAR, number);
                                    break;
                                
                                case 3: // TRUCK
                                    vehicle = VehicleFactory.creatVehicle(VehicleType.TRUCK, number);
                                    break;

                                default:
                                    System.out.println("Invalid type of vehicle");
                                    continue;
                            }

                            // Park the vehicle and generate the ticket

                            ParkingTicket ticket = parkingLot.parkVehicle(vehicle, entryGate);

                            // Display generated ticket
                            ticket.displayTicket();

                            break;


                        }   // end of case 1

                    case 2: // Exit Vehicle
                        {
                            System.out.println("Enter ticket number : ");

                            int ticketNumber = sobj.nextInt();

                            System.out.println();

                            System.out.println("Enter the payment option : ");
                            System.out.println("1 : Cash");
                            System.out.println("2 : UPI");
                            System.out.println("3 : Card");

                            int paymentType = sobj.nextInt();

                            PaymentStrategy paymentStrategy;

                            switch(paymentType)
                            {
                                case 1: // Cash
                                    paymentStrategy = new CashPayment();
                                    break;
                                
                                case 2: // UPI
                                    paymentStrategy = new UPIPayment();
                                    break;

                                case 3: // Card
                                    paymentStrategy = new CardPayment();
                                    break;
                                
                                default:
                                    System.out.println("Invalid payment option");
                                    continue;
                            } // end of payment switch

                            parkingLot.removeVehicle(ticketNumber, exitGate, paymentStrategy);
                            break;
                        }
                    case 3: // Search vehicle
                        {
                            System.out.println("Enter vehicle number : ");
                            String vehicleNumber = sobj.next();

                            ParkingTicket ticket = parkingLot.searchVehicle(vehicleNumber);

                            if(ticket == null)
                            {
                                System.out.println("This vehicle is not parked");
                            }
                            else
                            {
                                ticket.displayTicket();
                            }
                            break;
                        }
                    case 4: // Display Parking Lot
                        {
                            parkingLot.displayParkingLot();
                            break;
                        }
                    case 5:
                        {
                            System.out.println("Thank you for using Marvellous ParkEngine");

                            sobj.close();

                            return;   
                        }
                    default:
                        {
                            System.out.println("Invalid option");
                        }
                }   // End of switch
            }   // End of try
            catch(Exception eobj)
            {
                System.out.println("Exception occured : "+eobj);
            }  
            
        }   // End of while

    }
}   // End of main class

/////////////////////////////////////////////////////////////
// Step 1 : Create Enums
// It is used to create fixed constants which are required
// throughout the project
/////////////////////////////////////////////////////////////

// Represents the different types of vehicles supported by the project
enum VehicleType
{
    BIKE,
    CAR,
    TRUCK
}

// Represents different types of parking spots
enum SpotType
{
    BIKE,
    CAR,
    TRUCK
}

// Represents the current state of parking ticket
enum TicketStatus
{
    ACTIVE,
    CLOSED
}

/////////////////////////////////////////////////////////////
// Step 2 : Create Vehicle class hierarchy
// It is used to create multiple types of classes which
// represents the types of vehicles
// Concepts : Abstraction, Inheritance, Polymorphism,
// Encapsulation
/////////////////////////////////////////////////////////////

// class which represents a generic vehicle type 
abstract class Vehicle
{
    // Abstracted (Hidden) characteristics of class
    
    private String vehicleNumber;
    private VehicleType vehicleType;

    // Parameterised constructor

    public Vehicle(String vehicleNumber, VehicleType vehicleType)
    {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    // Concrete getter method
    public VehicleType getVehicleType()
    {
        return this.vehicleType;
    }

    // Concrete getter method
    public String getVehicleNumber()
    {
        return this.vehicleNumber;
    }

    // Every concrete class will provide its own definition
    public abstract void display();
}

// Class which represents the Vehicle type as Bike
class Bike extends Vehicle
{
    // Parameterised constructor
    public Bike(String vehicleNumber)
    {
        // Calls Vehicle class constructor
        super(vehicleNumber,VehicleType.BIKE);
    }

    // Method overriding
    @Override
    public void display()
    {
        System.out.println("Bike : "+getVehicleNumber());
    }
}

// Class which represents the Vehicle type as Car
class Car extends Vehicle
{
    // Parameterised constructor
    public Car(String vehicleNumber)
    {
        // Calls Vehicle class constructor
        super(vehicleNumber,VehicleType.CAR);
    }

    // Method overriding
    @Override
    public void display()
    {
        System.out.println("Car : "+getVehicleNumber());
    }
}

// Class which represents the Vehicle type as Truck
class Truck extends Vehicle
{
    // Parameterised constructor
    public Truck(String vehicleNumber)
    {
        // Calls Vehicle class constructor
        super(vehicleNumber,VehicleType.TRUCK);
    }

    // Method overriding
    @Override
    public void display()
    {
        System.out.println("Truck : "+getVehicleNumber());
    }
}

/////////////////////////////////////////////////////////////
// Step 3 : Create VehicleFactory class
// It is used to centralize the creation of vehicle objects
// Concepts : Factory Design Pattern
/////////////////////////////////////////////////////////////

class VehicleFactory
{
    // Creates and return the desired class object

    public static Vehicle creatVehicle(VehicleType type, String number)
    {
        switch(type)
        {
            case BIKE:
                return new Bike(number);
            
            case CAR:
                return new Car(number);
            
            case TRUCK:
                return new Truck(number);

            default:
                throw new IllegalArgumentException("Invalid Vehicle type");
        }
    }
}

/////////////////////////////////////////////////////////////
// Step 4 : Create ParkingSpot Hierarchy
// It is used to create hierarchy of Parking Spots
// Concepts : Encapsulation, Abstraction, Inheritance, Polymorphism
/////////////////////////////////////////////////////////////

abstract class ParkingSpot
{
    // Unique number for parking spot (Primary Key)
    private int spotNumber;

    // Type of parking spot
    private SpotType spotType;

    // Indicates whether spot is currently occupied or not
    private boolean occupied;

    // Stores information about the vehicle
    private Vehicle vehicle;

    // Parameterized constructor
    public ParkingSpot(int spotNumber, SpotType spotType)
    {
        this.spotNumber = spotNumber;
        this.spotType = spotType;

        // Initialized with default values
        this.occupied = false;
        this.vehicle = null;
    }

    public int getSpotNumber()
    {
        return this.spotNumber;
    }

    public SpotType getSpotType()
    {
        return this.spotType;
    }

    public boolean isOccupied()
    {
        return this.occupied;
    }

    public Vehicle getVehicle()
    {
        return this.vehicle;
    }

    // It is used to park the vehicle
    public void parkVehicle(Vehicle vehicle)
    {
        if(this.occupied == true)
        {
            throw new RuntimeException("Parking spot is already occupied");
        }
        else
        {
            this.vehicle = vehicle;
            this.occupied = true;
        }
    }

    public Vehicle removeVehicle()
    {
        if(this.occupied == true)
        {
            Vehicle temp = vehicle;

            this.vehicle = null;
            this.occupied = false;

            return temp;
        }
        else
        {
            throw new RuntimeException("Parking spot is already empty");
        }
    }

    // This method decides whether we can park it in the spot or not
    public abstract boolean canFitVehicle(Vehicle vehicle);

    public void display()
    {
        System.out.println("Spot : "+spotNumber+"["+spotType+"]");

        if(this.occupied == true)
        {
            System.out.println("Occupied by : "+vehicle.getVehicleNumber());
        }
        else
        {
            System.out.println("Spot is available");
        }
    }
}   // End of ParkingSpot class

class BikeSpot extends ParkingSpot
{
    public BikeSpot(int spotNumber)
    {
        super(spotNumber, SpotType.BIKE);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.BIKE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

class CarSpot extends ParkingSpot
{
    public CarSpot(int spotNumber)
    {
        super(spotNumber, SpotType.CAR);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.CAR)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

class TruckSpot extends ParkingSpot
{
    public TruckSpot(int spotNumber)
    {
        super(spotNumber, SpotType.TRUCK);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.TRUCK)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

/////////////////////////////////////////////////////////////
// Step 5 : ParkingObserver class
// It is used to automatically update display board when the
// parking availability changes
// Concepts : Observer
/////////////////////////////////////////////////////////////

interface ParkingObserver
{
    void update();
}

/////////////////////////////////////////////////////////////
// Step 6 : ParkingFloor class
// It is used to manage parking floor
// Concepts : Composition, ArrayList, Object Management
/////////////////////////////////////////////////////////////

class ParkingFloor
{
    // Unique floor number
    private int floorNumber;

    // Collection of all parking spots
    private List <ParkingSpot> parkingSpots;

    // Collection of observers registered for the floor
    private List <ParkingObserver> observers;

    public ParkingFloor(int floorNumber)
    {
        this.floorNumber = floorNumber;

        this.parkingSpots = new ArrayList <> ();

        this.observers = new ArrayList <> ();
    }

    public int getFloorNumber()
    {
        return this.floorNumber;
    }

    public void addParkingSpot(ParkingSpot spot)
    {
        parkingSpots.add(spot);
    }

    public void addObserver(ParkingObserver observer)
    {
        observers.add(observer);
    }

    private void notifyObservers()
    {
        for(ParkingObserver observer : observers)
        {
            observer.update();
        }
    }

    // Method is going to search parking spot for specific type of vehicle
    public ParkingSpot findAvailableSpot(Vehicle vehicle)
    {
        for(ParkingSpot spot : parkingSpots)
        {
            if(!spot.isOccupied() && spot.canFitVehicle(vehicle))
            {
                return spot;
            }
        }   

        return null;
    }

    // Called when new vehicle gets parked
    public void occupySpot(ParkingSpot spot, Vehicle vehicle)
    {
        // allocate spot for the vehicle
        spot.parkVehicle(vehicle);

        // notify all observers about the availability of spots
        notifyObservers();
    }

    public void releaseSpot(ParkingSpot spot)
    {
        // release the already allocated spot
        spot.removeVehicle();

        // notify all observers about the availability of spots
        notifyObservers();
    }

    public int getAvailableCount(SpotType type)
    {
        int count = 0;

        for(ParkingSpot spot : parkingSpots)
        {
            if(spot.getSpotType() == type && !spot.isOccupied())
            {
                count++;
            }
        }

        return count;
    }

    // Display all parking spots on specific floor
    public void displayFloor()
    {
        System.out.println();

        System.out.println("Floor : "+floorNumber);

        for(ParkingSpot spot : parkingSpots)
        {
            spot.display();
        }
    }
}

/////////////////////////////////////////////////////////////
// Step 7 : Create ParkingDisplayBoard class
// It is used to create a class which displays the parking status

// Subject  -> ParkingFloor
// Observer -> ParkingDisplayBoard

// Note : Any observer is going to observe the subject
// There will be multiple observers for one subject

// Concepts : Observer Design Pattern
/////////////////////////////////////////////////////////////

class ParkingDisplayBoard implements ParkingObserver
{
    // Floor whose availability is displayed by this board
    private ParkingFloor floor;

    public ParkingDisplayBoard(ParkingFloor floor)
    {
        this.floor = floor;
    }

    // Automatically called whenever floor availability changes
    @Override
    public void update()
    {
        System.out.println();
        System.out.println("------- Display Board -------");

        System.out.println("Floor : "+floor.getFloorNumber());
        System.out.println("Available Bike spots : "+floor.getAvailableCount(SpotType.BIKE));
        System.out.println("Available Car spots : "+floor.getAvailableCount(SpotType.CAR));
        System.out.println("Available Truck spots : "+floor.getAvailableCount(SpotType.TRUCK));

        System.out.println("-----------------------------");
        System.out.println();

    }

}

// We can create new observers for the same subject 
/* 
    class ParkingWebsite implements ParkingObserver
    {
        public void Update()
        {
            
        }
    }
*/

/////////////////////////////////////////////////////////////
// Step 8 : Create ParkingStrategy Class/Interface

// It is used to create a class ParkingStrategy which is
// responsible to decide the parking spot selection

// Concepts : Strategy Design Pattern
/////////////////////////////////////////////////////////////

// Defines a common concepts for parking spot selection algorithm
interface ParkingStrategy
{
    ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}

// Selects the first available parking spot
class FirstAvailableParkingStrategy implements ParkingStrategy
{
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle)
    {   
        // Iterate over all available floors
        for(ParkingFloor floor : floors)
        {
            ParkingSpot spot = floor.findAvailableSpot(vehicle);

            if(spot != null)
            {
                return spot;
            }
        }

        return null;
    }
    
}

/*
    class NearestAvailableParkingStrategy implements ParkingStrategy
    {
    
    }
*/

/////////////////////////////////////////////////////////////
// Step 9 : Create PricingStrategy Class/Interface

// It is used to create a class PricingStrategy 
// It keeps the pricing algorithm independent of exit logic

// Concepts : Strategy Design Pattern
/////////////////////////////////////////////////////////////

interface PricingStrategy
{
    double calculatePrice(Vehicle vehicle, long hours);
}

class NormalPricingStrategy implements PricingStrategy
{
    @Override
    public double calculatePrice(Vehicle vehicle, long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE:
                return hours * 20;

            case CAR:
                return hours * 50;

            case TRUCK:
                return hours * 100;

            default:
                return 0;
        }
    }
}

class WeekendPricingStrategy implements PricingStrategy
{
    @Override
    public double calculatePrice(Vehicle vehicle, long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE:
                return hours * 40;

            case CAR:
                return hours * 100;

            case TRUCK:
                return hours * 200;

            default:
                return 0;
        }
    }
}

/////////////////////////////////////////////////////////////
// Step 10 : Create PaymentStrategy Class/Interface

// It is used to create a class PaymentStrategy 
// It supports different types of payment methods

// Concepts : Strategy Design Pattern
/////////////////////////////////////////////////////////////

// Common contract for all payment methods
interface PaymentStrategy
{
    void pay(double amount);
}

class UPIPayment implements PaymentStrategy
{
    @Override
    public void pay(double amount)
    {
        System.out.println("UPI payment successful : Rs. "+amount);
    }
}

class CardPayment implements PaymentStrategy
{
    @Override
    public void pay(double amount)
    {
        System.out.println("Card payment successful : Rs. "+amount);
    }
}

class CashPayment implements PaymentStrategy
{
    @Override
    public void pay(double amount)
    {
        System.out.println("Cash payment successful : Rs. "+amount);
    }
}

/////////////////////////////////////////////////////////////
// Step 11 : Create ParkingTicket Class

// It is used to represent one complete parking transaction

/////////////////////////////////////////////////////////////

class ParkingTicket
{   
    // Used for generating unique tickets
    private static int counter = 1000;

    // Ticket number for unique ticket
    private int ticketNumber;

    // Vehicle associated with that ticket
    private Vehicle vehicle;

    // Floor on which the vehicle is parked
    private ParkingFloor floor;

    // Actual spot on which the vehicle is parked 
    private ParkingSpot spot;

    // Time at which vehicle arrives
    private LocalDateTime entryTime;
    
    // Time at which vehicle exited from parking floor
    private LocalDateTime exitTime;

    // It maintains the status of the ticket    
    private TicketStatus status;

    // Parameterized constructor
    public ParkingTicket(
                            Vehicle vehicle,
                            ParkingFloor floor,
                            ParkingSpot spot
                        )
    {
        this.ticketNumber = ++counter;
        this.vehicle = vehicle;
        this.floor = floor;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }

    // Getter method for Ticket number
    public int getTicketNumber()
    {
        return this.ticketNumber;
    }

    // Getter method for vehicle
    public Vehicle getVehicle()
    {
        return this.vehicle;
    }

    // Getter method for floor
    public ParkingFloor getFloor()
    {
        return this.floor;
    }

    // Getter method for spot
    public ParkingSpot getSpot()
    {
        return this.spot;
    }

    // Getter method for entry time
    public LocalDateTime getEntryTime()
    {
        return this.entryTime;
    }

    // Getter method for exit time
    public LocalDateTime getExitTime()
    {
        return this.exitTime;
    }

    // Getter method for status
    public TicketStatus getStatus()
    {
        return this.status;
    }

    // Method gets called when vehicle is going out
    public void closeTicket()
    {
        this.exitTime = LocalDateTime.now();

        this.status =TicketStatus.CLOSED;
    }

    // Calculate the total number of hours the vehicle is parked
    public long calculateHours()
    {
        LocalDateTime endTime;

        if(exitTime == null)
        {
            endTime = LocalDateTime.now();
        }
        else
        {
            endTime = exitTime;
        }

        // Calculate the actual time
        long minutes = Duration.between(entryTime, endTime).toMinutes();

        //  Convert minutes to Hours
        long hours = minutes / 60;

        if(minutes % 60 != 0)
        {
            hours++;
        }

        if(hours == 0)
        {
            hours = 1;
        }

        return hours;
    }

    // It will display complete ticket on screen
    public void displayTicket()
    {
        System.out.println();

        System.out.println("-----------------------------");
        System.out.println("------- Parking Ticket ------");
        System.out.println("-----------------------------");

        System.out.println("Ticket Number : "+this.ticketNumber);
        System.out.println("Vehicle Number : "+this.vehicle.getVehicleNumber());        
        System.out.println("Vehicle Type : "+this.vehicle.getVehicleType());
        System.out.println("Floor Number : "+this.floor.getFloorNumber());
        System.out.println("Spot Number : "+this.spot.getSpotNumber());
        System.out.println("Entry Time : "+this.entryTime);
        System.out.println("Ticket Status : "+this.status);

        System.out.println("-----------------------------");

        System.out.println();
    }
}

/////////////////////////////////////////////////////////////
// Step 12 : Create EntryGate Class

// It is used to handle entry of a vehicle and its ticket 
// generation
/////////////////////////////////////////////////////////////

class EntryGate
{
    private int gateNumber;

    public EntryGate(int gateNumber)
    {
        this.gateNumber = gateNumber;
    }

    public int getGateNumber()
    {
        return this.gateNumber;
    }

    // It generates the new parking ticket when vehicle enters 
    public ParkingTicket generateTicket(Vehicle vehicle, ParkingFloor floor, ParkingSpot spot)
    {
        System.out.println("Vehicle entering from gate : "+this.gateNumber);

        // New parking ticket gets generated for the vehicle
        return new ParkingTicket(vehicle,floor,spot);
    }
}

/////////////////////////////////////////////////////////////
// Step 13 : Create ExitGate Class

// It is used to handle billing and payment during the
// vehicle exit

/////////////////////////////////////////////////////////////

class ExitGate
{
    private int gateNumber;

    public ExitGate(int gateNumber)
    {
        this.gateNumber = gateNumber;
    }

    public int getGateNumber()
    {
        return this.gateNumber;
    }

    // This performs complete exit operations
    public void processExit(    
                                ParkingTicket ticket, 
                                PricingStrategy pricingStrategy,
                                PaymentStrategy paymentStrategy
                            )
    {
        // Step 1 : Close the ticket and record the exit time
        ticket.closeTicket();

        // Step 2 : Calculate the parking duration
        long hours = ticket.calculateHours();

        // Step 3 : Calculate the parking charges
        double amount = pricingStrategy.calculatePrice(ticket.getVehicle(),hours);

        System.out.println();

        System.out.println("Vehicle exiting from gate : "+gateNumber);
        System.out.println("Parking Duration : "+hours);
        System.out.println("Parking charges : "+amount);

        // Step 4 : Process the payment using selected payment strategy
        paymentStrategy.pay(amount);
    }
}

/////////////////////////////////////////////////////////////
// Step 14 : Create ParkingLot Class

// Pattern : Singleton pattern

// This class is the main controller of complete parking system
/////////////////////////////////////////////////////////////

// Singleton class
class ParkingLot
{
    // instance of class
    private static ParkingLot instance;

    // Store the parking lot name
    private String parkingLotName;

    // Store all floors 
    private List <ParkingFloor> floors;

    // Maps the ticket number with active parking slot
    private Map <Integer, ParkingTicket> activeTickets;

    // Maps vehicle number with active tickets
    // Used for searching vehicle
    // It prevents duplicate parking
    private Map <String, ParkingTicket> vehicleTicketMap;

    // Algorithm used for selecting Parking spot
    private ParkingStrategy parkingStrategy;

    // Algorithm used for calculating parking charges
    private PricingStrategy pricingStrategy;

    // private Constructor for singleton class
    private ParkingLot()
    {
        floors = new ArrayList<>();

        activeTickets = new HashMap<>();

        vehicleTicketMap = new HashMap<>();

        // Default parking strategy
        parkingStrategy = new FirstAvailableParkingStrategy();

        // Default pricing strategy
        pricingStrategy = new NormalPricingStrategy();
    }

    // Method to return the singleton class object
    public static synchronized ParkingLot getInstance()
    {
        if(instance == null)
        {
            instance = new ParkingLot();
        }

        return instance;
    }

    // Used to set name for complete parking lot
    public void setParkingLotName(String parkingLotName)
    {
        this.parkingLotName = parkingLotName;
    }

    // Used to add new parking floor 
    public void addFloor(ParkingFloor floor)
    {
        // Insert in arrayList
        floors.add(floor);
    }

    // This method returns list of all floors
    public List<ParkingFloor> getFloors()
    {
        return floors;
    }

    // This method can be used to change the default parking strategy
    public void setParkingStrategy(ParkingStrategy strategy)
    {
        this.parkingStrategy = strategy;
    }
    
    // This method can be used to change the default pricing strategy
    public void setPricingStrategy(PricingStrategy strategy)
    {
        this.pricingStrategy = strategy;
    }

    /*
        Algorith for parking the vehicle:

            Check Duplicate Vehicle
                    |
            Find available spot
                    |
            Identify floor for vehicle
                    |
            Occupy spot for vehicle
                    |
            Generate ticket for vehicle
                    |
            Store the final ticket
        

    */
    public ParkingTicket parkVehicle(
                                        Vehicle vehicle,
                                        EntryGate entryGate
                                    )
    {
        // Step 1 : Prevent the same vehicle for being parked multiple times
        if(vehicleTicketMap.containsKey(vehicle.getVehicleNumber()))
        {
            System.out.println("This vehicle is already parked");

            throw new RuntimeException("This vehicle is already parked");
        }
        
        // Step 2 : Find the available spot

        ParkingSpot spot = parkingStrategy.findSpot(floors, vehicle);

        // If there is no empty spot
        if(spot == null)
        {
            throw new RuntimeException("Parking is full");
        }

        // Step 3 : Identify the exact floor for the vehicle

        ParkingFloor selectedFloor = null;

        for(ParkingFloor floor : floors)
        {
            ParkingSpot temp = floor.findAvailableSpot(vehicle);

            if(temp == spot)
            {
                selectedFloor = floor;
                break;
            }
        }

        if(selectedFloor == null)
        {
            throw new RuntimeException("Unable to identify floor");
        }

        // Step 4 : Occupy the spot

        selectedFloor.occupySpot(spot, vehicle);

        // Step 5 : Generate parking ticket from entry gate

        ParkingTicket ticket = entryGate.generateTicket(vehicle, selectedFloor, spot);

        // Step 6 : Store the ticket using ticket number

        activeTickets.put(ticket.getTicketNumber(), ticket);

        // Step 7 : Store ticket using vehicle number

        vehicleTicketMap.put(vehicle.getVehicleNumber(), ticket);

        return ticket;
    }

    /*
        Find Ticket
            |
        Process Exit
            |
        Calculate Charges
            |
        Payment
            |
        Release Spot
            |
        Remove active records
    */

    public void removeVehicle(
                                int ticketNumber,
                                ExitGate exitGate,
                                PaymentStrategy paymentStrategy
                             )
    {
        // Step 1 : Find active ticket using ticket number
        ParkingTicket ticket = activeTickets.get(ticketNumber);

        if(ticket == null)
        {
            throw new RuntimeException("There is no such ticket");
        }

        // Step 2 : Perform billing and payment
        exitGate.processExit(ticket, pricingStrategy, paymentStrategy);

        // Step 3 : Release the occupied spot
        ticket.getFloor().releaseSpot(ticket.getSpot());

        // Step 4 : Remove ticket
        activeTickets.remove(ticketNumber);

        // Step 5 : Remove vehicle from active vehicle
        vehicleTicketMap.remove(ticket.getVehicle().getVehicleNumber());

        System.out.println("Vehicle removed successfully");
    }

    // Search the specified method
    public ParkingTicket searchVehicle(String vehicleNumber)
    {
        return vehicleTicketMap.get(vehicleNumber);
    }

    // Display complete parking lot information
    public void displayParkingLot()
    {
        System.out.println();
        System.out.println("---------------------------------------------");
        System.out.println("------------ Parking Lot Details ------------");
        System.out.println("---------------------------------------------");

        for(ParkingFloor floor : floors)
        {
            floor.displayFloor();
        }
    }
}   // End of ParkingLot class
