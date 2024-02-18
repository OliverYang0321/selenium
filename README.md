# Technical_Assessment

## Project structure
Under src/test/java folder, there are four test files.
- Test case 1
  - TestCaseOnePartOne.java (Verify error messages after clicking submit button directly without populating any mandatory fields)
  - TestCaseOnePartTwo.java (Verify error messages gone after populating any mandatory fields)
- Test case 2
  - TestCaseTwo.java
- Test case 3
  - TestCaseThree.java  
__Tips__: All tests will be run both on Chrome and FireFox

## CI
### Jenkins pipeline
1. Open Jenkins and click "New Item" on the dashboard
2. Enter an item name, choose "FreeStyle Project" and click "OK" button
3. Under Source Code Management section, choose git;
4. Repository URL -> https://github.com/OliverYang0321/selenium.git, Branch Specifier -> */main
5. Build Steps section, choose "Invoke top-level Maven targets", choose "Maven Version" and Goals -> "clean test"
6. Post-build Actions section, choose "Publish JUnit test result report", Test report XMLs -> "**/target/surefire-reports/*.xml"
7. Click "Save" and click "Build now"
