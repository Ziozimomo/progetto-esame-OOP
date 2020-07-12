package esameOOP.project.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import esameOOP.project.Filters.FilterHandler;
import esameOOP.project.Model.Post;
import esameOOP.project.Model.Post.Politic;
import esameOOP.project.Model.Stat;


class DebugTestClass {

	private Post post1,post2,post3;
	private Stat stat;
	private Calendar date1,date2,date3;
	private Vector<Post> v;
	private FilterHandler filter;

	@BeforeEach
	void setUp() throws Exception {
		date1=Calendar.getInstance();
		date2=Calendar.getInstance();
		date3=Calendar.getInstance();
		date1.set(2020,0,1,10,30,0);
		date2.set(2020,2,1,16,30,0);
		date3.set(2020,4,1,22,30,0);
		post1=new Post("2","Questo è un messaggio di test", date1,null,"status");
		post2=new Post("3","Questo è un messaggio di test politico", date2,"www.provapolitica.com","link");
		post3=new Post("34","Questo è un messaggio di test libero come gli USA", date3,"www.linkdiunafoto.com","photo");
		v = new Vector<Post>();
		v.add(post1);
		v.add(post2);
		v.add(post3);
		stat=new Stat(v);
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	void politicControlTest() {
		assertAll("politic",()->assertEquals(Politic.NON_POLITIC,post1.getPolitic()),
				()->assertEquals(Politic.POLITIC,post2.getPolitic()),
						()->assertEquals(Politic.EXTRA_EU,post3.getPolitic()));
	}
	@Test
	void politicStatTest() {
		double d=((double)1/3)*100;
		assertEquals(Double.toString(d)+"%",stat.getStatPolitics().getNumPoliticPost());
		assertEquals("0.0%",stat.getStatPolitics().getNumNationalPost());
		assertEquals("0.0%",stat.getStatPolitics().getNumEUPost());
		assertEquals(Double.toString(d)+"%",stat.getStatPolitics().getNumExtraEUPost());
		assertEquals("0.0%",stat.getStatPolitics().getNumInternationalPost());
	}
	@Test
	void lenghtStatTest() {
		assertEquals(29, stat.getStatLenght().getMinChar());
		assertEquals(49, stat.getStatLenght().getMaxChar());
		double avg =116/3;
		assertEquals(avg,stat.getStatLenght().getAvgChar());
		double stdev=Math.sqrt((Math.pow(29-avg,2)+Math.pow(38-avg,2)+Math.pow(49-avg,2))/3);
		assertEquals(stdev,stat.getStatLenght().getStdDev());
	}
	
	@Test
	void timeStatTest() {
		LinkedHashMap<String,String> h = new LinkedHashMap<String,String>();
		int k;
		for(int i=0;i<24;i++) {
			k=i+1;
			h.put(i+"-"+k,"0.0%");
		}
		double d=((double)1/3)*100;
		h.put("10-11", Double.toString(d)+"%");
		h.put("16-17", Double.toString(d)+"%");
		h.put("22-23", Double.toString(d)+"%");
		assertEquals(h,stat.getStatTime().getPostPerHour());
	}
	
	@Test
	void mediaFilterTest() {
		Vector <Post> v2;
		String body="{\"type\":\"photo\"}";
		filter= new FilterHandler(body);
		v2=filter.filterFeed(v);
		assertEquals(post3, v2.elementAt(0));
	}
	@Test
	void politicFilterTest() {
		Vector <Post> v2;
		String body ="{\"category\":\"NON_POLITIC\"}";
		filter=new FilterHandler(body);
		v2=filter.filterFeed(v);
		assertEquals(post1, v2.elementAt(0));
	}
	@Test
	void dateFilterTest() {
		Vector <Post> v2;
		String body ="{\"created_time\":{\"after\":\"2020/02/01\"}}";
		filter=new FilterHandler(body);
		v2=filter.filterFeed(v);
		assertEquals(post2, v2.elementAt(0));
		assertEquals(post3, v2.elementAt(1));
	}
	@Test
	void doubleFilterTest() {
		Vector <Post> v2;
		String body="[{\"category\":\"EXTRA_EU\"},OR,{\"type\":\"status\"}]";
		filter=new FilterHandler(body);
		v2=filter.filterFeed(v);
		assertEquals(post1, v2.elementAt(0));
		assertEquals(post3, v2.elementAt(1));
	}
}
