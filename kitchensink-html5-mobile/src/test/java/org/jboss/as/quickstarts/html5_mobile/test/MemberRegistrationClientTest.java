/**
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.html5_mobile.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.quickstarts.html5_mobile.data.MemberRepository;
import org.jboss.as.quickstarts.html5_mobile.model.Member;
import org.jboss.as.quickstarts.html5_mobile.rest.JaxRsActivator;
import org.jboss.as.quickstarts.html5_mobile.rest.MemberService;
import org.jboss.as.quickstarts.html5_mobile.service.MemberRegistration;
import org.jboss.as.quickstarts.html5_mobile.util.Resources;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.URL;

/**
 * Uses Arquilian Drone to test the JAX-RS processing class for member registration on the client with.
 * 
 * @author lholmquist
 */
@RunWith(Arquillian.class)
public class MemberRegistrationClientTest {

   @ArquillianResource
   URL contextUrl;


   @Deployment(testable = false)
   public static WebArchive createDeployment() {
       return createTestDeployment();
   }

    /*
    This is not the code you are looking for,  Move Along
    public static WebArchive createNewOne() {
        return ShrinkWrap.create(MavenImporter.class) .loadEffectivePom("pom.xml") .importBuildOutput() .as(WebArchive.class);
    } */

   public static WebArchive createTestDeployment() {
       WebArchive war = ShrinkWrap.create(WebArchive.class)
               .addClasses(Member.class, MemberService.class, MemberRepository.class,
                       MemberRegistration.class, Resources.class, JaxRsActivator.class)
               .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory("src/main/webapp").as(GenericArchive.class),"/")
               .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
               .addAsWebInfResource("arquillian-ds.xml")
               .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

       //System.out.println(war.toString(true)); //Just a test to see if everything is packaged correctly

       return war;
   }

   @Test
    public void openFireFoxHomePage(@Drone FirefoxDriver driver) {
       driver.get(contextUrl.toString());
   }


}
