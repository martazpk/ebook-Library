package pl.marta.kopp.view;

import pl.marta.kopp.domain.service.BorrowingService;
import pl.marta.kopp.library.BorrowingController;
import pl.marta.kopp.library.ReturnController;
import pl.marta.kopp.login.LoginController;
import pl.marta.kopp.login.LoginView;
import pl.marta.kopp.persistence.BookStorage;
import pl.marta.kopp.registration.RegistrationController;
import pl.marta.kopp.registration.RegistrationView;

public class EbookView {
    private final SystemInterface systemInterface;
    private final MainMenu mainMenu;
    private final LoginView loginView;
    private final RegistrationView registrationView;


    public EbookView(SystemInterface systemInterface, LoginController loginController, RegistrationController registrationController,
                     BorrowingController borrowingController, ReturnController returnController, BookStorage bookStorage,BorrowingService borrowingService) {
        this.systemInterface = systemInterface;
        mainMenu = new MainMenu(systemInterface);
        loginView = new LoginView(systemInterface, loginController,borrowingController,returnController,bookStorage);
        registrationView = new RegistrationView(systemInterface,registrationController);
    }

    public void show() {
        mainMenu.show();
        try {
            int option = Integer.parseInt(systemInterface.read());
            if (option == 1) {
                loginView.show();

            } else if (option == 2) {
                registrationView.show();
                show();
            } else if (option == 3) {
                System.exit(0);
            } else {
                aFailureChoice();
            }
        } catch (NumberFormatException e) {
            aFailureChoice();

        }
    }

    private void aFailureChoice() {
        systemInterface.display("Niepoprawny wybór. Wybierz ponownie");
        mainMenu.show();
    }
}
