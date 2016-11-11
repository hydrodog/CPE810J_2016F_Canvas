/**************************************************************************************
 * CS-561-B Assignment 1
 * Name: Shenwei Chen
 * CWID: 10404898
========================================
(a)
ReadMe:

-How to run the file:
1. Create an empty Java project, then add JDBC in the project build path. The JDBC 
version should match the version of PostgreSQL on your machine.
2. Add this file to the project.
3. modify[url],[username],[password] (##LINE 150 to 152##) to yours. 5432 is the default 
port of postgres. If you’re not using 5432 as your port, please change it.
4. Run the project and the result will be print in the console window. Both query 1 and 
query 2 will be printed.
========================================
(b)
Choice of data structure:
I choose ArrayList as the data structure. And here are the reasons:
1. ArrayList can contain various types of data and also supports user-defined type. In 
this assignment, we need to use some user-defined type of data.
2. ArrayList is a dynamic data structure, so we don't need to know the total number of
elements. Specifically, we don't know how many products are in the table, so using 
ArrayList is a good way.
3. ArrayList is easy to get element by index and get index of element.

In opposed to array, we will update the size of the array when we need to add the new element.
This will lead to higher time complexity. Or if we preallocate for all the elements, because
we don't know the exact size of the elements, this will waste lots of space in memory.
========================================
(c)
Algorithm of the program:
For query 1:

	Create List A to store Product name
	Create List B to store the data which has the max quant of the product
	Create List C to store the data which has the max quant of the product
	Create List D to store average value of the quant
	
	Get query result to ResultSet R
	while R != NULL
		Store the the data as d
		if d.prod exists in A, index = i
			if d.quant > B[i].quant
				B[i].quant = d.quant
			end
			if d.quant < C[i].quant
				c[i].quant = d.quant
			end
			D[i].total += d.quant
			D[i].count ++
		end
		else
			Create new node in List A, B, C, D
		end
		R to next row
	end
	
	// output
	len = length in A
	for i = 0 to len
		print A
		print B.quant, B.cust, B.date, B.state
		print C.quant, C.cust, C.date, C.state
		print D.avg
	end

+++++++++++++++++++++++++++++++++++++++++++++++++++++++	
For query 2:

	Create List A to store the combination of Product name and Customer name
	Create List B to store the data which has the max quant in CT
	Create List C to store the data which has the min quant in NY
	Create List D to store the data which has the min quant in NJ
	
	Get query result to ResultSet R
	while R != NULL
		Store the the data as d
		if d.custProd exists in A, index = i
			if d.state = "CT" and year between 2000 to 2005
				if d.quant > B[i].quant
					B[i].quant = d.quant
				end
			end
			if d.state = "NY"
				if d.quant < C[i].quant
					C[i].quant = d.quant
				end
			end
			if d.state = "NJ"
				if d.quant < D[i].quant
					D[i].quant = d.quant
				end
			end
		end
		else
			Create new node in List A, B, C, D
		end
		R to next row
	end
	
	//output
	len = length in A
	for i = 0 to len
		print A
		print B.quant, B.date
		print C.quant, C.date
		print D.quant, D.date
	end
**************************************************************************************/

// import API files
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// this class stores the data in one line from the sales table.
// including customer, product, day, month, year, state and quant.
class data{
	String cust, prod, state;
	int day, month, year, quant;
	public data(String cust, String prod, int day, int month, int year, String state, int quant){
		this.cust = cust;
		this.prod = prod;
		this.day = day;
		this.month = month;
		this.year = year;
		this.state = state;
		this.quant = quant;
	}
}

// this class stores the data that are necessary to compute the average quantity.
// including count, total and avg.
class computeAVG{
	int count = 0, total = 0, avg = 0;
	public computeAVG(int total){	//initialization: count = 1, total and avg are the same.
		this.count ++;
		this.total = total;
		this.avg = total;
	}
}



public class Assignment1{
	public static void main(String[] args){
		// set up database's user name, password and URL
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "024498";
		// load the driver
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Successfully loaded the driver!");
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load the driver!");
			e.printStackTrace();
		}
		
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Successfully connected to the server!");
			// get query result to ResultSet rs
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
			
			// ********************declaration for task #1********************
			
			// store every product name, and no duplication
			List<String> productName = new ArrayList<String>();
			
			// store the data which has the max quant of the product
			// same product has the same index in the productName List
			List<data> MAX = new ArrayList<data>();
			
			// store the data which has the min quant of the product
			// same product has the same index in the productName List
			List<data> MIN = new ArrayList<data>();
			
			// store the average value of every product
			// same product has the same index in the productName List
			List<computeAVG> AVG = new ArrayList<computeAVG>();
			
			// ********************declaration for task #2********************
			
			// store the combination of every customer name and product name, and no duplication
			List<String> customerProd = new ArrayList<String>();
			
			// store the data which has the max quant of the product in CT
			// has the same index in the customerProd List
			List<data> CTMAX = new ArrayList<data>();
			
			// store the data which has the min quant of the product in NY
			// has the same index in the customerProd List
			List<data> NYMIN = new ArrayList<data>();
			
			// store the data which has the min quant of the product in NJ
			// has the same index in the customerProd List
			List<data> NJMIN = new ArrayList<data>();
			
			// scan the table line by line
			while (rs.next()) {
				String cust = rs.getString("cust");
				String prod = rs.getString("prod");
				int day = rs.getInt("day");
				int month = rs.getInt("month");
				int year = rs.getInt("year");
				String state = rs.getString("state");
				int quant = rs.getInt("quant");
				// store in _data
				data _data = new data(cust, prod, day, month, year, state, quant);
				
				// ********************for task #1********************
				
				// if the name is in the List, compare the quant values
				if(productName.contains(prod)){
					// find the max value
					if(MAX.get(productName.indexOf(prod)).quant < _data.quant){
						MAX.set(productName.indexOf(prod), _data);
					}
					// find the min value
					if(MIN.get(productName.indexOf(prod)).quant > _data.quant){
						MIN.set(productName.indexOf(prod), _data);
					}
					// update the average value
					int c = ++AVG.get(productName.indexOf(prod)).count;
					int t = AVG.get(productName.indexOf(prod)).total += _data.quant;
					AVG.get(productName.indexOf(prod)).avg = t / c;
				}
				//if the name is not in List, add it, so all the Lists share the same index
				else{
					productName.add(prod);
					MAX.add(_data);
					MIN.add(_data);
					computeAVG avg = new computeAVG(_data.quant);
					AVG.add(avg);
				}
				
				// ********************for task #2********************
				
				// if the combination of names is in the List, compare the quant values
				if(customerProd.contains(cust+prod)){
					// find the max value in CT between year 2000 and 2005
					if(state.equals("CT") && year >= 2000 && year <= 2005){
						if(CTMAX.get(customerProd.indexOf(cust+prod)).quant == 0 || CTMAX.get(customerProd.indexOf(cust+prod)).quant < _data.quant)
						CTMAX.set(customerProd.indexOf(cust+prod), _data);
					}
					// find the min value in NY
					else if(state.equals("NY")){
						if(NYMIN.get(customerProd.indexOf(cust+prod)).quant == 0 || NYMIN.get(customerProd.indexOf(cust+prod)).quant > _data.quant)
						NYMIN.set(customerProd.indexOf(cust+prod), _data);
					}
					// find the min value in NJ
					else if(state.equals("NJ")){
						if(NJMIN.get(customerProd.indexOf(cust+prod)).quant == 0 || NJMIN.get(customerProd.indexOf(cust+prod)).quant > _data.quant)
						NJMIN.set(customerProd.indexOf(cust+prod), _data);
					}
				}
				// if the name combination is not in List, add it.
				else{
					customerProd.add(cust+prod);
					// null data should also be added to other states
					data _null = new data(cust, prod, 0, 0, 0, null, 0);
					// for CT
					if(state.equals("CT") && year >= 2000 && year <= 2005){
						CTMAX.add(_data);
						NYMIN.add(_null);
						NJMIN.add(_null);
					}
					// for NY
					else if(state.equals("NY")){
						CTMAX.add(_null);
						NYMIN.add(_data);
						NJMIN.add(_null);
					}
					// for NJ
					else if(state.equals("NJ")){
						CTMAX.add(_null);
						NYMIN.add(_null);
						NJMIN.add(_data);
					}
					// if the state is none of these 3, add all to null
					else{
						CTMAX.add(_null);
						NYMIN.add(_null);
						NJMIN.add(_null);
					}
				}
			}
			
			// ********************output task #1********************
			System.out.println("Table 1:");
			// set the format of the title
			String titleFormat1 = "%-8s  %-5s  %-8s  %-10s  %-2s  %-5s  %-8s  %-10s  %-2s  %-5s\n";
			
			// print the title of the table
			System.out.printf(titleFormat1, "PRODUCT", "MAX_Q", "CUSTOMER", "DATE", "ST", "MIN_Q", "CUSTOMER", "DATE", "ST", "AVG_Q");
			System.out.printf(titleFormat1, "========", "=====", "========", "==========", "==", "=====", "========", "==========", "==", "=====");
			
			// set the format of the data
			String dataFormat1 = "%-8s  %5s  %-8s  %02d/%02d/%04d  %-2s  %5s  %-8s  %02d/%02d/%04d  %-2s  %5s\n";
			
			// print the data line by line
			for(int i = 0; i < productName.size(); i++){
				System.out.printf(dataFormat1, productName.get(i), MAX.get(i).quant, MAX.get(i).cust, MAX.get(i).month, MAX.get(i).day, MAX.get(i).year, MAX.get(i).state, MIN.get(i).quant, MIN.get(i).cust, MIN.get(i).month, MIN.get(i).day, MIN.get(i).year, MIN.get(i).state, AVG.get(i).avg);
			}
			
			System.out.println();
			
			// ********************output task #1********************
			System.out.println("Table 2:");
			
			// set the format of the title
			String titleFormat2 = "%-8s  %-8s  %-6s  %-10s  %-6s  %-10s  %-6s  %-10s\n";
			
			// print the title of the table
			System.out.printf(titleFormat2, "CUSTOMER", "PRODUCT", "CT_MAX", "DATE", "NY_MIN", "DATE", "NJ_MIN", "DATE");
			System.out.printf(titleFormat2, "========", "========", "======", "==========", "======", "==========", "======", "==========");
			
			// the format of the data, it will be changed during the loop
			String dataFormat2;
			
			// print the data line by line
			for(int i = 0; i < customerProd.size(); i++){
				// for CT
				if(CTMAX.get(i).quant == 0){
					// set the format of the data
					dataFormat2 = "%-8s  %-8s  %6s  %10s  ";
					// print
					System.out.printf(dataFormat2, CTMAX.get(i).cust, CTMAX.get(i).prod, "null", "null");
				}
				else{
					// set the format of the data
					dataFormat2 = "%-8s  %-8s  %6s  %02d/%02d/%04d  ";
					// print
					System.out.printf(dataFormat2, CTMAX.get(i).cust, CTMAX.get(i).prod, CTMAX.get(i).quant, CTMAX.get(i).month, CTMAX.get(i).day, CTMAX.get(i).year);
				}
				// for NY
				if(NYMIN.get(i).quant == 0){
					// set the format of the data
					dataFormat2 = "%6s  %10s  ";
					// print
					System.out.printf(dataFormat2, "null", "null");
				}
				else{
					// set the format of the data
					dataFormat2 = "%6s  %02d/%02d/%04d  ";
					// print
					System.out.printf(dataFormat2, NYMIN.get(i).quant, NYMIN.get(i).month, NYMIN.get(i).day, NYMIN.get(i).year);
				}
				// for NJ
				if(NJMIN.get(i).quant == 0){
					// set the format of the data
					dataFormat2 = "%6s  %10s\n";
					// print
					System.out.printf(dataFormat2, "null", "null");
				}
				else{
					// set the format of the data
					dataFormat2 = "%6s  %02d/%02d/%04d\n";
					// print
					System.out.printf(dataFormat2, NJMIN.get(i).quant, NJMIN.get(i).month, NJMIN.get(i).day, NJMIN.get(i).year);
				}
			}
		} catch (SQLException e) {
			System.out
			.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}
}

//By Shenwei Chen
//CWID 10404898