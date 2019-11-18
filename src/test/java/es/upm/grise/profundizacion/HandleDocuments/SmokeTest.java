package es.upm.grise.profundizacion.HandleDocuments;

import static es.upm.grise.profundizacion.HandleDocuments.Error.CANNOT_FIND_DRIVER;
import static es.upm.grise.profundizacion.HandleDocuments.Error.NON_EXISTING_FILE;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import es.upm.grise.profundizacion.HandleDocuments.Document;
import es.upm.grise.profundizacion.HandleDocuments.NonRecoverableError;
import es.upm.grise.profundizacion.HandleDocuments.RecoverableError;
import java.lang.reflect.Field;


public class SmokeTest {
	
	@Test
	public void formatTemplateCorrectly() throws NonRecoverableError, RecoverableError {
		
		
		Map<String, String> newEnv = new HashMap<>();
		newEnv.put("APP_HOME", System.getProperty("user.dir")+"/");
		try {
			setEnv(newEnv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String databaseDriver = "com.mysql.jdbc.Driver";
		DocumentIdProvider documentIdProvider = DocumentIdProvider.getInstance(databaseDriver);
		int id=documentIdProvider.getDocumentId();
		Document d = new Document(documentIdProvider);
		d.setTemplate("DECLARATION");
		d.setTitle("A");
		d.setAuthor("B");
		d.setBody("C");
		String get=d.getFormattedDocument();
		String expected="DOCUMENT ID: " + (id+1) + "\n\nTitle : A\nAuthor: B\n\nC";
		assertEquals(expected, get);

	}
	
	//4.B Y 4.C
	@Test
	public void numberCorrectly () throws NonRecoverableError, RecoverableError {
		
		
		
		Map<String, String> newEnv = new HashMap<>();
		newEnv.put("APP_HOME", System.getProperty("user.dir")+"/");
		try {
			setEnv(newEnv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String databaseDriver = "com.mysql.jdbc.Driver";
		DocumentIdProvider documentIdProvider = DocumentIdProvider.getInstance(databaseDriver);
		int prevId = documentIdProvider.getDocumentId();	
		documentIdProvider = DocumentIdProvider.getInstance(databaseDriver);
		assertEquals(prevId +1, documentIdProvider.getDocumentId());
	}
	
	//5.a
	@Test
	public void configFile (){
		
		PrintStream originalOut = System.out;
		
		ByteArrayOutputStream outMessage = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outMessage));
		
		Map<String, String> newEnv = new HashMap<>();
		newEnv.put("APP_HOME", System.getProperty("user.dir")+"/directorioFalso");
		try {
			setEnv(newEnv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String databaseDriver = "com.mysql.jdbc.Driver";
		try {
			DocumentIdProvider.getInstance(databaseDriver);
		} catch (NonRecoverableError e) {

			System.setOut(originalOut);
			System.out.println(outMessage);
			
			assertEquals(NON_EXISTING_FILE.getMessage()+'\n', outMessage.toString());
		}
		catch (Exception e) {
			fail();
		}
	}
	
	//5.b
	@Test
	public void badDriver (){
		
		PrintStream originalOut = System.out;
		
		ByteArrayOutputStream outMessage = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outMessage));
		
		Map<String, String> newEnv = new HashMap<>();
		newEnv.put("APP_HOME", System.getProperty("user.dir")+"/");
		

		try {
			setEnv(newEnv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String databaseDriver = "com.mysql.jdbc.DriverFalso";
		
		try {
			DocumentIdProvider.getInstance(databaseDriver);
		} catch (NonRecoverableError e) {

			System.setOut(originalOut);
			System.out.println(outMessage);
			
			assertEquals(CANNOT_FIND_DRIVER.getMessage() +'\n', outMessage.toString());

		}
		catch (Exception e) {
			fail();
		}
		
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setEnv(Map<String,String> newEnv) throws Exception {
		try {
			Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
			Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
			theEnvironmentField.setAccessible(true);
			Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
			env.putAll(newEnv);
			Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
			theCaseInsensitiveEnvironmentField.setAccessible(true);
			Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
			cienv.putAll(newEnv);
		} catch (NoSuchFieldException e) {
			Class[] classes = Collections.class.getDeclaredClasses();
			Map<String, String> env = System.getenv();
			for(Class cl : classes) {
				if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
					Field field = cl.getDeclaredField("m");
					field.setAccessible(true);
					Object obj = field.get(env);
					Map<String, String> map = (Map<String, String>) obj;
					map.clear();
					map.putAll(newEnv);
				}
			}
		}
	}
}
