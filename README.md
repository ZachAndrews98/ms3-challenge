# MS3 Coding Challenge Java Edition

## **Requirements**

- Java
- Gradle

## **Running**

***Note CSV file must be located at `/app/src/input/`***

To run
1. Open a command prompt/terminal in the root of the project.
2. Run `gradle run`.
3. When prompted enter in the name of the csv file placed in `/app/src/input/`.
4. Wait for execution to finish (will take a minute or two).
5. All output files (.db,.csv,.log) can be found at `/app/src/output/`


## **Purpose**

The purpose of this code is to take in a csv file and separate the lines that have ten filled cells from the rest of the data. The lines that meet that criteria are added to a database called `<csvFileName>.db`, the lines that do not are added to a csv file called `<csvFileName>-bad.csv`. The total number of records along with the number of valid/invalid records are written to a file called `<csvFileName>.log`.

## **Design**

### Assumptions

There are several assumptions that this implementation takes. The first assumption is that the data in the CSV is all to be stored within the SQLite database as text. This assumption allowed me to more easily use a PreparedStatement to insert the data in the table. I do feel that this assumption was correct due to all columns in the CSV file other than two falling into the text data type in SQLite. The two columns that did not were true or false, which in SQLite are represented as either an int 1 or int 0. This difference would not be worth using a different data type for and as such I used text for each column.

There are also two additional assumptions concerning the database, the first of which is that there will only ever be ten columns and their names will be A-J as described in the challenge requirements. Currently, this is hardcoded into the creation of the database table and, like above, was done mainly for convenience. The second assumption is that if a cell in the CSV file is empty that invalidates the row from being included in the database. If that is not a correct assumption, it is easy enough to change the validation method.

The next assumption is that the CSV file will only be located in `/src/input/`. This assumption was made mostly for ease of programming and testing and could be changed by editing the file path that is used to open the CSV file. If the CSV file is not in that folder then the program will not work.

### General Process

A generalized look at how this program looks can be seen below.

1. The user supplies the name of the csv file to be opened.
2. Objects for writing and reading data are created.
    - csv file reader
    - -bad.csv file writer
    - .log file writer
    - .db connection
3. A line is read from the csv file.
4. That line is split into segments based on commas. Commas within quotes are ignored utilizing a regular expression.
5. The array is passed to `validateRecord()` for validation.
6. If the line is valid, write the line to the database. If the line is invalid, write the line to the -bad.csv file.
7. Repeat steps 3-6 until no more lines need to be read from the input csv file.
8. Write the number of records received, successful, and failed to log file.
9. Close Objects made in step 2.

### Design Choices and Approach

My program has three files which each handle an aspect of the program:

- `Main.java`
- `Record.java`
- `Database.java`

In my development process `Main.java` served as both a testing space for the initial code as well as, like the name suggests, the main file. This is also the location for any methods that are not related to writing to a file or database, the only one being the `validateRecord()` method. `Record.java` is a consolidation of what was once two separate classes for writing the log file and the -bad.csv file. The code in those two classes was essentially identical so it made logical sense to consolidate it into a single class. The last file, `Database.java` handles the interactions with the database, which in this case is just writing each valid record to it.

There are certainly several areas where this can be improved. Currently, with a CSV file that is just over 6000 lines long, to finish running the program takes roughly 3 minutes which means that the program can process roughly 33 lines per second. This could likely be improved upon if multithreading was utilized to process lines simultaneously. Unfortunately, I do not have much experience with multithreading in java and was not able to get it working properly.

Despite this, the amount of time that is taken is fairly optimized (at least as far as I can tell, there might be areas or methods that could be used that I am not aware of). Besides the main loop, which gets the lines from the CSV file, there are only 2 loops, both of which do not run for every line in the CSV file. In the case of `validateRecord()` this is due to the return condition that comes before it and with `writeRecord()` it depends on the value returned by `validateRecord()`.