package translation;

import javax.swing.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            JPanel countryPanel = new JPanel();
            Translator translator = new JSONTranslator();

            String[] countries = new String[translator.getCountryCodes().size()];
            int i = 0;
            for (String countryCode : translator.getCountryCodes()) {
                countries[i] = countryConverter.fromCountryCode(countryCode);
                i++;
            }

            JList<String> countryList = new JList<>(countries);
            JScrollPane scrollPane = new JScrollPane(countryList);

            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(scrollPane);

            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();

            for (String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(
                        languageConverter.fromLanguageCode(languageCode)
                );
            }
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = (String) languageComboBox.getSelectedItem();
                    String country = countryList.getSelectedValue();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String countryCode = countryConverter.fromCountry(country);
                    String languageCode = languageConverter.fromLanguage(language);

                    String result = translator.translate(countryCode, languageCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        });
    }
}
