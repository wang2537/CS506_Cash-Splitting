# Cash-Splitting
Spring+MySQL backend
# Database Configuration

## Linux:
### terminal
1. install mysql
2. [FOR NOW] use script in CS506_Cash-Splitting/sql/db_init.sql to initialize user, db, and tables:
    SOURCE .../sql/db_init.sql;

PS:
password is changed to '12345678' for 'root'@'localhost';
userdb and hivedb are created with 2 dummy entries for testing;
[see CS506-spike-exercise/sql/db_init.sql for details]


### intellij (or terminal for those who are too bold)
3. Link database to this project: modify CS506_Cash-Splitting/src/main/resources/application.properties [if needed]
4. Link database to intellij: intellij->View->Tool Window->Database: click "+" to add MySQL database (e.g.:database=localhost:3306/spiketester)

PS: default_port=3306, db_name_initialized_with_db_init.sql=cash_splittingTester

## Windows:
### Installation
1. Download mysql server for windows at: https://dev.mysql.com/downloads/installer/
2. In "Select Product and Feature": select and install MySQL Servers->MySQL Server->MySQL Server 8.0->MySQL Server 8.0.20 (or whatever, they all work)
3. Add database with user=root, password=12345678 (or whatever, but change setting in application.properties if use other password)
### Intellij
4. Link database to this project: modify CS506_Cash-Splitting/src/main/resources/application.properties [if needed]
5. Link database to intellij: intellij->View->Tool Window->Database: click "+" to add MySQL database (e.g.:database=localhost:3306/spiketester)
6. run CS506_Cash-Splitting/sql/db_init.sql from intellij on selected the database just added (e.g. localhost:3306...)

PS: default_port=3306, db_name_initialized_with_db_init.sql=cash_splittingTester


#
# Run
## All systems
### backend-only
In intellij, navigate to CS506_Cash-Splitting/src/main/java/com/spike/spike/SpikeApplication, hit run (shift + F10);
If no error message in console, application is running;
Access backend by opening 'localhost:<port_number>/api/hive' or 'localhost:<port_number>/api/user' in a browser

PS:
default port_number is 8181; if occupied, change port_number in application.properties by adding:
server.port=<your_port_number>

### frontend as well
Run backend;
In terminal, navigate to Cash-Splitting-frontend/,

run:
npm install && npm start
(it will start in a browser and link with backend _automatically_ if configured properly)
