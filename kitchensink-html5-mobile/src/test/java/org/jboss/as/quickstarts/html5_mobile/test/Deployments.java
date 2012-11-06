package org.jboss.as.quickstarts.html5_mobile.test;

import java.io.File;

import org.jboss.as.quickstarts.html5_mobile.data.MemberRepository;
import org.jboss.as.quickstarts.html5_mobile.model.Member;
import org.jboss.as.quickstarts.html5_mobile.rest.MemberService;
import org.jboss.as.quickstarts.html5_mobile.service.MemberRegistration;
import org.jboss.as.quickstarts.html5_mobile.util.Resources;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.filter.ScopeFilter;

public class Deployments {

    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap
                .create(WebArchive.class)
                // add classes
                .addPackages(true, Member.class.getPackage(), MemberService.class.getPackage(), Resources.class.getPackage(),
                        MemberRepository.class.getPackage(), MemberRegistration.class.getPackage())
                // add configuration - resources from src/main/resources
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").addAsWebInfResource("arquillian-ds.xml");

        // add web pages and other resources
        war = ArchiveUtils.addWebResourcesRecursively(war, new File("src/main/webapp"));

        // add required libraries
        File[] requiredLibraries = DependencyResolvers.use(MavenDependencyResolver.class).loadEffectivePom("pom.xml")
                .importAnyDependencies(new ScopeFilter("", "compile")).resolveAsFiles();

        return war.addAsLibraries(requiredLibraries);
    }
}
