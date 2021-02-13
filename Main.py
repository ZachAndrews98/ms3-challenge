import csv
from src import Database

def main():
    CSV_FILE_NAME = str(input("Enter CSV file name: "))
    TOTAL_RECORDS = 0 # total number of records
    SUCCESSFUL_RECORDS = 0 # total number of records that had the correct number of elements
    FAILED_RECORDS = 0 # total number of records that had an issue with number or contents
    db = Database.Database("ms3Interview") # database connection
    csv_data = open("./" + CSV_FILE_NAME + ".csv") # open csv data
    for entry in csv.reader(
                    csv_data, quotechar='"', delimiter=',', 
                    quoting=csv.QUOTE_ALL): # for each record in the csv table
        TOTAL_RECORDS = TOTAL_RECORDS + 1
        if validate_record(entry): # check if record is valid
            # if valid add to database
            SUCCESSFUL_RECORDS = SUCCESSFUL_RECORDS + 1
            db.add_entry(entry)
        else:
            # if not valid add to bad csv
            FAILED_RECORDS = FAILED_RECORDS + 1
            with open(CSV_FILE_NAME + '-bad.csv', mode='a') as bad: # create/open bad record csv file
                bad_writer = csv.writer(bad, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
                bad_writer.writerow(entry) # write bad record to csv file
    print("Total Records:",TOTAL_RECORDS)
    print("Successful Records:",SUCCESSFUL_RECORDS)
    print("Failed Records:",FAILED_RECORDS)

def validate_record(entry):
    if any(item == '' for item in entry): # check if any element in entry is empty
        return False
    if len(entry) != 10: # check if there are only 10 elements in entry
        return False
    return True

if __name__ == "__main__":
    main()