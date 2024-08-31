@echo off
REM: print new line
echo -----------------------------------------
echo *** Test Execution with parameter ***
echo -----------------------------------------
REM: print new line
echo TestNG File is %1
REM: print new line
echo Browser Name is %2
REM: print new line
echo Environment is %3
REM: print new line
echo Tag Name is %4
REM: print new line
echo Execution Mode is %5
REM: print new line
echo Selected Feature is %6
REM: print new line
echo Report location is %7
REM: print new line
mvn clean install -Dproject.testngFile=%1 -Dbrowser.name=%2 -Dproject.environment=%3 -Dcucumber.filter.tags=%4 -DexecutionPlatform=%5 -Dcucumber.options="src/test/resources/features/UI/%6" -Dreport.location=%7