package in.doubloons.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@PropertySource("classpath:test.properties")
public class DataSourceConfig {

	@Autowired
	Environment env;
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource)
	{
		LocalSessionFactoryBuilder factoryBuilder=new LocalSessionFactoryBuilder(dataSource);
		factoryBuilder.scanPackages("in.doubloons.dto");
		factoryBuilder.addProperties(getProperties());
		return factoryBuilder.buildSessionFactory();
	}
	
	public Properties getProperties()
	{
		Properties properties=new Properties();
		properties.put("hibernate.show_sql", "true");
	    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	    properties.put("hibernate.hbm2ddl.auto", "update");
	  
	   return properties;
	   
	}
	
	@Autowired
	@Bean(name = "dataSource")
	public DataSource getDataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		dataSource.setDriverClassName(env.getProperty("db.driver"));
		return dataSource;
		
	}
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		  HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		  return transactionManager;
	}
	

}
