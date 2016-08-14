//package publishSubscribe;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.connection.ConnectionFactoryUtils;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.support.destination.DestinationResolver;
//
//import javax.jms.*;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import java.util.Hashtable;
//
///**
// * Created by RAM0N on 8/13/16.
// */
//
//@Configuration
//@EnableJms
//public class AppConfig {
//
//    @Bean
//    public InitialContext initialContext() throws NamingException {
//        Hashtable<Object, Object> env = new Hashtable<Object, Object>();
//        env.put("java.naming.factory.initial",
//                "org.jnp.interfaces.NamingContextFactory");
//        env.put("java.naming.factory.url.pkgs",
//                "org.jboss.naming:org.jnp.interfaces");
//        env.put("java.naming.provider.url", "jnp://localhost:1099");
//        return new InitialContext(env);
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory(InitialContext initialContext)
//            throws NamingException {
//        return (ConnectionFactory) initialContext.lookup("/ConnectionFactory");
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
//        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
//        jmsTemplate.setPubSubDomain(true);
//        return jmsTemplate;
//    }
//
//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
//            ConnectionFactory connectionFactory) {
//        DefaultJmsListenerContainerFactory factory =
//                new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPubSubDomain(true);
//        return factory;
//    }
//
//    // other @Bean definitions
//}
