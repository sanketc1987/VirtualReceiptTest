package edu.upenn.cis599.test;

import com.robotium.solo.Solo;

//import junit.framework.TestCase;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import edu.upenn.cis599.eas499.VirtualReceiptActivity;

import edu.upenn.cis599.R;

public class TestVirtualReceipt extends
		ActivityInstrumentationTestCase2<VirtualReceiptActivity> {

	private Solo solo;

	public TestVirtualReceipt() {
		super(VirtualReceiptActivity.class);
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testModifyDescription() throws Exception {

		solo.clickOnText("Add a receipt");
		solo.clickOnButton("No");

		EditText mDescriptionText = (EditText) solo.getView(R.id.description);
		solo.clearEditText(mDescriptionText);
		solo.enterText(mDescriptionText,
				"buy a lot of time to finish the test!");

		boolean result1 = solo
				.waitForText("buy a lot of time to finish the test!", 1, 100, false, true);
		assertTrue(result1);

		solo.clearEditText(mDescriptionText);
		solo.enterText(mDescriptionText, "Now you got it!");

		boolean result2 = solo.waitForText("Now you got it!", 1, 100, false, true);
		
		assertTrue(result2);

		solo.clearEditText(mDescriptionText);

		boolean result3 = solo
				.waitForText("buy a lot of time to finish the test!", 1, 100, false, true)
				|| solo.waitForText("Now you got it!", 1, 100, false, true);
		assertFalse(result3);

		solo.goBackToActivity("VirtualReceiptActivity");
	}

	public void testModifyAmount() throws Exception {

		solo.clickOnText("Add a receipt");

		solo.clickOnButton("No");

		EditText mAmountText = (EditText) solo.getView(R.id.amount);
		solo.clearEditText(mAmountText);
		solo.enterText(mAmountText, "100");

		boolean result1 = solo.waitForText("100", 1, 100, false, true);
		assertTrue(result1);

		solo.clearEditText(mAmountText);
		solo.enterText(mAmountText, "200");

		boolean result2 = solo.waitForText("200", 1, 100, false, true);
		assertTrue(result2);

		solo.clearEditText(mAmountText);

		boolean result3 = solo.waitForText("100", 1, 100, false, true) || solo.waitForText("200", 1, 100, false, true);
		assertFalse(result3);

		solo.goBackToActivity("VirtualReceiptActivity");

	}

	public void testModifyDate() throws Exception {

		solo.clickOnText("Add a receipt");

		solo.clickOnButton("No");

		EditText mDateText = (EditText) solo.getView(R.id.date);
		 solo.enterText(mDateText, "11/13/14");
		 solo.setDatePicker(0, 2014, 11, 25);
		 solo.clickOnText("Done");
		EditText mDescriptionText = (EditText) solo.getView(R.id.description);
		solo.clearEditText(mDescriptionText);
		solo.enterText(mDescriptionText, "Merry Christmas!!");
		boolean result1 = solo.waitForText("12/25/14",  1, 100, false, true);
		boolean result2 = solo.waitForText("11/13/14",  1, 100, false, true);
		 
		assertTrue(result1);
		assertFalse(result2);
		 
	    solo.clearEditText(mDateText);
		 solo.enterText(mDateText, "12/25/14");
		 solo.setDatePicker(0, 2015, 0, 1);
		 solo.clickOnText("Done");
		solo.clearEditText(mDescriptionText);
		solo.enterText(mDescriptionText, "Happy New Year!!!");
		boolean result3 = solo.waitForText("12/25/13", 1, 100, false, true);
		boolean result4 = solo.waitForText("01/01/15",  1, 100, false, true);
		assertFalse(result3);
		assertTrue(result4);
		 
		solo.clearEditText(mDateText);
		solo.clearEditText(mDescriptionText);
		solo.goBackToActivity("VirtualReceiptActivity");
	}

	 public void testModifyCategory() throws Exception{
		 
		 solo.clickOnText("Add a receipt");
		 solo.clickOnButton("No");
		 solo.pressSpinnerItem(0, 3);
		 boolean result1 = solo.isSpinnerTextSelected(0, "Rent");
		 assertTrue(result1);
		 
		 solo.pressSpinnerItem(0, 1);
		 boolean result2 = solo.isSpinnerTextSelected(0, "Bill");
		 assertTrue(result2);
	 }
	 
	 public void testAddReceipt() throws Exception{
		 
		 solo.clickOnText("Add a receipt");
		 solo.clickOnButton("No");
		 EditText mDescriptionText = (EditText) solo.getView(R.id.description);
		 EditText mAmountText = (EditText) solo.getView(R.id.amount);
		 EditText mDateText = (EditText) solo.getView(R.id.date);
		 
		 solo.clearEditText(mDescriptionText);
		 solo.enterText(mDescriptionText, "The tuition is a pain!");
		 
		 solo.clearEditText(mAmountText);
		 solo.enterText(mAmountText, "99999");
		 
		 solo.enterText(mDateText, "11/13/14");
		 solo.setDatePicker(0, 2014, 10, 11);
		 solo.clickOnText("Done");
		  
		 solo.clickOnText("Cash");
		 solo.clickOnButton("Save");
		 solo.goBackToActivity("VirtualReceiptActivity");
		 solo.clickOnText("View receipts");
		 
		 boolean result1 = solo.waitForText("The tuition is a pain!", 1, 100, false, true);
		 
		 assertTrue(result1);
		 
		 solo.clickOnText("The tuition is a pain!");
		 
		 boolean result2 = solo.waitForText("The tuition is a pain!", 1, 100, false, true) 
				 && solo.waitForText("99999", 1, 100, false, true) && 
		 solo.waitForText("11-11-2014", 1, 100, false, true) 
		 && solo.waitForText("Education", 1, 100, false, true) 
		   && solo.waitForText("Cash", 1, 100, false, true);
		 
		 assertTrue(result2);
		 
		 solo.clickOnButton("Delete");
		 solo.clickOnButton("Yes");
		 
		 solo.goBackToActivity("VirtualReceiptActivity");
		 
		 solo.clickOnText("View receipts");
		 
		 boolean result3 = solo.waitForText("The tuition is a pain!", 1, 100, false, true);
		 
		 assertFalse(result3);
	 }
	 
		//create a database with 3 receipts then compare them with the expected charts
		public void testSpendStat() throws Exception {
			//add new receipt1
			 solo.clickOnText("Add a receipt");
			 solo.clickOnButton("No");
			 EditText mDescriptionText1 = (EditText) solo.getView(R.id.description);
			 EditText mAmountText1 = (EditText) solo.getView(R.id.amount);
			 EditText mDateText1 = (EditText) solo.getView(R.id.date);
			 
			 solo.clearEditText(mDescriptionText1);
			 solo.enterText(mDescriptionText1, "Test1");
			 solo.clearEditText(mAmountText1);
			 solo.enterText(mAmountText1, "100");
			 solo.enterText(mDateText1, "12/01/13");
			 solo.setDatePicker(0, 2014, 10, 14);
			 solo.clickOnText("Done");
			 solo.clickOnText("Cash");
			 solo.clickOnButton("Save");
			 
			//add new receipt2
			 solo.clickOnText("Add a receipt");
			 solo.clickOnButton("No");

			 EditText mDescriptionText2 = (EditText) solo.getView(R.id.description);
			 EditText mAmountText2 = (EditText) solo.getView(R.id.amount);
			 EditText mDateText2 = (EditText) solo.getView(R.id.date);
			 solo.clearEditText(mDescriptionText2);
			 solo.enterText(mDescriptionText2, "Test2");
			 
			 solo.clearEditText(mAmountText2);
			 solo.enterText(mAmountText2, "200");
			 
			 solo.enterText(mDateText1, "12/01/13");
			 solo.setDatePicker(0, 2014, 10, 15);
			 solo.clickOnText("Done");
			 
			 solo.pressSpinnerItem(0, 3);
			 
			 solo.clickOnText("Credit");
			 
			 solo.clickOnButton("Save");
			 
			//add new receipt3
			 solo.clickOnText("Add a receipt");
			 solo.clickOnButton("No");
			 EditText mDescriptionText3 = (EditText) solo.getView(R.id.description);
			 EditText mAmountText3 = (EditText) solo.getView(R.id.amount);
			 EditText mDateText3 = (EditText) solo.getView(R.id.date);
			 
			 solo.clearEditText(mDescriptionText3);
			 solo.enterText(mDescriptionText3, "Test3");
			 
			 solo.clearEditText(mAmountText3);
			 solo.enterText(mAmountText3, "300");
			 
			 solo.enterText(mDateText1, "12/01/13");
			 solo.setDatePicker(0, 2014, 10, 16);
			 solo.clickOnText("Done");
			 
			 solo.pressSpinnerItem(0, -1);
			 
			 solo.clickOnText("Debit");
			 
			 solo.clickOnButton("Save");
			 
			 //show charts
			 solo.clickOnText("View spending statistics");
			 solo.clickOnText("Category Spending Analyzer");
			 solo.sleep(2500);
			 solo.goBack();
			 solo.clickOnText("Monthly Spending");
			 solo.sleep(2500);
			 solo.goBack();
			 solo.clickOnText("Payment Type Analyzer");
			 solo.sleep(2500);
			 solo.goBack();
		}
		
		public void testAddIncome() throws Exception {
			 
			 solo.clickOnText("Add Income");;
			 EditText mDescriptionText = (EditText) solo.getView(R.id.description);
			 EditText mAmountText = (EditText) solo.getView(R.id.amount);
			 EditText mDateText = (EditText) solo.getView(R.id.date);
			 
			 solo.clearEditText(mDescriptionText);
			 solo.enterText(mDescriptionText, "New Income");
			 solo.clearEditText(mAmountText);
			 solo.enterText(mAmountText, "500");
			 solo.clearEditText(mDateText);
			 solo.goBack();
			 solo.enterText(mDateText, "11/13/14");
			 solo.setDatePicker(0, 2015, 0, 1);
			 solo.clickOnText("Done");
			 solo.clickOnButton("Save");
			 solo.goBackToActivity("VirtualReceiptActivity");
			 solo.clickOnText("View receipts");
			 boolean result1 = solo.waitForText("New Income", 1, 100, false, true);
			 assertTrue(result1);
			 
			 solo.clickOnText("New Income");
			 boolean result2 = solo.waitForText("New Income", 1, 100, false, true) && solo.waitForText("500", 1, 100, false, true) && 
			 solo.waitForText("01-01-2015", 1, 100, false, true) && solo.waitForText("Income", 1, 100, false, true);
			 assertTrue(result2);
			 solo.clickOnButton("Delete");
			 solo.clickOnButton("Yes");
			 solo.goBackToActivity("VirtualReceiptActivity");
			 solo.clickOnText("View receipts");
			 boolean result3 = solo.waitForText("New Income", 1, 100, false, true);
			 assertFalse(result3);
		 }	
		
		public void testAddBudget() throws Exception {
			 
			 solo.clickOnText("Add Income");;
			 EditText mDescriptionText = (EditText) solo.getView(R.id.description);
			 EditText mAmountText = (EditText) solo.getView(R.id.amount);
			 EditText mDateText = (EditText) solo.getView(R.id.date);
			 
			 solo.clearEditText(mDescriptionText);
			 solo.enterText(mDescriptionText, "New Budget");
			 solo.clearEditText(mAmountText);
			 solo.enterText(mAmountText, "200");
			 solo.clearEditText(mDateText);
			 solo.goBack();
			 solo.enterText(mDateText, "11/13/14");
			 solo.setDatePicker(0, 2015, 0, 3);
			 solo.clickOnText("Done");

			 solo.pressSpinnerItem(0, 1);
			 solo.clickOnButton("Save");
			 solo.goBackToActivity("VirtualReceiptActivity");
			 solo.clickOnText("View receipts");
			 boolean result1 = solo.waitForText("New Budget", 1, 100, false, true);
			 assertTrue(result1);
			 
			 solo.clickOnText("New Budget");
			 boolean result2 = solo.waitForText("New Budget", 1, 100, false, true) && solo.waitForText("200", 1, 100, false, true) && 
					 			solo.waitForText("01-03-2015", 1, 100, false, true) && solo.waitForText("Budget", 1, 100, false, true);
			 assertTrue(result2);
			 solo.clickOnButton("Delete");
			 solo.clickOnButton("Yes");
			 solo.goBackToActivity("VirtualReceiptActivity");
			 solo.clickOnText("View receipts");
			 boolean result3 = solo.waitForText("New Budget", 1, 100, false, true);
			 assertFalse(result3);
		 }		
		
		public void testModifyDateInIncome() throws Exception {

			solo.clickOnText("Add Income");

			EditText mDateText = (EditText) solo.getView(R.id.date);
			 solo.enterText(mDateText, "11/13/14");
			 solo.setDatePicker(0, 2014, 9, 30);
			 solo.clickOnText("Done");
			EditText mDescriptionText = (EditText) solo.getView(R.id.description);
			solo.clearEditText(mDescriptionText);
			solo.enterText(mDescriptionText, "Happy Hallowien!!");
			boolean result1 = solo.waitForText("10/30/14",  1, 100, false, true);
			boolean result2 = solo.waitForText("11/13/14",  1, 100, false, true);
			 
			assertTrue(result1);
			assertFalse(result2);
			 
		    solo.clearEditText(mDateText);
			 solo.enterText(mDateText, "12/25/14");
			 solo.setDatePicker(0, 2015, 0, 1);
			 solo.clickOnText("Done");
			solo.clearEditText(mDescriptionText);
			solo.enterText(mDescriptionText, "Happy New Year!!!");
			boolean result3 = solo.waitForText("12/25/13", 1, 100, false, true);
			boolean result4 = solo.waitForText("01/01/15",  1, 100, false, true);
			assertFalse(result3);
			assertTrue(result4);
			 
			solo.clearEditText(mDateText);
			solo.clearEditText(mDescriptionText);
			solo.goBackToActivity("VirtualReceiptActivity");
		}		
		 
		 public void testModifyCategoryinIncome() throws Exception{
			 
			 solo.clickOnText("Add Income");
			 solo.pressSpinnerItem(0, 1);
			 boolean result1 = solo.isSpinnerTextSelected(0, "Budget");
			 assertTrue(result1);
			 
			 solo.pressSpinnerItem(0, -1);
			 boolean result2 = solo.isSpinnerTextSelected(0, "Income");
			 assertTrue(result2);
		 }		
	@Override
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
}

