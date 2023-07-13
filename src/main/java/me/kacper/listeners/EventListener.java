package me.kacper.listeners;

import me.kacper.Main;
import me.kacper.license.License;
import me.kacper.product.Product;
import me.kacper.utils.LicenseGenerator;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

import java.util.Objects;

public class EventListener extends ListenerAdapter {

    private final LicenseGenerator licenseGenerator = new LicenseGenerator();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("product")) {
            if (event.getOptions().get(0).getAsString().equalsIgnoreCase("create")) {

                TextInput product = TextInput.create("product", "Product", TextInputStyle.SHORT)
                        .setPlaceholder("Ender product name (String)")
                        .setMaxLength(16)
                        .setRequired(true)
                        .build();

                Modal modal = Modal.create("product_creation", "Product Creation")
                        .addActionRows(ActionRow.of(product))
                        .build();
                event.replyModal(modal).queue();
            } else if (event.getOptions().get(0).getAsString().equalsIgnoreCase("delete")) {

                TextInput product = TextInput.create("product", "Product", TextInputStyle.SHORT)
                        .setPlaceholder("Ender product name")
                        .setMaxLength(100)
                        .setRequired(true)
                        .build();

                Modal modal = Modal.create("product_deletion", "Product Deletion")
                        .addActionRows(ActionRow.of(product))
                        .build();

                event.replyModal(modal).queue();
            }
        } else if (event.getName().equals("license")) {
            if (event.getOptions().get(0).getAsString().equalsIgnoreCase("create")) {

                TextInput product = TextInput.create("product", "Product", TextInputStyle.SHORT)
                        .setPlaceholder("Enter product name (String)")
                        .setMaxLength(16)
                        .setRequired(true)
                        .build();

                TextInput license = TextInput.create("license", "License", TextInputStyle.SHORT)
                        .setPlaceholder("Enter license (AUTO Generated, change if needed)")
                        .setValue(licenseGenerator.licenseCodec(licenseGenerator.license()))
                        .setRequired(true)
                        .build();

                TextInput addresses = TextInput.create("addresses", "Addresses", TextInputStyle.SHORT)
                        .setPlaceholder("Enter addresses amount, that can use the license at the same time (Number)")
                        .setRequired(true)
                        .build();

                Modal modal = Modal.create("license_creation", "License Creation")
                        .addActionRows(ActionRow.of(product), ActionRow.of(license), ActionRow.of(addresses))
                        .build();

                event.replyModal(modal).queue();
            } else if (event.getOptions().get(0).getAsString().equalsIgnoreCase("delete")) {

                TextInput product = TextInput.create("product", "Product", TextInputStyle.SHORT)
                        .setPlaceholder("Enter Product")
                        .setRequired(true)
                        .build();
                TextInput license = TextInput.create("license", "License", TextInputStyle.SHORT)
                        .setPlaceholder("Enter License")
                        .setRequired(true)
                        .build();

                Modal modal = Modal.create("license_deletion", "License Deletion")
                        .addActionRows(ActionRow.of(product), ActionRow.of(license))
                        .build();
                event.replyModal(modal).queue();
            }
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("product_creation")) {
            String product = Objects.requireNonNull(event.getValue("product")).getAsString();

            Main.getProductManager().createProduct(
                    new Product(product)
            );
            event.reply("Successfully created a product.").setEphemeral(true).queue();
        } else if (event.getModalId().equals("product_deletion")) {
            String product = Objects.requireNonNull(event.getValue("product")).getAsString();
            Main.getProductManager().deleteProduct(
                    new Product(product)
            );
            event.reply("Successfully deleted a product").setEphemeral(true).queue();
        } else if (event.getModalId().equalsIgnoreCase("license_creation")) {
            String product = Objects.requireNonNull(event.getValue("product")).getAsString();
            String license = Objects.requireNonNull(event.getValue("license")).getAsString();
            String addresses =  Objects.requireNonNull(event.getValue("addresses")).getAsString();

            License licenseClass = new License(license, Integer.parseInt(addresses), product);
            Main.getLicenseManager().createLicense(licenseClass);
            event.reply("Successfully created license for product " + product + ".").setEphemeral(true).queue();
        } else if (event.getModalId().equalsIgnoreCase("license_deletion")) {
            String product = Objects.requireNonNull(event.getValue("product")).getAsString();
            String license = Objects.requireNonNull(event.getValue("license")).getAsString();

            License licenseClass = new License(license, 0, product);
            Main.getLicenseManager().deleteLicense(licenseClass);
            event.reply("Successfully deleted a license for product " + product + ".").setEphemeral(true).queue();
        }
    }
}
