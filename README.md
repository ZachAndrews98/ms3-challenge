# MS3 Coding Challenge Java Edition

## **Requirements**

- Java 11
- Gradle

## **Running**

To run
***Note CSV file must be located at `/app/src/input/`***
1. Open a command prompt/terminal
2. Run `gradle run`
3. When prompted enter in the name of the csv file without the '.csv' extension
4. Wait for execution to finish (will currently take several minutes)
5. All output files (.db,.csv,.log) can be found at `/app/src/output/`.

## **Purpose**

The purpose of this code is to take in a csv file and separate the lines that have ten filled cells from the rest of the data. The lines that meet that criteria are added to a database called `<csvFileName>.db`, the lines that do not are added to a csv file called `<csvFileName>-bad.csv`.

## **Design**

### Assumptions

There are several assumptions that this implementation takes. The first assumption is that the data in the csv is all to be stored withi: class and methods for writing information to the database
n the SQLite database as text. This assumption allowed for me to more easily use a PreparedStatement to insert the data in the table. I do feel that this assumption was the correct due to all columns in the csv file other than two falling into the text data type in SQLite. The two columns that did not were true or false, which in SQLite are represented as either an int 1 or int 0. This difference would not be worth using a different data type for and as such I used text for each column.

An additional assumption in relation to the database is that there will only ever be ten columns and their names will be A-J as described in the challenge requirements. Currently, this is something that is hard coded into the creation of the database table and, like above, was done mainly for convenience.

The next assumption is one of my own creation and is that the csv file will only be located in `/src/input/`. This assumption was made mostly for ease of programming and testing and could be changed by editing the file path that is used to open the csv file. If the csv file is not in that folder then the program will not work.


### Design Choices and Approach

My program has three files which each handle an aspect of the program:

- `Main.java`
- `Record.java`
- `Database.java`

In my development process `Main.java` served as both a testing space for the inital code as well as, like the name suggests, the main file. This is also the location for any methods that are not related to writing to a file or database, the only one being the `validateRecord` method. Record.java is a consolidation of what was once two separate classes for writing the log file and the -bad.csv file. The code in those two classes was essentially identical so it made logical sense to consolidate it into a single class. The last file, `Database.java`

There are certainly several areas where this can be improved. Currently, with a csv file that is just over 6000 lines long, to finish running the program takes roughly 3 minutes which means that the program is able to process roughly 33 lines per second. This could likely be improved upon if multithreading was utilized to process lines simultaneously. Unfortunately, I do not have much expericence with multithreading in java and was not able to get it working properly.