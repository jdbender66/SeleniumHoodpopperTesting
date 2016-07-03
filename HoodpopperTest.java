//Created by Joseph Bender 

//imports
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HoodpopperTest {//HoodpopperTest class 

	static WebDriver driver = new HtmlUnitDriver(); //initialize WebDriver object
	static WebDriver driver2 = new HtmlUnitDriver(); //initialize WebDriver object

	// Before each test, load the hoodpopper web application in the HtmlUnitDriver
	@Before
	public void setUp() throws Exception {
		
		driver.get("http://lit-bayou-7912.herokuapp.com/");//add url to HtmlUnitDriver
		driver2.get("http://lit-bayou-7912.herokuapp.com/");//add url to HtmlUnitDriver
		
	}//end setUp method

	@After
	public void teardown(){
		//delete all cookies after each test to start from scratch
		driver.manage().deleteAllCookies();
		driver2.manage().deleteAllCookies();
	}//end teardown method
	
	//User Story #1
	//As a data analyst
	//I want to investigate tokenization of data
	//So that I can better understand how Ruby code is segmented and broken down
	
	//Scenario #1.1
	//Given that the website is loaded
	//When an integer variable is declared, and the code is tokenized
	//Then an ":on_ident" token should exist
	@Test
	public void testIdentifier() {
		try{
			//locate the text input area and send it a string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys("a = 5");
			
			//locate Tokenize button and click
			WebElement tokenizeButton = driver.findElement(By.xpath("//input[@value='Tokenize']"));
			tokenizeButton.click();
			
			//locate the resulting tokenization code and store in string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult = codeResult.getText();
			
			assertTrue(textResult.contains(":on_ident"));
			
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}//end testIdentifier() method
	
	
	//Scenario #1.2
	//Given the webpage is loaded
	//When a variable is declared, and the same variable is then printed with the "puts" command
	//Then both the declaration and put statements should be listed in the tokenization as the same, with ":on_ident"
	@Test
	public void testDoubleIdent() {
		try{
			
			//locate the text input area and send it a string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys("a = 5\nputs a");
			
			//locate Tokenize button and click
			WebElement tokenizeButton = driver.findElement(By.xpath("//input[@value='Tokenize']"));
			tokenizeButton.click();
			
			//locate the resulting tokenization code and store in string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult = codeResult.getText();
			
			
			assertTrue(textResult.contains(":on_ident, \"a\""));
			assertTrue(textResult.contains(":on_ident, \"puts\""));
			
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}// end testDoubleIdent() method
	
	
	//Scenario #1.3
	//Given the webpage is loaded
	//When two identical declarations, but one with whitespace, are entered
	//Then the one with whitespace will have more tokens of "op_sp"
	@Test
	public void whitespaceTokenTest() {
		try{
			//initialize variables to hold strings for the tokenizer
			String firstDeclaration = "a=5";
			String secondDeclaration = "a = 5";
			
			//locate the text input area and send it the first string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys(firstDeclaration);
			
			//locate Tokenize button and click
			WebElement tokenizeButton = driver.findElement(By.xpath("//input[@value='Tokenize']"));
			tokenizeButton.click();
			
			//locate the resulting tokenization code and store in a string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult1 = codeResult.getText();
			
			//Reload Hoodpopper with different driver to insert different code for juxtaposition
			
			//locate the text input area again and send it the second string
			WebElement codeInput2 = driver2.findElement(By.tagName("textarea"));
			codeInput2.sendKeys(secondDeclaration);
			
			//locate Tokenize button and click
			WebElement tokenizeButton2 = driver2.findElement(By.xpath("//input[@value='Tokenize']"));
			tokenizeButton2.click();
			
			//locate the resulting tokenization code and store in a second string
			WebElement codeResult2 = driver2.findElement(By.tagName("code"));
			String textResult2 = codeResult2.getText();
			
			//Testing that the resulting tokens are not identical
			//The tokenizer creates a token for the whitespace in the second declaration
			assertTrue(textResult1.length() != textResult2.length());// not the same length
			assertTrue(textResult2.contains("on_sp"));//the second contains a whitespace token
			
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}//end whitespaceTokenTest() method
	
	
	//User Story #2
	//As a computer language specialist
	//I want to investigate the grammar rules of Ruby
	//So that I can fully grasp the complexities of the abstract syntax tree
	
	//Scenario #2.1
	//Given that the webpage is loaded
	//When two declarations are parsed that have the same operation, but different amounts of whitespace
	//Then the length of the parse should still be identical, regardless of whitespace
	@Test
	public void testWhitespace() {
		try{
			//initialize variables to hold strings for the parser
			String firstDeclaration = "a=5";
			String secondDeclaration = "a = 5";
			
			//parsing "a=5"
			
			//locate the text input area and send it the first string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys(firstDeclaration);
			
			//locate Parse button and click
			WebElement parseButton = driver.findElement(By.xpath("//input[@value='Parse']"));
			parseButton.click();
			
			//locate the resulting parse code and store in a string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult1 = codeResult.getText();
			
			//Reload Hoodpopper with different driver to insert different code for juxtaposition
			
			//parsing "a = 5"
			
			//locate the text input area and send it the second string
			WebElement codeInput2 = driver2.findElement(By.tagName("textarea"));
			codeInput2.sendKeys(secondDeclaration);
			
			//locate Parse button and click
			WebElement parseButton2 = driver2.findElement(By.xpath("//input[@value='Parse']"));
			parseButton2.click();
			
			//locate the resulting parse code and store in a second string
			WebElement codeResult2 = driver2.findElement(By.tagName("code"));
			String textResult2 = codeResult2.getText();
			
			//Testing that the resulting parse code for each should be identical length regardless of whitespace
			//Because whitespace is not included in the parse
			assertTrue(textResult1.length() == textResult2.length());//the length of each parse is equal regardless of whitespace
		
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}//end testWhitespace() method
	
	
	//Scenario #2.2
	//Given the webpage is loaded
	//When the only text parsed are a combination of spaces and newlines
	//Then the parser shall not construct an AST, but return "void_stmt"
	@Test
	public void testWhitespaceNewline() {
		try{
			//initialize variable to hold strings for the parser
			String declaration = "    \n    \n   \n \n   ";
			
			//locate the text input area and send it a string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys(declaration);
			
			//locate the Parse button and click
			WebElement parseButton = driver.findElement(By.xpath("//input[@value='Parse']"));
			parseButton.click();
			
			//locate the resulting parse code and store it in a string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult = codeResult.getText();
			
			//test that even though whitespace and newlines were submit to be parsed,
			//there is no AST due to whitespace and newlines returning void
			assertTrue(textResult.contains("void_stmt"));
		
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}//end testWhitespaceNewline() method
	
	
	//Scenario #2.3
	//Given the webpage is loaded
	//When two variables are declared
	//Then you can see the variables being assigned as integers using "@int" in the abstract syntax tree
	@Test
	public void variableAssignmentTest() {
		try{
			//initialize variable to hold strings for the parser
			String declaration = "a = 5\nb = 8";
			
			//locate the text input area and send it a string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys(declaration);
			
			//locate the Parse button and click
			WebElement parseButton = driver.findElement(By.xpath("//input[@value='Parse']"));
			parseButton.click();
			
			//locate the resulting parse code and store it in a string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult = codeResult.getText();
			
			//ensure that both declarations are reflected in the parse AST as integers
			assertTrue(textResult.contains("@int, \"5\""));
			assertTrue(textResult.contains("@int, \"8\""));
			
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}//end variableAssignmentTest() method
	
	//User Story #3
	//As a bytecode developer
	//I want to understand how to translate the abstract syntax tree
	//So that I can better interpret the resulting executable code for the Ruby YARV virtual machine
	
	//Scenario #3.1
	//Given the webpage has loaded
	//When two declarations and an addition are performed
	//Then two "putobject" lines, and a "opt_plus" command, will be constructed in the bytecode for the Ruby YARV virtual machine
	@Test
	public void compileMatchingTest() {
		try{
			//code to be entered into compiler stored in a string variable
			String declaration = "a=5\nb=10\nc=a+b";
			
			//locate the text input area and send it a string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys(declaration);
			
			//find and click Compile button
			WebElement compileButton = driver.findElement(By.xpath("//input[@value='Compile']"));
			compileButton.click();
			
			//store compiler code result in string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult = codeResult.getText();
			
			//Assert that the outcome is as expected with declarations and addition
			assertTrue(textResult.contains("putobject 5"));
			assertTrue(textResult.contains("putobject 10"));
			assertTrue(textResult.contains("opt_plus"));
			
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}//end compileMatchingTest() method
	
	
	//Scenario #3.2
	//Given the webpage is loaded
	//When four variables are declared
	//Then the resulting compiler table size should equal 5
	@Test
	public void compileTableSizeTest() {
		try{
			//code to be entered into compiler
			String firstDeclaration = "a=10\nb=15\nd=20\nc=a+b+d";
			
			//locate the text input area and send it a string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys(firstDeclaration);
			
			//find and click Compile button
			WebElement compileButton = driver.findElement(By.xpath("//input[@value='Compile']"));
			compileButton.click();
			
			//store compiler code result in string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult = codeResult.getText();
			
			//Assert that resulting compiler table size is as expected
			assertTrue(textResult.contains("local table (size: 5"));
			
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}//end compileTableSizeTest() method
	
	//Scenario #3.3
	//Given the webpage is loaded
	//When four variables are declared using the mathematical operations of addition, subtraction, multiplication, and division
	//Then all four declarations will be identifiable in the compiler code as "opt_plus", "opt_minus", "opt_mult", and "opt_div"
	@Test
	public void multipleOperatorTest() {
		try{
			//code to be entered into compiler
			String firstDeclaration = "a = 5 + 5\nb = 8 - 3\nc = 3 * 3\nd = 20 / 4";
			
			//locate the text input area and send it a string
			WebElement codeInput = driver.findElement(By.tagName("textarea"));
			codeInput.sendKeys(firstDeclaration);
			
			//find and click Compile button
			WebElement compileButton = driver.findElement(By.xpath("//input[@value='Compile']"));
			compileButton.click();
			
			//store compiler code result in string
			WebElement codeResult = driver.findElement(By.tagName("code"));
			String textResult = codeResult.getText();
			
			//Assert that the outcome is as expected
			//All four mathematical operations should be reflected in the compiler code
			assertTrue(textResult.contains("opt_plus"));
			assertTrue(textResult.contains("opt_mult"));
			assertTrue(textResult.contains("opt_minus"));
			assertTrue(textResult.contains("opt_div"));
			
		}catch(NoSuchElementException nseex){//if an element is not found throw exception
			nseex.printStackTrace();//print error to console
			fail();//fail test
		}
	}// end multipleOperatorTest() method
	
}//end HoodpopperTest class
