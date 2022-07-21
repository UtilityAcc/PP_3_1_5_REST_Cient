import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Web_Client {

    static RestTemplate restTemplate = new RestTemplate();
    static String baseUrl = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {

        fetchAll();
    }

    private static void fetchAll() {
        StringBuilder key = new StringBuilder();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> unCookied = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "", HttpMethod.GET, unCookied, String.class);
        responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie", String.join(";", Objects.requireNonNull(responseEntity.getHeaders().get("Set-Cookie"))));
        HttpEntity<Object> Cookied;
        User user = new User(3L, "James", "Brown", (byte) 35);
        Cookied = new HttpEntity<Object>(user, headers);
        key.append(addUser(Cookied));
        user.setName("Thomas");
        user.setLastName("Shelby");
        user.setAge((byte) 39);
        key.append(" ").append(updateUser(Cookied));
        key.append(" ").append(deleteUser(Cookied, user.getId()));
        System.out.println("key :" + key);
    }

    public static String addUser(HttpEntity<Object> requestEntity) {

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String.class);
        return response(responseEntity);
    }

    private static String updateUser(HttpEntity<Object> requestEntity) {

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, String.class);
        return response(responseEntity);
    }

    private static String deleteUser(HttpEntity<Object> requestEntity, Long id) {

        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/" + id, HttpMethod.DELETE, requestEntity, String.class);
        return response(responseEntity);
    }

    private static String response(ResponseEntity<String> responseEntity) {

        return responseEntity.getBody();
    }
}