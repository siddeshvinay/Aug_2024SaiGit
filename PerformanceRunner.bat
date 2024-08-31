@echo off
REM: print new line
echo ---------------------------------------------------
echo *** Performance Test Execution with parameter ***
echo ---------------------------------------------------
REM: print new line
echo JMX File is %1
REM: print new line
echo Report Location is %2
REM: print new line
echo Given Threads are %3
REM: print new line
echo Ramup Periord is %4
REM: print new line
echo User Credential Location is %5
REM: print new line
echo Given BaseUrl is %6
REM: print new line
echo Auth0Client %7
REM: print new line

jmeter -n -t jmeter\%1 -l %2\Report.jtl -e -o %2\HTMLReport\ -JThreads=%3 -JRampup=%4 -JUserCredentialsFile=%5 -JBaseUrl=%6 -JAuth0Client=%7