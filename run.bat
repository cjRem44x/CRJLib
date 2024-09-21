@echo off

set src=%cd%\src 
set bin=%cd%\bin

cd %src%
javac -d %bin% *.java 

cd %bin%
java Main

pause
