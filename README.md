JUnit4 Selenium Tests with SauceLabs
====================================

These tests were/are being created by Paul Roth and Corey Wu to aid Polar Mobile in testing their web platform.
We hope that others interested in automating their test suites may find the examples contained in this repository to be of use.

Setup
-----

1. Create a java project in your IDE of choice (we'll assume you're using Eclipse for these setup instructions) and add/create one or more Selenium test files in it.

2. Add all of the JAR files in ```libs/``` to your project build path.

   NOTE: if you are testing locally and not with SauceLabs you can now proceed to step 4.

3. Start the Sauce-Connect tunnel by typing the following command in your shell:

   ```java -jar Sauce-Connect.jar <SauceLabs userID>  <SauceLabs access key>```

   If you do not have a SauceLabs account, you can sign up for a free account on ```https://saucelabs.com/```.

4. Right click on a test in the Project Explorer, then select Run As > JUnit Test.

5. SauceLabs specific: Navigate to ```https://saucelabs.com``` and view your results, a captured video of each test, and more.
   Local or SauceLabs: View the results of your tests directly in your IDE.
   

To Do
-----

-Include more asserts.

