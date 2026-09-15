Feature: login sur OrangeHRM
    Background:
        Given visiter le site "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"

    @loginUS
    Scenario: Verifier la connexion et acces au Dashboard
        When saisir le username "Admin"
        And saisir password "admin123"
        And cliquer sur le bouton login
        Then verifier on est sur le dashboard
        And la section "My_Actions" est visible
        And la section "Quick_Launch" est visible

