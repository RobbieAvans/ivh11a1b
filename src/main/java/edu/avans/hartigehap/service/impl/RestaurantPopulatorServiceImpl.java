package edu.avans.hartigehap.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.avans.hartigehap.domain.ConcreteHallReservation;
import edu.avans.hartigehap.domain.Customer;
import edu.avans.hartigehap.domain.DiningTable;
import edu.avans.hartigehap.domain.Drink;
import edu.avans.hartigehap.domain.FoodCategory;
import edu.avans.hartigehap.domain.Hall;
import edu.avans.hartigehap.domain.HallOption;
import edu.avans.hartigehap.domain.HallReservation;
import edu.avans.hartigehap.domain.HallReservationOption;
import edu.avans.hartigehap.domain.Meal;
import edu.avans.hartigehap.domain.Restaurant;
import edu.avans.hartigehap.repository.CustomerRepository;
import edu.avans.hartigehap.repository.FoodCategoryRepository;
import edu.avans.hartigehap.repository.HallOptionRepository;
import edu.avans.hartigehap.repository.HallRepository;
import edu.avans.hartigehap.repository.MenuItemRepository;
import edu.avans.hartigehap.repository.RestaurantRepository;
import edu.avans.hartigehap.service.RestaurantPopulatorService;

@Service("restaurantPopulatorService")
@Repository
@Transactional
public class RestaurantPopulatorServiceImpl implements RestaurantPopulatorService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HallOptionRepository hallOptionRepository;
    @Autowired
    private HallRepository hallRepository;

    private List<Meal> meals = new ArrayList<>();
    private List<FoodCategory> foodCats = new ArrayList<>();
    private List<Drink> drinks = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<HallOption> hallOptions = new ArrayList<>();

    /**
     * menu items, food categories and customers are common to all restaurants
     * and should be created only once. Although we can safely assume that the
     * are related to at least one restaurant and therefore are saved via the
     * restaurant, we save them explicitly anyway
     */
    private void createCommonEntities() {
        // create FoodCategories
        createFoodCategory("low fat");
        createFoodCategory("high energy");
        createFoodCategory("vegatarian");
        createFoodCategory("italian");
        createFoodCategory("asian");
        createFoodCategory("alcoholic drinks");
        createFoodCategory("energizing drinks");

        // create Meals
        int lowFat = 0;
        int highEnergy = 1;
        int vegatarian = 2;
        int italian = 3;
        
        int spaghettiPrice = 8;
        int macaroniPrice = 8;
        int canneloniPrice = 9;
        int pizzaPrice = 9;
        int carpaccioPrice = 7; 
        int ravioliPrice = 8;
        
        createMeal("spaghetti", "spaghetti.jpg", spaghettiPrice, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(italian), foodCats.get(highEnergy)));
        createMeal("macaroni", "macaroni.jpg", macaroniPrice, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(italian), foodCats.get(highEnergy)));
        createMeal("canneloni", "canneloni.jpg", canneloniPrice, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(italian), foodCats.get(highEnergy)));
        createMeal("pizza", "pizza.jpg", pizzaPrice, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(italian), foodCats.get(highEnergy)));
        createMeal("carpaccio", "carpaccio.jpg", carpaccioPrice, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(italian), foodCats.get(lowFat)));
        createMeal("ravioli", "ravioli.jpg", ravioliPrice, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(italian), foodCats.get(highEnergy), foodCats.get(vegatarian)));

        // create Drinks
        int alcoholicDrinks = 5;
        int energizingDrinks = 6;
        
        int DrinkPrice = 1;
        
        createDrink("beer", "beer.jpg", DrinkPrice, Drink.Size.LARGE, Arrays.<FoodCategory> asList(foodCats.get(alcoholicDrinks)));
        createDrink("coffee", "coffee.jpg", DrinkPrice, Drink.Size.MEDIUM, Arrays.<FoodCategory> asList(foodCats.get(energizingDrinks)));

        // create Customers
        byte photo1 = 127;
        byte photo2 = -128;
        
        byte[] photo = new byte[] { photo1, photo2, 0 };
        createCustomer("peter", "limonade", "peterlimonade@gmail.com", new DateTime(), 1, "description", photo);
        createCustomer("barry", "batsbak", "barrybatsbak@hotmail.com", new DateTime(), 1, "description", photo);
        createCustomer("piet", "bakker", "pietbakker@gmail.com", new DateTime(), 1, "description", photo);
        createCustomer("piet", "bakker", "pietbakker@hotmail.com", new DateTime(), 1, "description", photo);
        createCustomer("piet", "bakker", "pietbakker@live.nl", new DateTime(), 1, "description", photo);

        double wifiPrice = 5.0;
        double djPrice = 50.0;
        
        createHallOptions("Hall", 0.0);
        createHallOptions("Wifi", wifiPrice);
        createHallOptions("DJ", djPrice);

        // Create Hall
        int hallSeats = 180;
        double hallPrice=100;
        
        Hall hall = new Hall("Grote zaal", hallSeats, hallPrice);
        hallRepository.save(hall);

        // Decorate reservation
        int hallId = 0;
        int wifiId = 1;
        int djId = 2;
        
        HallReservation reservation = new ConcreteHallReservation(hallOptions.get(hallId), hall);
        HallReservation hallOption1 = new HallReservationOption(reservation, hallOptions.get(wifiId));
        HallReservation hallOption2 = new HallReservationOption(hallOption1, hallOptions.get(djId));

        hall.addReservation(hallOption2);
        hallRepository.save(hall);
    }

    private void createHallOptions(String description, Double price) {
        HallOption hallOption = new HallOption(description, price);
        hallOption = hallOptionRepository.save(hallOption);
        hallOptions.add(hallOption);
    }

    private void createFoodCategory(String tag) {
        FoodCategory foodCategory = new FoodCategory(tag);
        foodCategory = foodCategoryRepository.save(foodCategory);
        foodCats.add(foodCategory);
    }

    private void createMeal(String name, String image, int price, String recipe, List<FoodCategory> foodCats) {

        Meal meal = new Meal(name, image, price, recipe);
        // as there is no cascading between FoodCategory and MenuItem (both
        // ways), it is important to first
        // save foodCategory and menuItem before relating them to each other,
        // otherwise you get errors
        // like "object references an unsaved transient instance - save the
        // transient instance before flushing:"
        meal.addFoodCategories(foodCats);
        meal = menuItemRepository.save(meal);
        meals.add(meal);
    }

    private void createDrink(String name, String image, int price, Drink.Size size, List<FoodCategory> foodCats) {
        Drink drink = new Drink(name, image, price, size);
        drink = menuItemRepository.save(drink);
        drink.addFoodCategories(foodCats);
        drinks.add(drink);
    }

    private void createCustomer(String firstName, String lastName, String email, DateTime birthDate, int partySize,
            String description, byte[] photo) {
        Customer customer = new Customer(firstName, lastName, email, birthDate, partySize, description, photo);
        customers.add(customer);
        customerRepository.save(customer);
    }

    private void createDiningTables(int numberOfTables, Restaurant restaurant) {
        for (int i = 0; i < numberOfTables; i++) {
            DiningTable diningTable = new DiningTable(i + 1);
            diningTable.setRestaurant(restaurant);
            restaurant.getDiningTables().add(diningTable);
        }
    }

    private Restaurant populateRestaurant(Restaurant restaurant) {

        // will save everything that is reachable by cascading
        // even if it is linked to the restaurant after the save
        // operation
        Restaurant savedRestaurand = restaurantRepository.save(restaurant);

        // every restaurant has its own dining tables
        int numberOfTables = 5;
        createDiningTables(numberOfTables, savedRestaurand);

        // for the moment every restaurant has all available food categories
        for (FoodCategory foodCat : foodCats) {
            savedRestaurand.getMenu().getFoodCategories().add(foodCat);
        }

        // for the moment every restaurant has the same menu
        for (Meal meal : meals) {
            savedRestaurand.getMenu().getMeals().add(meal);
        }

        // for the moment every restaurant has the same menu
        for (Drink drink : drinks) {
            savedRestaurand.getMenu().getDrinks().add(drink);
        }

        // for the moment, every customer has dined in every restaurant
        // no cascading between customer and restaurant; therefore both
        // restaurant and customer
        // must have been saved before linking them one to another
        for (Customer customer : customers) {
            customer.getRestaurants().add(savedRestaurand);
            savedRestaurand.getCustomers().add(customer);
        }

        return savedRestaurand;
    }

    @Override
    public void createRestaurantsWithInventory() {

        createCommonEntities();

        Restaurant restaurant = new Restaurant(HARTIGEHAP_RESTAURANT_NAME, "deHartigeHap.jpg");
        restaurant = populateRestaurant(restaurant);

        restaurant = new Restaurant(PITTIGEPANNEKOEK_RESTAURANT_NAME, "dePittigePannekoek.jpg");
        restaurant = populateRestaurant(restaurant);

        restaurant = new Restaurant(HMMMBURGER_RESTAURANT_NAME, "deHmmmBurger.jpg");
        restaurant = populateRestaurant(restaurant);
    }
}
