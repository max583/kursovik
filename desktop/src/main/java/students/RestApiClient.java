package students;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.*;
public class RestApiClient {
    private static RestApiClient INSTANCE;
    HttpClient client = new DefaultHttpClient();
    private RestApiClient() {
    }

    public static RestApiClient getClient() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new RestApiClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }

    public boolean checkLogin(String username, String password, String hostname) throws Exception {
        System.out.println("checkLogin: " + username + " " + password + " " + hostname);
        HttpGet request = new HttpGet("http://"+hostname+"/checkUserExists?username=" + username + "&password=" + password);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            return false;
        }

        return true;
    }

}
