
Feature: Start Edge

  Scenario: Starting Edge
    Given Open Edge browser and navigate to url "https://www.google.com/#sbfbu=1&pi="
    Then I load Properties File "selenium.properties"
   # Then On Webpage, I double click "abtBtn"
    #run main.java to start java coordinate tools
    #Then I drag and drop "423,198" from file explorer folder path "C:\\Users\\wangd\\OneDrive\\Documents\\upload selenium Test" to web "712,440"
    #Then On Webpage, I double click "abtBtn"
    Then On Webpage, I type "penguin" on "GoogleSearchBox" Textbox
    Then On Webpage, I double click "abtBtn"
