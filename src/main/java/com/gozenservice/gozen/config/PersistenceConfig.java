//package com.gozenservice.gozen.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages="com.gozenservice.gozen.repository")
//public class PersistenceConfig 
//{
//    @Autowired
//    private Environment env;
//    
// /*
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
//    {
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
// 
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(Boolean.TRUE);
//        vendorAdapter.setShowSql(Boolean.TRUE);
// 
//        factory.setDataSource(getDataSource());
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.emploi.repository");
// 
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        factory.setJpaProperties(jpaProperties);
// 
//        factory.afterPropertiesSet();
//        factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
//        return factory;
//    }
//     
//    
//    @Bean
//    public DataSource getDataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//        dataSourceBuilder.url("jdbc:mysql://localhost:3306/social_network?autoreconnect=true");
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("");
//        return dataSourceBuilder.build();
//    }
//    
//    @Bean
//    public DataSource dataSource()
//    {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));
//        return dataSource;
//    }
//    */  
//}