package com.uniandes.ecos.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @name Tester
 * @author Juan
 * @version 1.0
 * @date 07/04/2016
 * @description clase encargada de ejecutar los test de prueba a traves de JUnit.
 */
public class Tester extends TestCase{

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public Tester( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( Tester.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    //method
    /**
     * Comprueba que se puedan insertar entidades de tipo formulario en la BD. 
     */
    @org.junit.Test
    public void test1(){
        
    }
    
    //method
    /**
     * Ejeucta el caso de prueba 2
     */
    @org.junit.Test
    public void test2() {

    }
    
    //method
    /**
     * Ejeucta el caso de prueba 3
     */
    @org.junit.Test
    public void test3(){
        
    }
}
