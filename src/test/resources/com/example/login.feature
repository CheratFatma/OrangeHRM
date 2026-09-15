Feature: login sur OrangeHRM
    Background:
        Given visiter le site "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"
    
    @loginValid
    Scenario: Login Valide
        When saisir le username "Admin"
        And saisir password "admin123"
        And cliquer sur le bouton login
        Then verifier on est sur le dashboard 

    @loginInvalid
    Scenario: Login Invalide
        When saisir le username "Admin_fake"
        And saisir password "admin123_fake"
        And cliquer sur le bouton login
        Then verifier erreur

    @loginMyActions
    Scenario: Verifier la section My Actions 
         When saisir le username "Admin"
        And saisir password "admin123"
        And cliquer sur le bouton login
        Then la section "My_Actions" est visible 

    @loginQuickLaunch   
    Scenario: Verifier la section Quick Launch 
         When saisir le username "Admin"
        And saisir password "admin123"
        And cliquer sur le bouton login
        Then la section "Quick_Launch" est visible

