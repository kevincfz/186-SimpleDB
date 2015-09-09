import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class YelpQueries
{
  public static void main(String[] args) throws ClassNotFoundException
  {
    // load the sqlite-JDBC driver using the current class loader
    Class.forName("org.sqlite.JDBC");

    String dbLocation = "yelp_dataset.db"; 

    Connection connection = null;
    try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbLocation);

      Statement statement = connection.createStatement();

      // Question 0
      statement.execute("DROP VIEW IF EXISTS q0"); // Clean out views
      String q0 = "CREATE VIEW q0 AS "
                   + "SELECT count(*) FROM reviews";
      statement.execute(q0);

      // Question 1
      statement.execute("DROP VIEW IF EXISTS q1");
      String q1 = "CREATE VIEW q1 AS " 
                  + "SELECT avg(users.review_count) FROM users WHERE users.review_count < 10"; // Replace this line
      statement.execute(q1);

      // Question 2
      statement.execute("DROP VIEW IF EXISTS q2");
      String q2 = "CREATE VIEW q2 AS "
                  + "SELECT users.name FROM users WHERE users.yelping_since > '2014-11' AND users.review_count > 50 "; // Replace this line
      statement.execute(q2);

      // Question 3
      statement.execute("DROP VIEW IF EXISTS q3");
      String q3 = "CREATE VIEW q3 AS "
                  + "SELECT businesses.name, businesses.stars FROM businesses " 
                  + "WHERE businesses.stars > 3 AND businesses.city = 'Pittsburgh'"; // Replace this line
      statement.execute(q3);

      // Question 4
      statement.execute("DROP VIEW IF EXISTS q4");
      String q4 = "CREATE VIEW q4 AS "
                  + "SELECT businesses.name FROM businesses "
                  + "WHERE businesses.review_count >= 500 ORDER BY businesses.stars LIMIT 1"; // Replace this line
      statement.execute(q4);

      // Question 5
      statement.execute("DROP VIEW IF EXISTS q5");
      String q5 = "CREATE VIEW q5 AS "
                  + "SELECT B.name FROM businesses AS B, checkins AS c "
                  + "WHERE c.business_id = b.business_id AND c.day = '0' "
                  + "ORDER BY c.num_checkins DESC LIMIT 5";
      statement.execute(q5);

      // Question 6
      statement.execute("DROP VIEW IF EXISTS q6");
      String q6 = "CREATE VIEW q6 AS "
                  + "SELECT checkins.day FROM checkins "
                  + "GROUP BY checkins.day "
                  + "ORDER BY SUM(checkins.num_checkins) DESC LIMIT 1"; 

      statement.execute(q6);

      // Question 7
      statement.execute("DROP VIEW IF EXISTS q7");
      String q7 = "CREATE VIEW q7 AS "
                  + "SELECT B.name FROM users AS U, reviews AS R, businesses as B "
                  + "WHERE R.business_id = B.business_id AND R.user_id = U.user_id "
                  + "AND U.user_id = " 
                  + "(SELECT U.user_id FROM users as U ORDER BY U.review_count DESC LIMIT 1)"; 
      statement.execute(q7);

      // Question 8
      statement.execute("DROP VIEW IF EXISTS q8");
      String q8 = "CREATE VIEW q8 AS "
                  + "SELECT AVG(B.stars) "
                  + "FROM businesses AS B WHERE B.business_id IN "
                  + "(SELECT B.business_id FROM businesses AS B WHERE B. city = 'Edinburgh' ORDER BY B.review_count DESC LIMIT " 
                  + "(SELECT count(*)/10 FROM businesses where businesses.city='Edinburgh'))"; // Replace this line
      statement.execute(q8);

      // Question 9
      statement.execute("DROP VIEW IF EXISTS q9");
      String q9 = "CREATE VIEW q9 AS "
                  + "select users.name from users where users.name like '%..' OR users.name like '..%' "; // Replace this line
      statement.execute(q9);

      // Question 10
      statement.execute("DROP VIEW IF EXISTS q10");
      String q10 = "CREATE VIEW q10 AS "
                  + "SELECT B.city "
                  + "FROM businesses as B, users as U, reviews as R " 
                  + "WHERE B.business_id = R.business_id And R.user_id IN "
                  + "(SELECT users.user_id from users where users.name like '%..' OR users.name like '..%') "
                  + "GROUP BY B.city "
                  + "ORDER BY count(*) DESC LIMIT 1";      
      statement.execute(q10);

      connection.close();

    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
}
