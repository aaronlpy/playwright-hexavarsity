package testCases.pomTests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutomationExerciseAPITest {


    static Playwright playwright;
    static APIRequestContext request;
    APIResponse response;

    @BeforeAll
    public static void setup() {
        playwright = Playwright.create();
    }

    @AfterAll
    public static void closePlaywright() {
        playwright.close();
    }

    @BeforeEach
    public void createContext() throws Exception {
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://automationexercise.com/"));
    }

    @Test
    @DisplayName("API Get Products list")
    @Order(1)
    public void getProductsList() throws Exception {
        try {

            response = request.get("/api/productsList");
            assertThat(response).isOK();

            JsonObject json = (JsonObject) JsonParser.parseString(response.text());
            JsonArray products = json.getAsJsonArray("products");
            for (JsonElement item : products) {
                JsonObject product = item.getAsJsonObject();
                System.out.printf("Id: %s - %s - price: %s\n", product.get("id"), product.get("name"), product.get("price"));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void postANewProduct() {
        response = request.post("/api/productsList");
        JsonObject json = (JsonObject) JsonParser.parseString(response.text());
        System.out.println(json);
        assert json.get("message").getAsString().equals("This request method is not supported.");
        assert json.get("responseCode").getAsInt() == 405;
        assertThat(response).not();


    }


    @Test
    @Order(3)
    public void getUserName(){
        response = request.get("api/getUserDetailByEmail?email=joe.doe27@email.com");
        JsonObject json = (JsonObject) JsonParser.parseString(response.text());
        JsonObject user = json.getAsJsonObject("user");
        System.out.println(user.get("email"));
    }




}
