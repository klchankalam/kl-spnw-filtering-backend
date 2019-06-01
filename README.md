This is done with spring boot, to start please run the following command in terminal:

(on linux) gradlew bootRun 

or

(on windows) gradlew.bat bootRun

in project root.

Design decisions:
- in normal application, user and favourite/score should be in separate tables. In this demo they are all in user table due to the assumption of requirement
- city is in another table, from what i see the coordinates of a city is the same, i assume there would be a city selector when user create their profiles

To create WAR package, run gradlew with 'bootWar' task, similar to running the application above.

API doc is available at build/asciidoc/html5/API Documentation.html after you gradle build the project.

Sample postman script is included in root directory as well.
