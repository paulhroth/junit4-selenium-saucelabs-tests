JUnit4 Selenium Automation Tests with SauceLabs
===============================================

These tests were/are being created by Paul Roth and Corey Wu for use by Polar Mobile (or anyone else interested) in testing their Native Ad Creator tool.

SauceLabs
---------

Before running any SauceLabs tests, you will need to start the Sauce-Connect
tunnel by typing the following command in your shell:

'''java -jar Sauce-Connect.jar polarqa d609b648-22e3-44bb-a38e-c28931df837d'''

To Do
-----

-Implement SauceREST in order to pass/fail tests instead of having them only
show up as "finished" or "error".

-Convert our tests to support parallelization.

-Include more asserts.

