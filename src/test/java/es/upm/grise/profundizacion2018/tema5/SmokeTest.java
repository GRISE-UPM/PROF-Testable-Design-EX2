package es.upm.grise.profundizacion2018.tema5;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class SmokeTest {
	
	@Mock private PreparedStatement mockPreparedStatement;
	@Mock private Connection mockConnection;
	@Mock private Statement mockStatement;
	@InjectMocks private DocumentIdProvider documentIdProvider;
	
	private String databaseDriver;
	
	private ByteArrayOutputStream outMessage = new ByteArrayOutputStream();
	private PrintStream originalOut = System.out;
	
	@BeforeEach
	public void init() {
	    System.setOut(new PrintStream(outMessage));
	}
	
	@AfterEach
	public void clean() {
	    System.setOut(originalOut);
	}
	
	/**
	 * 4.a)
	 * La aplicación genera las plantillas correctamente.
	 */
	@Test
	public void formatTemplateCorrectly() throws Exception {
		
		DocumentIdProvider mockDocumentIdProvider = mock(DocumentIdProvider.class);		
		given(mockDocumentIdProvider.getDocumentId()).willReturn(1);
		
		Document document = new Document(mockDocumentIdProvider);
		document.setTemplate("DECLARATION");
		document.setTitle("A");
		document.setAuthor("B");
		document.setBody("C");
		assertThat(document.getFormattedDocument()).isEqualTo("DOCUMENT ID: 1\n\nTitle : A\nAuthor: B\n\nC");
	}

	/**
	 * 5.a) La aplicación detecta correctamente que el fichero de configuración no existe.
	 * 5.b) La aplicación detecta correctamente que el driver MySQL no existe.
	 * 4.b) La aplicación asigna el número de documento correcto.
	 * 4.c) Los números de documento asignados a documentos consecutivos son también números consecutivos.
	 * 5.c) La aplicación detecta correctamente que hay más de una fila en la tabla Counters.
	 * 5.d) La aplicación detecta correctamente que la actualización del documentID en la tabla Counters ha sido incorrectamente realizado.
	 */
	@Test
	public void assignCorrectDocumentId() throws Exception {
		
		Map<String, String> newEnv = new HashMap<>();
		newEnv.put("APP_HOME", System.getProperty("user.dir")+"/asd/");
		setEnv(newEnv);
		
		// 5.a)
		assertThatThrownBy(() -> {
			DocumentIdProvider.getInstance(databaseDriver);
		}).isInstanceOf(NonRecoverableError.class);
		assertThat(outMessage.toString()).isEqualTo("Error code 2: Non-existing file\n");
		System.setOut(originalOut);
		outMessage = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outMessage));
		
		newEnv = new HashMap<>();
		newEnv.put("APP_HOME", System.getProperty("user.dir")+"/");
		setEnv(newEnv);
		
		// 5.b)
		assertThatThrownBy(() -> {
			DocumentIdProvider.getInstance("com.mysql.jdbc.Asd");
		}).isInstanceOf(NonRecoverableError.class);
		assertThat(outMessage.toString()).isEqualTo("Error code 4: Cannot find database driver\n");
		System.setOut(originalOut);
		outMessage = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outMessage));
		
		// 4.b) & 4.c)
		databaseDriver = "com.mysql.jdbc.Driver";
		documentIdProvider = DocumentIdProvider.getInstance(databaseDriver);		
		MockitoAnnotations.initMocks(this);		
		given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
		given(mockPreparedStatement.executeUpdate()).willReturn(1);		
		
		int prevId = documentIdProvider.getDocumentId();		
		Document document = new Document(documentIdProvider);		
		assertThat(document.getDocumentId()).isEqualTo(prevId + 1);
		
		// 5.c)
		given(mockPreparedStatement.executeUpdate()).willReturn(2);
		assertThatThrownBy(() -> {
			documentIdProvider.getDocumentId();
		}).isInstanceOf(NonRecoverableError.class);
		assertThat(outMessage.toString()).isEqualTo("Error code 10: Corrupted COUNTER table\n");
		System.setOut(originalOut);
		outMessage = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outMessage));
		
		// 5.d)
		given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
		assertThatThrownBy(() -> {
			documentIdProvider.getDocumentId();
		}).isInstanceOf(NonRecoverableError.class);
		assertThat(outMessage.toString()).isEqualTo("java.sql.SQLException\nError code 11: Unknown problem with the COUNTER table\n");
	}
	
	/**
	 * This method sets up a new set of environment variables to mock environment variables while testing
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setEnv(Map<String, String> newEnv) throws Exception {
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
