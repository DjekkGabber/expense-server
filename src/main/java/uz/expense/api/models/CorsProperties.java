package uz.expense.api.models;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;


public class CorsProperties {

    private PropertiesConfiguration config;
    private boolean serverStarted;

    public CorsProperties() {
        try {
            config = new PropertiesConfiguration("cors.properties");
            config.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException ex) {
            ex.printStackTrace();
        }

    }

    public PropertiesConfiguration getConfig() {
        return config;
    }

    public boolean isServerStarted() {
        return serverStarted;
    }

    public void setServerStarted(boolean serverStarted) {
        this.serverStarted = serverStarted;
    }
}
