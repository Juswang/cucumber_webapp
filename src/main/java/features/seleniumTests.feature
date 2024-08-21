
Feature: Start Edge
 @test
  Scenario: Starting Edge
    Given Open Edge browser and navigate to url "https://www.lambdatest.com/selenium-playground/checkbox-demo"
    Then I load Properties File "selenium.properties"
   # Then On Webpage, I double click "abtBtn"
    #run main.java to start java coordinate tools
    #Then I drag and drop "423,198" from file explorer folder path "C:\\Users\\wangd\\OneDrive\\Documents\\upload selenium Test" to web "712,440"
    #Then On Webpage, I double click "abtBtn"
    #Then On Webpage, I type "penguin" on "GoogleSearchBox" Textbox
#    Then On Webpage, I check if  "abtBtn" is displayed
 #   Then On Webpage, I hover mouse over "JavaScriptHover"
  #  Then On Webpage, I wait "3" seconds
    Then On Webpage, I scroll down until "ScrollTo"
    Then On Webpage, I wait for "5" seconds until "waitTill" is visible
    Then On Webpage, I return to the previous page
    Then On WebPage, I key enter on "<>"
    Then On WebPage, I key tab on "<>"


   # Then On Webpage, I double click "abtBtn"
