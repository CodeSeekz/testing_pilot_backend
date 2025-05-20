package com.both.testing_pilot_backend.config;

import com.both.testing_pilot_backend.utils.OtpPurpose;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import javax.sql.DataSource;
import java.util.UUID;

@Configuration
@MapperScan("com.both.testing_pilot_backend.repository")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();

        registry.register(UUID.class, UUIDTypeHandler.class);
        registry.register(OtpPurpose.class, EnumConfig.class);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }
}