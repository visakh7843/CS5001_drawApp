javac -cp Tests/junit-4.13.1.jar:Tests/hamcrest-core-1.3.jar:. src/main/MVCMain.java src/model/Model.java src/model/shapeAttribute.java src/view/View.java src/controller/Controller.java Tests/ModelTest.java Tests/ModelTestSuite.java Tests/ModelTestSuiteRunner.java

#Running Test Cases
echo "Running Tests now"
java -cp ../CS5001-p4-drawing:Tests/junit-4.13.1.jar:Tests/hamcrest-core-1.3.jar Tests.ModelTestSuiteRunner
