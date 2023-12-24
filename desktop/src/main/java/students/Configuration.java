package students;

import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.lang.ClassLoader;
public class Configuration extends ClassLoader {
    private String appVersion;
    private String hostname;
    private String username;
    private static Configuration INSTANCE;

    private Configuration() throws IOException {
        final Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/Students.properties");
        properties.load(fis);
        this.appVersion = properties.getProperty("app.version");
        this.hostname = properties.getProperty("hostname");
        this.username = properties.getProperty("username");
    }

    public static Configuration getConfiguration(){
        if (INSTANCE == null) {
            try {
                INSTANCE = new Configuration();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}