import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DtoExample {
    public static void main(String[] args) {
        dtoExample();
    }

    private static void dtoExample() {
        // Retrieve the users and their orders
        List<UserOrder> userOrder = findAllUsersMapDto();
        List<Order> orders = findAllOrdersMapDto();

        // Define the query data (users) and the data to be joined (orders)
        QueryData<UserOrder> queryData = new QueryData<>(userOrder, List.of("id"), new UserOrderGetter(), new UserOrderSetter());
        JoinedData<Order> joinedData = new JoinedData<>(orders, List.of("userId"), new OrderGetter());

        // Perform the left join
        List<UserOrder> userOrders = MergeUtils.leftJoin(
                queryData,
                joinedData,
                () -> Set.of("productName", "orderId"),
                data -> new UserOrder(data.getId(), data.getName())
        );

        // Print the joined data
        for (UserOrder user : userOrders) {
            System.out.println(user.toString());
        }
    }

    private static List<Order> findAllOrdersMapDto() {
        return Arrays.asList(
                new Order(1, 1, "Apple"),
                new Order(2, 2, "Banana"),
                new Order(3, 3, "Orange"),
                new Order(4, 1, "Grape")
        );
    }

    private static List<UserOrder> findAllUsersMapDto() {
        return Arrays.asList(
                new UserOrder(1, "John"),
                new UserOrder(2, "Jane"),
                new UserOrder(3, "Mike")
        );
    }
}
