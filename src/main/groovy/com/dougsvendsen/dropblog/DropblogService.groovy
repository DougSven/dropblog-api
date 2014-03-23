package com.dougsvendsen.dropblog

import com.dougsvendsen.dropblog.core.Post
import com.dougsvendsen.dropblog.db.PostDAO
import com.dougsvendsen.dropblog.resources.PostResource
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.db.DatabaseConfiguration
import com.yammer.dropwizard.hibernate.HibernateBundle
import com.yammer.dropwizard.migrations.MigrationsBundle

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

    @Override
    public void run(DropblogConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {

        PostDAO postDAO = new PostDAO(hibernateBundle.sessionFactory)
        environment.addResource(new PostResource(postDAO))

    }
}
