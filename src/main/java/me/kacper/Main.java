package me.kacper;

import me.kacper.database.Database;
import me.kacper.license.manager.LicenseManager;
import me.kacper.listeners.EventListener;
import me.kacper.product.manager.ProductManager;
import me.kacper.utils.ConfigReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.security.auth.login.LoginException;

public class Main {

    private final static ConfigReader configReader = new ConfigReader();
    private final static ProductManager productManager = new ProductManager();
    private final static LicenseManager licenseManager = new LicenseManager();

    private static Database database;

    public static void main(String[] args) {
        database = new Database(configReader.get("uri"));

        try {
            JDA jda = JDABuilder.createDefault(configReader.get("token"))
                    .setActivity(Activity.watching("ULicense"))
                    .addEventListeners(new EventListener())
                    .build().awaitReady();

            Guild guild = jda.getGuildById(configReader.get("guild"));

            if (guild != null){
                guild.upsertCommand("product", "Product command handler")
                        .addOption(OptionType.STRING, "action", "select an action to perform", true, true)
                        .queue();
                guild.upsertCommand("license", "License command handler")
                        .addOption(OptionType.STRING, "action", "select an action to perform", true, true)
                        .queue();
            }
        } catch (InterruptedException | LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public ConfigReader getConfigReader() {
        return configReader;
    }
    public static Database getDatabase() {
        return database;
    }

    public static ProductManager getProductManager() {
        return productManager;
    }

    public static LicenseManager getLicenseManager() {
        return licenseManager;
    }
}
