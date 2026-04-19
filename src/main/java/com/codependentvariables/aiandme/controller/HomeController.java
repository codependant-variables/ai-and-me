package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.AiAndMe;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import static javafx.application.Application.setUserAgentStylesheet;

public class HomeController {
    private final AppState appState = AppState.getInstance();

    @FXML
    private void initialize() {
        setThemeContent(appState.getIsDarkMode());
    }

    // SVG paths for dark/light mode icons
    final String darkSvg = "M29.2,26.72A12.07,12.07,0,0,1,22.9,4.44,13.68,13.68,0,0,0,19.49,4a14,14,0,0,0,0,28,13.82,13.82,0,0,0,10.9-5.34A11.71,11.71,0,0,1,29.2,26.72Z";// moon - dark mode
    final String lightSvg = "m24.131 10.835 -2.92 -1.978 0.669 -3.467c0.127 -0.645 -0.078 -1.309 -0.542 -1.777 -0.469 -0.464 -1.133 -0.669 -1.768 -0.542l-3.462 0.669 -1.973 -2.925c-0.737 -1.089 -2.534 -1.089 -3.271 0l-1.973 2.925 -3.457 -0.669c-0.649 -0.122 -1.309 0.078 -1.772 0.547s-0.669 1.128 -0.542 1.772l0.669 3.467 -2.92 1.978C0.322 11.206 0 11.816 0 12.476s0.327 1.27 0.869 1.636l2.92 1.978 -0.669 3.467c-0.127 0.645 0.078 1.309 0.542 1.772s1.118 0.669 1.772 0.542l3.457 -0.669 1.973 2.925C11.23 24.673 11.846 25 12.5 25s1.27 -0.327 1.636 -0.869l1.973 -2.925 3.462 0.669c0.654 0.132 1.309 -0.078 1.772 -0.542s0.664 -1.128 0.542 -1.772l-0.669 -3.467 2.92 -1.978c0.542 -0.366 0.869 -0.981 0.869 -1.636 -0.005 -0.664 -0.327 -1.274 -0.874 -1.646m-5.513 4.18 0.859 4.453 -4.443 -0.859 -2.534 3.755 -2.534 -3.76 -4.438 0.859 0.859 -4.453 -3.75 -2.539 3.75 -2.539 -0.859 -4.453 4.443 0.859 2.529 -3.75 2.534 3.755 4.443 -0.859 -0.859 4.448 3.75 2.539zM12.5 7.422c-2.798 0 -5.078 2.28 -5.078 5.078S9.702 17.578 12.5 17.578 17.578 15.298 17.578 12.5 15.298 7.422 12.5 7.422m0 7.813c-1.509 0 -2.734 -1.226 -2.734 -2.734s1.226 -2.734 2.734 -2.734 2.734 1.226 2.734 2.734 -1.226 2.734 -2.734 2.734";// sun - light mode
    //TODO: move to settings view when made

    @FXML
    public SVGPath themeToggleIcon;

    @FXML
    public void switchTheme() {
        boolean isDarkMode = appState.getIsDarkMode();
        appState.setIsDarkMode(!isDarkMode);
        setThemeContent(!isDarkMode);
    }

    private void setThemeContent(boolean isDarkMode) {
        themeToggleIcon.setContent(isDarkMode ? lightSvg : darkSvg);
        themeToggleIcon.setFill(isDarkMode ? Color.DARKORANGE : Color.MEDIUMSLATEBLUE);
    }

    @FXML
    private void navigateQuizLibrary() {
        Router.navigateLayout(View.QUIZ_LIBRARY);
    }

    @FXML
    private void navigateSignup() {
        Router.navigateApp(View.SIGNUP);
    }

    @FXML
    private void navigateLogin() {
        Router.navigateApp(View.LOGIN);
    }
}
