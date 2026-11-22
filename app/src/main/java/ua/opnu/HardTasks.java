package ua.opnu;

import ua.opnu.util.Customer;
import ua.opnu.util.DataProvider;
import ua.opnu.util.Order;
import ua.opnu.util.Product;

import java.util.*;
import java.util.stream.Collectors;

public class HardTasks {

    private final List<Customer> customers = DataProvider.customers;
    private final List<Order> orders = DataProvider.orders;
    private final List<Product> products = DataProvider.products;

    public static void main(String[] args) {
        HardTasks tasks = new HardTasks();

        // Завдання 1
        Objects.requireNonNull(tasks.getBooksWithPrice(), "Method getBooksWithPrice() returns null").forEach(System.out::println);

        // Завдання 2
        Objects.requireNonNull(tasks.getOrdersWithBabyProducts(), "Method getOrdersWithBabyProducts() returns null").forEach(System.out::println);

        // Завдання 3
        Objects.requireNonNull(tasks.applyDiscountToToys(), "Method applyDiscountToToys() returns null").forEach(System.out::println);

        // Завдання 4
        System.out.println(Objects.requireNonNull(tasks.getCheapestBook(), "Method getCheapestBook() returns null").get());

        // Завдання 5
        Objects.requireNonNull(tasks.getRecentOrders(), "Method getRecentOrders() returns null").forEach(System.out::println);

        // Завдання 6
        DoubleSummaryStatistics statistics = Objects.requireNonNull(tasks.getBooksStats(), "Method getBooksStats() returns null");
        System.out.printf("count = %1$d, average = %2$f, max = %3$f, min = %4$f, sum = %5$f%n", statistics.getCount(), statistics.getAverage(), statistics.getMax(), statistics.getMin(), statistics.getSum());

        // Завдання 7
        Objects.requireNonNull(tasks.getOrdersProductsMap(), "Method getOrdersProductsMap() returns null").forEach((id, size) -> System.out.printf("%1$d : %2$d\n", id, size));

        // Завдання 8
        Objects.requireNonNull(tasks.getProductsByCategory(), "Method getProductsByCategory() returns null").forEach((name, list) -> System.out.printf("%1$s : %2$s\n", name, Arrays.toString(list.toArray())));
    }

    // Завдання 1: товари з id 7, 9, 16, 17, 24
    public List<Product> getBooksWithPrice() {
        return products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .filter(p -> p.getPrice() > 100)
                .toList();
    }

    // Завдання 2: замовлення, що містять товари категорії Baby
    public List<Order> getOrdersWithBabyProducts() {
        return orders.stream()
                .filter(o -> o.getProducts().stream().anyMatch(p -> p.getCategory().equals("Baby")))
                .toList();
    }

    // Завдання 3: знижка 50% на іграшки
    public List<Product> applyDiscountToToys() {
        return products.stream()
                .filter(p -> p.getCategory().equals("Toys"))
                .peek(p -> p.setPrice(p.getPrice() / 2))
                .toList();
    }

    // Завдання 4: найдешевша книга (id = 17)
    public Optional<Product> getCheapestBook() {
        return products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .min(Comparator.comparingDouble(Product::getPrice));
    }

    // Завдання 5: три останні замовлення (23, 30, 33)
    public List<Order> getRecentOrders() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();
    }

    // Завдання 6: статистика цін книг
    public DoubleSummaryStatistics getBooksStats() {
        return products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
    }

    // Завдання 7: id замовлення -> кількість товарів
    public Map<Integer, Integer> getOrdersProductsMap() {
        return orders.stream()
                .collect(Collectors.toMap(Order::getId, o -> o.getProducts().size()));
    }

    // Завдання 8: категорія -> список id товарів
    public Map<String, List<Integer>> getProductsByCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getId, Collectors.toList())));
    }

}
