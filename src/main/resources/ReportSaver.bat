for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set "dt=%%a"
set "YY=%dt:~2,2%" & set "YYYY=%dt:~0,4%" & set "MM=%dt:~4,2%" & set "DD=%dt:~6,2%"
set "HH=%dt:~8,2%" & set "Min=%dt:~10,2%" & set "Sec=%dt:~12,2%"

set "datestamp=%YYYY%%MM%%DD%" & set "timestamp=%HH%%Min%%Sec%"
set "fullstamp=%YYYY%-%MM%-%DD%_%HH%-%Min%-%Sec%"
echo datestamp: "%datestamp%"
echo timestamp: "%timestamp%"
echo fullstamp: "%fullstamp%"

cd ../../..

SET var=%1
echo %var%

mkdir %var%\cucumber-jvm-reporting
mkdir %var%\allure-html-report
mkdir %var%\allure-results
mkdir %var%\extent-reports
mkdir %var%\cucumber-report

Xcopy allure-html-report %var%\allure-html-report /E /H /C /I
Xcopy allure-results %var%\allure-results /E /H /C /I
Xcopy target\extent-reports %var%\extent-reports /E /H /C /I
Xcopy target\cucumber-report.html %var%\cucumber-report\
Xcopy target\cucumber-report-retry.html %var%\cucumber-report\
echo copy of all reports successfully done...