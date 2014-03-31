package com.dougsvendsen.dropblog

import groovy.util.logging.Slf4j

import org.eclipse.jetty.servlets.CrossOriginFilter

import com.dougsvendsen.dropblog.auth.AdminAuthenticator
import com.dougsvendsen.dropblog.core.Post
import com.dougsvendsen.dropblog.core.User
import com.dougsvendsen.dropblog.db.PostDAO
import com.dougsvendsen.dropblog.db.UserDAO
import com.dougsvendsen.dropblog.resources.PostResource
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.auth.basic.BasicAuthProvider
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle

@Slf4j
class DropblogService extends Service<DropblogConfiguration> {
    public static void main(String[] args) throws Exception {
        new DropblogService().run(args)
    }

    HibernateBundle<DropblogConfiguration> hibernateBundle =
        new HibernateBundle<DropblogConfiguration>([Post]) {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(DropblogConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    MigrationsBundle<DropblogConfiguration> migrationsBundle =
        new MigrationsBundle<DropblogConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(DropblogConfiguration configuration) {
                return configuration.databaseConfiguration
            }
        }

    AssetsBundle assetsBundle = new AssetsBundle()

    @Override
    public void initialize(Bootstrap<DropblogConfiguration> bootstrap) {
        bootstrap.with {
            name = 'Dropblog'
            addBundle migrationsBundle
            addBundle hibernateBundle
            addBundle assetsBundle
        }
    }

	@SuppressWarnings('DuplicateStringLiteral')
    @Override
    public void run(DropblogConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {

        PostDAO postDAO = new PostDAO(hibernateBundle.sessionFactory)
		UserDAO userDAO = new UserDAO()
        environment.addResource(new PostResource(postDAO))
		environment.addProvider(new BasicAuthProvider<User>(new AdminAuthenticator(userDAO), 'Blog Admin'))
		
		//CORS configured to allow access from any domain.
		environment.addFilter(CrossOriginFilter, '*')
			.setInitParam(CrossOriginFilter.ALLOWED_HEADERS_PARAM, 'origin,content-type,accept,authorization,x-requested-with')
			.setInitParam(CrossOriginFilter.ALLOWED_METHODS_PARAM, 'GET,PUT,POST,DELETE')
			.setInitParam(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, 'true')
			.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, '*')
    }
}
