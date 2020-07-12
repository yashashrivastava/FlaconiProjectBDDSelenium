##BDD based Selenium project for Flaconi

I have used cucumber framework to organize the test cases.  

###To Run

Please run `TestRunner` class from `src/main/java/runner` package.

It will execute feature files from `features` package using the step definitions 
present under `steps.definitions` package.

For MainNavigationLink, I have ignored the timeout exceptions but this can be raised as
defect in normal test execution.  
