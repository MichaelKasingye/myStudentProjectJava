package addressBook;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;


@ManagedBean(name = "addressBean")
public class addressbean {
	//instant variables that represent address
private String firstName,lastName, street, city, state, zipcode;

//allow server to inject Datasource
@Resource(name = "jdbc:mysql://localhost:3306/me2")
DataSource dataSource;

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}
//getter&Setter last name
public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}
//getter&Setter street
public String getStreet() {
	return street;
}

public void setStreet(String street) {
	this.street = street;
}
//getter&Setter city
public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}
//getter&Setter State
public String getState() {
	return state;
}

public void setState(String state) {
	this.state = state;
}
//getter& Zipcode
public String getZipcode() {
	return zipcode;
}

public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
}

// return a Resultset of entries
public ResultSet getAddresses() throws SQLException
{//check whether dataSource was injected by server
if (dataSource == null)
	throw new SQLException("Unable to obtain DataSource");

//obtain a connection from the connection pool
Connection connection = dataSource.getConnection();

//check is connection was successful
if(connection == null)
	throw new SQLException("Unable to connect to dataSource");
try {
//create a PreparedStatement to insert a new address book entry
PreparedStatement getAddresses = connection.prepareStatement(
		"SELECT FIRSTNAME, LASTNAME, STREET, CITY, STATE, ZIP,"
		+ "FROM ADDRESSES ORDER BY LASTNAME, FIRSTNAME");
CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();
rowSet.populate(getAddresses.executeQuery());
return rowSet;
// end try
}
finally {
	connection.close();// return this connection pool
}//end finally
}//end method getAddresses

// save a new address book entry
public String save() throws SQLException{
	//check whether dataSource was injected by server
	if (dataSource == null)
		throw new SQLException("Unable to obtain DataSource");

	//obtain a connection from the connection pool
	Connection connection = dataSource.getConnection();

	//check is connection was successful
	if(connection == null)
		throw new SQLException("Unable to connect to dataSource");
	try {
		//create a PreparedStatement to insert a new address book entry
		PreparedStatement addEntry = connection.prepareStatement(
	"INSERT INTO ADDRESSES "
	+ "(FIRSTNAME, LASTNAME, STREET, CITY, STATE, ZIP)"
				+"VALUES (?,?,?,?,?,?,)");
		//Specify the statement's arguments
		addEntry.setString(1, getFirstName());
		addEntry.setString(2, getLastName());
		addEntry.setString(3, getStreet());
		addEntry.setString(4, getCity());
		addEntry.setString(5, getState());
		addEntry.setString(6, getZipcode());
		
		//insert Entry
		addEntry.executeUpdate();
		return "index";// go back to index.xhtml page
		
	}// end try
	finally {
		connection.close();
	}//end finally
}// end save method
}//end class AddressBook