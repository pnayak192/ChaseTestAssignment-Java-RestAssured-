Java API Framework using jUnit, Cucumber and RESTAssured for Technical Assignment Of Chase

1). Setup

   Pre-Requisites
   1. Install Java 8 or above and setup JAVA_HOME

   2. Install Maven and setup MAVEN_HOME

      Download the latest version of Maven
   3. Add JAVA_HOME and MAVEN_HOME to PATH in your machine according your OS requirements.
   4. Install IntelliJ community edition

      Note: Please make sure to use correct Java and maven installed location in IntelliJ editor settings.

2). Set up the automation project

   1. Build the project and install required dependencies.


      mvn clean install 

3). Run features
   execute tests from test runner

      mvn clean test
   execute individual feature files
      mvn test -Dcucumber.options="<path to feature file>"
   execute specified tags    
   mvn test -Dcucumber.filter.tags='@feature_file_name'
    
Test runner

        => Edit jUnit runner > class path: com.run.TestRunner

4). Test Coverage Highlights

    1) Test cases
        
        => We have Positive, Negative and Edge test cases added.
        => All Scenarios can be found in feature file (src/test/resources/feature)
        => The scenarios are mapped with steps in stepDefinations file (src/main/java/steps)
    2) Test Data

        => BaseURI is being passed via global.properties file
        => Json payload is being passed .json file placed under resources folder
        => Json response Schema is being validated by comparing with schema file placed under resources folder
    3) Response Assertions

        => I have used both JsonPath and Pojo implementations.

5). Test Run Report

    => Post running the test cases, reports will be generated in (target/html-reports)
    => One can open the html report in browser and view it.
