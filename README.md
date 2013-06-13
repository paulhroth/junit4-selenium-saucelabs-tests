JUnit4 Selenium Tests with SauceLabs
====================================

These tests were/are being created by Paul Roth and Corey Wu for use by Polar Mobile (or anyone else interested) in testing their Native Ad Creator tool.

Setup
-----

1. Create a java project in your IDE of choice and add one or more Selenium test files.

2. Add all of JAR files from 'libs' to your project build path.

3. Before running any SauceLabs tests, you will need to start the Sauce-Connect
tunnel by typing the following command in your shell:

'''java -jar Sauce-Connect.jar <SauceLabs userID>  <SauceLabs access key>'''

To Do
-----

-Convert our tests to support parallelization.

-Include more asserts.

