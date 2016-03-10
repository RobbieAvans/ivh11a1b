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
        createMeal("spaghetti", "spaghetti.jpg", 8, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1)));
        createMeal("macaroni", "macaroni.jpg", 8, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1)));
        createMeal("canneloni", "canneloni.jpg", 9, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1)));
        createMeal("pizza", "pizza.jpg", 9, "easy", Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1)));
        createMeal("carpaccio", "carpaccio.jpg", 7, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(0)));
        createMeal("ravioli", "ravioli.jpg", 8, "easy",
                Arrays.<FoodCategory> asList(foodCats.get(3), foodCats.get(1), foodCats.get(2)));

        // create Drinks
        createDrink("beer", "beer.jpg", 1, Drink.Size.LARGE, Arrays.<FoodCategory> asList(foodCats.get(5)));
        createDrink("coffee", "coffee.jpg", 1, Drink.Size.MEDIUM, Arrays.<FoodCategory> asList(foodCats.get(6)));

        // create Customers
        byte[] photo = new byte[] { 127, -128, 0 };
        createCustomer("peter", "limonade", "peterlimonade@gmail.com", new DateTime(), 1, "description", photo);
        createCustomer("barry", "batsbak", "barrybatsbak@hotmail.com", new DateTime(), 1, "description", photo);
        createCustomer("piet", "bakker", "pietbakker@gmail.com", new DateTime(), 1, "description", photo);
        createCustomer("piet", "bakker", "pietbakker@hotmail.com", new DateTime(), 1, "description", photo);
        createCustomer("piet", "bakker", "pietbakker@live.nl", new DateTime(), 1, "description", photo);

        createHallOptions("Hall", 0.0);
        createHallOptions("Wifi", 5.00);
        createHallOptions("DJ", 50.00);

        // Create Hall
        Hall hall = new Hall("Grote zaal", 180, 100);
        hallRepository.save(hall);

        // Decorate reservation
        HallReservation reservation = new ConcreteHallReservation(hallOptions.get(0), hall);
        HallReservation hallOption1 = new HallReservationOption(reservation, hallOptions.get(1));
        HallReservation hallOption2 = new HallReservationOption(hallOption1, hallOptions.get(2));

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
        Restaurant SavedRestaurand = restaurantRepository.save(restaurant);

        // every restaurant has its own dining tables
        createDiningTables(5, SavedRestaurand);

        // for the moment every restaurant has all available food categories
        for (FoodCategory foodCat : foodCats) {
            SavedRestaurand.getMenu().getFoodCategories().add(foodCat);
        }

        // for the moment every restaurant has the same menu
        for (Meal meal : meals) {
            SavedRestaurand.getMenu().getMeals().add(meal);
        }

        // for the moment every restaurant has the same menu
        for (Drink drink : drinks) {
            SavedRestaurand.getMenu().getDrinks().add(drink);
        }

        // for the moment, every customer has dined in every restaurant
        // no cascading between customer and restaurant; therefore both
        // restaurant and customer
        // must have been saved before linking them one to another
        for (Customer customer : customers) {
            customer.getRestaurants().add(SavedRestaurand);
            SavedRestaurand.getCustomers().add(customer);
        }

        return SavedRestaurand;
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
