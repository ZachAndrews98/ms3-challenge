import sqlite3

class Database:

    def __init__(self,file_name):
        """ Database connection initializer """
        self.conn = sqlite3.connect(file_name + '.db') # connect to/create database
        self.c = self.conn.cursor()
        self.c.execute('''CREATE TABLE IF NOT EXISTS records \
                        (A text, B text, C text, D text, E text, \
                        F text, G text, H text, I text, J text)''') # create table for data entry

    def add_entry(self, entry):
        """ Add validated row to database """
        self.c.execute("INSERT INTO records VALUES (?,?,?,?,?,?,?,?,?,?)", entry)
        self.conn.commit()