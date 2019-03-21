package com.myretail.repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.springframework.stereotype.Repository;

@Repository
public class CassandraRepository implements ProductPriceRepository{

    private Cluster cluster;
    private Session session;

    public String getProductPrice(){

        cluster = Cluster.builder().addContactPoint("localhost").withPort(9042).build();
        session = cluster.connect("demo");
        session.execute("INSERT INTO users (lastname, age, city, email, firstname) VALUES ('Jones', 35, 'Austin', 'bob@example.com', 'Bob')");
        ResultSet results = session.execute("SELECT * FROM users WHERE lastname='Jones'");
        String name = null;
        for (Row row : results) {
            System.out.format("%s %d\n", row.getString("firstname"), row.getInt("age"));
            name = row.getString("firstname");
        }
        cluster.close();
        return name;
    }
}
