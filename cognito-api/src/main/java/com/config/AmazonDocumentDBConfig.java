package com.config;

import org.springframework.context.annotation.Configuration;

//@Configuration
public class AmazonDocumentDBConfig {//extends AbstractMongoClientConfiguration {

    /*public static final String DOCUMENT_DB_CONNECTION_STRING = "mongodb://%s:%s@%s/%s?ssl=%b&replicaSet=%s&readpreference=%s&retryWrites=false";
    public static final String LOCAL_DB_CONNECTION_STRING = "mongodb://%s/%s?";

    @Value("${mongo.readPreference}")
    private String readPreference;

    @Value("${mongo.clusterEndpoint}")
    private String clusterEndpoint;

    @Value("${mongo.username}")
    private String username;

    @Value("${mongo.password}")
    private String password;

    @Value("${mongo.databaseName}")
    private String databaseName;

    @Value("${keystore.password}")
    private String keyStorePassword;

    @Value("${mongo.ssl.enabled}")
    private Boolean mongoSslEnabled;

    @Value("${mongo.replicaSet}")
    private String replicaSet;

    @Value("${mongo.isLocal}")
    private boolean isLocal;

    @Override
    @Bean
    public MongoClient mongoClient() {
        String connectionString = null;
        if (isLocal) {
            connectionString = String.format(LOCAL_DB_CONNECTION_STRING, clusterEndpoint, databaseName);
        } else {
            connectionString = String.format(DOCUMENT_DB_CONNECTION_STRING, username, password, clusterEndpoint, databaseName, mongoSslEnabled, replicaSet, readPreference);
        }


        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyToSslSettings(builder -> {
                    builder.enabled(mongoSslEnabled);
                    builder.context(buildSSLContext());
                    builder.invalidHostNameAllowed(true);
                })
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, databaseName);
        return mongoTemplate;
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    private SSLContext buildSSLContext() {
        final SSLContext sslContext;
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");

            keyStore.load(getClass().getClassLoader().getResourceAsStream("jks/rds-truststore.jks"), keyStorePassword.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);


            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
     */
}
